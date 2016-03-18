package com.lftechnology.hamropay.db;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.lftechnology.hamropay.BuildConfig;
import com.lftechnology.hamropay.HamroPayApplication;
import com.lftechnology.hamropay.MessageModel;
import com.lftechnology.hamropay.db.models.Bank;
import com.lftechnology.hamropay.db.models.BankLocation;
import com.lftechnology.hamropay.db.models.DbLoaded;
import com.lftechnology.hamropay.db.models.Transaction;
import com.lftechnology.hamropay.db.models.TransactionListItem;
import com.lftechnology.hamropay.db.models.User;
import com.lftechnology.hamropay.db.models.UserBank;
import com.lftechnology.hamropay.db.models.UserServices;
import com.lftechnology.hamropay.messagemodels.PaymentRequestModel;
import com.lftechnology.hamropay.model.UserInfo;
import com.lftechnology.hamropay.utils.GeneralUtility;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import timber.log.Timber;

/**
 */
public class DbManager {
    public static final int USER_FREQUENCY_THRESHOLD = 1;
    static volatile DbManager singleton = null;


    private Realm realm;

    private DbManager() {

    }

    public static DbManager getInstance() {
        if (singleton == null) {
            synchronized (DbManager.class) {
                if (singleton == null)
                    singleton = new DbManager();
            }
        }
        return singleton;
    }

    public void initDbConfig(Context context) {
        DbConfig.initDbConfig(context);
    }

    public void register() {
        Timber.d("Register() for Db()");
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
    }

    public void unregister() {
        Timber.d("Unregister() for Db()");
        if (realm != null) {
            realm.removeAllChangeListeners();
            realm.close();
            realm = null;
        }
    }


    public void registerNewUser(final UserInfo userInfo) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User user = realm.createObject(User.class);
                user.setUserId(Calendar.getInstance().getTimeInMillis());
                user.setUserName(userInfo.getName());
                user.setLocalPhoto(userInfo.getPicLocation());
                user.setPhoneNumber(userInfo.getPhone());
                user.setUserEmail(userInfo.getEmail());
                PrefManager.getInstance().registerNewUser(user);
            }
        });

    }

    private boolean isDbLoadedFromAssets() {
        return realm.where(DbLoaded.class).equalTo(DbLoaded.ID_VALUE, DbLoaded.ID).equalTo(DbLoaded.DB_LOADED, true)
                .findAll().size() > 0;
    }

    public void notifyDbLoadedFromAssets(final boolean success) {
        initDbConfig(HamroPayApplication.getContext());
        unregister();
        register();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                DbLoaded dbLoaded = realm.createObject(DbLoaded.class);
                dbLoaded.setDbLoaded(success);
                dbLoaded.setId(9);
            }
        });
    }

    public void checkIfDbIsLoadedFromAssets(OnDbLoadedFromAssets onDbLoadedFromAssets) {
        if (onDbLoadedFromAssetsWeakReference != null && onDbLoadedFromAssetsWeakReference.get() != null)
            onDbLoadedFromAssetsWeakReference.clear();
        onDbLoadedFromAssetsWeakReference = new WeakReference<>(onDbLoadedFromAssets);
        if (isDbLoadedFromAssets()) {
            notifyDbLoaded(true);
            return;
        }
        String assetFileName = "db/" + BuildConfig.DB_NAME;
        String dbFileLocation = new File(HamroPayApplication.getContext().getFilesDir(), DbConfig.REALM_NAME).getPath();
        DbLoader dbLoader = new DbLoader(new DbLoader.OnDbLoaded() {
            @Override
            public void dbLoadedFromAssets(boolean success) {
                notifyDbLoadedFromAssets(success);
                notifyDbLoaded(success);
            }
        });
        dbLoader.execute(assetFileName, dbFileLocation);
    }

    private WeakReference<OnDbLoadedFromAssets> onDbLoadedFromAssetsWeakReference;

    private void notifyDbLoaded(final boolean success) {
        if (onDbLoadedFromAssetsWeakReference != null && onDbLoadedFromAssetsWeakReference.get() != null
                && !onDbLoadedFromAssetsWeakReference.get().isObjectNull()) {
            onDbLoadedFromAssetsWeakReference.get().notifyDbLoadedFromAssets(success);
            onDbLoadedFromAssetsWeakReference.clear();
            onDbLoadedFromAssetsWeakReference = null;
        }
    }

    public RealmResults<User> getAllUsers() {
        RealmResults<User> users = realm.where(User.class).not().equalTo("userId", PrefManager.getInstance().getRegisteredUserId()).findAll();
        users.sort("userName", Sort.ASCENDING);
        return users;
    }

    public ArrayList<TransactionListItem> getUsersHavingTransaction() {
        ArrayList<TransactionListItem> listItems = new ArrayList<>();
        User user = realm.where(User.class).not().isEmpty("transactionRealmList").equalTo("userId", PrefManager.getInstance().getRegisteredUserId()).findFirst();
        if (user != null) {
            for (Transaction transaction : user.getTransactionRealmList()) {
                if (transaction.getTransactionDetail() == null || TextUtils.isEmpty(transaction.getTransactionDetail()))
                    continue;
                TransactionListItem transactionListItem = new TransactionListItem();
                transactionListItem.date = transaction.getTransactionId();
                transactionListItem.dateString = GeneralUtility.getTransactionTime(transactionListItem.date);
                transactionListItem.serviceId = transaction.getServiceId();
                transactionListItem.snippet = transaction.getSnippet();
                transactionListItem.serviceName = transaction.getServiceProviderName();
                User serviceUser = realm.where(User.class).equalTo("userId", transaction.getServiceId()).findFirst();
                transactionListItem.servicePhoto = serviceUser.getLocalPhoto();
                transactionListItem.serviceType = serviceUser.getType();
                transactionListItem.isComplete = transaction.isTransactionComplete();
                listItems.add(transactionListItem);
            }
        }
        return listItems;
    }

    public RealmResults<User> getAllUserByServiceType() {
        RealmResults<User> users = realm.where(User.class).equalTo("type", User.TYPE_SERVICE).or().equalTo("type", User.TYPE_STORE).not().equalTo("userId", PrefManager.getInstance().getRegisteredUserId()).findAll();
        users.sort("userName", Sort.ASCENDING);
        return users;
    }

    public RealmResults<User> getFrequentUsers() {
        RealmResults<User> users = realm.where(User.class).greaterThan("frequency", USER_FREQUENCY_THRESHOLD).findAll();
        users.sort("userName", Sort.ASCENDING);
        return users;
    }

    public ArrayList<Bank> getAllBanks() {
        RealmResults<Bank> banks = realm.where(Bank.class).findAll();
        banks.sort("bankName", Sort.ASCENDING);
        return new ArrayList<>(banks);
    }

    public ArrayList<BankLocation> getAllBankLocation() {
        RealmResults<BankLocation> bankLocations = realm.where(BankLocation.class).findAll();
        bankLocations.sort("locationName", Sort.ASCENDING);
        return new ArrayList<>(bankLocations);
    }

    public ArrayList<UserServices> getAllUserServices(User user) {
        ArrayList<UserServices> userServices = new ArrayList<>();
        if (user == null || user.getType() != User.TYPE_SERVICE)
            return userServices;
        RealmResults<UserServices> realmResults = realm.where(UserServices.class).equalTo("userId", user.getUserId()).findAll();
        realmResults.sort("serviceName", Sort.ASCENDING);
        userServices = new ArrayList<>(realmResults);
        return userServices;
    }

    public User getSelectedUserById(long userId) {
        return realm.where(User.class).equalTo("userId", userId).findFirst();
    }



    public void addUserBank(final long userId, final int bankId, final int bankLocationId, final String ebankUname, final String ebankPwd) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User user = realm.where(User.class).equalTo("userId", userId).findFirst();
                UserBank userBank = realm.createObject(UserBank.class);
                userBank.setUserId(userId);
                userBank.setBankId(bankId);
                userBank.setLocationId(bankLocationId);
                userBank.setEbankUname(ebankUname);
                userBank.setEbankPwd(ebankPwd);
                user.setUserBank(userBank);
            }
        });

    }

    public Transaction doesTransactionExistForSelectedUser(long selectedUserId) {
        User loggedInUser = realm.where(User.class).equalTo("userId", PrefManager.getInstance().getRegisteredUserId()).findFirst();
        for (Transaction transaction : loggedInUser.getTransactionRealmList()) {
            if (transaction.getServiceId() == selectedUserId) {
                return transaction;
            }
        }
        return null;
    }

    public void createEmptyTransactionForUser(final User selectedUser, final int transactionType, final Transaction emptyTransaction, final long transactionId) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User loggedInUser = realm.where(User.class).equalTo("userId", PrefManager.getInstance().getRegisteredUserId()).findFirst();
                if (emptyTransaction == null) {
                    Transaction emptyTransaction = new Transaction();
                    emptyTransaction.setTransactionId(transactionId);
                    emptyTransaction.setUserId(loggedInUser.getUserId());
                    emptyTransaction.setServiceId(selectedUser.getUserId());
                    emptyTransaction.setServiceProviderName(selectedUser.getUserName());
                    emptyTransaction.setTransactionType(transactionType);
                    emptyTransaction.setTransactionComplete(false);
                    loggedInUser.getTransactionRealmList().add(emptyTransaction);
                }
            }
        });
    }

    public String getUserBankName() {
        User user = realm.where(User.class).equalTo("userId", PrefManager.getInstance().getRegisteredUserId()).findFirst();
        if (user == null || user.getUserBank() == null)
            return null;
        Bank bank = realm.where(Bank.class).equalTo("bankId", user.getUserBank().getBankId()).findFirst();
        return bank.getBankName();
    }

    public ArrayList<MessageModel> getTransactionChatList(long transactionId) {
        Transaction transaction = realm.where(Transaction.class).equalTo("transactionId", transactionId).findFirst();
        if (transaction == null || transaction.getTransactionDetail() == null)
            return new ArrayList<>();
        return new Gson().fromJson(transaction.getTransactionDetail(), new TypeToken<List<MessageModel>>() {
        }.getType());
    }

    public void updateTransactionDetail(final long transactionId, final ArrayList<MessageModel> transactionDetail) {
        final Realm realm1 = Realm.getDefaultInstance();
        realm1.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Transaction transaction = realm.where(Transaction.class).equalTo("transactionId", transactionId).findFirst();
                if (transaction != null) {
                    User loggedInUser = realm.where(User.class).equalTo("userId", PrefManager.getInstance().getRegisteredUserId()).findFirst();
                    Gson gson = new Gson();
                    loggedInUser.getTransactionRealmList().remove(transaction);
                    boolean isPending = false;
                    for (MessageModel messageModel : transactionDetail) {
                        if (messageModel.paymentStatus == PaymentRequestModel.PaymentStatus.PENDING) {
                            isPending = true;
                            break;
                        }
                    }
                    transaction.setTransactionComplete(isPending);
                    JsonElement element = gson.toJsonTree(transactionDetail, new TypeToken<List<MessageModel>>() {
                    }.getType());
                    transaction.setSnippet(transactionDetail.get(transactionDetail.size() - 1).textMessage);
                    transaction.setTransactionDetail(element.toString());
                    loggedInUser.getTransactionRealmList().add(transaction);
                }

            }
        }, new Realm.Transaction.Callback() {
            @Override
            public void onSuccess() {
                super.onSuccess();
                realm1.close();
            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
                realm1.close();
            }
        });
    }

//    public ArrayList<String> getAllUsersName() {
//        ArrayList<String> results = new ArrayList<>();
//        RealmResults<User> realmResults = realm.where(User.class).findAll();
//        for (User user : realmResults) {
//            results.add(user.getUserName());
//        }
//        return results;
//    }
}
