package com.lftechnology.hamropay.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.activities.base.BaseActivity;
import com.lftechnology.hamropay.adapter.SpinnerAdapter;
import com.lftechnology.hamropay.db.DbManager;
import com.lftechnology.hamropay.db.PrefManager;
import com.lftechnology.hamropay.db.models.Bank;
import com.lftechnology.hamropay.interfaces.ToolBarClickListener;
import com.lftechnology.hamropay.model.Constants;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * An form where user need to input data to add an bank account
 */
public class AddBankActivity extends BaseActivity implements ToolBarClickListener {

    @Bind(R.id.bank_name_wrapper)
    TextInputLayout inputLayoutBankName;

    @Bind(R.id.account_holder_wrapper)
    TextInputLayout inputLayoutAccountHolderName;

    @Bind(R.id.account_number_wrapper)
    TextInputLayout inputLayoutAccountNumber;

    @Bind(R.id.btn_connect_bank)
    Button btnConnectBank;

//    @Bind(R.id.wrapper_branch)
//    TextInputLayout inputLayoutBankBranch;

    @Bind(R.id.spin_bank_name)
    Spinner spinBankName;

    @Bind(R.id.edt_account_holder_name)
    EditText edtAccountHolderName;

    @Bind(R.id.edt_account_number)
    EditText edtAccountNumber;

//    @Bind(R.id.spin_bank_branch)
//    Spinner spinBankBranch;

    String accountHolderName = "", accountNumber = "", bankBranch = "";

    private ArrayList<Bank> banks = new ArrayList<>();
    //private ArrayList<BankLocation> bankLocations = new ArrayList<>();
    private ArrayList<String> bankName;// bankLocationName;
    private Bank selectedBank;
    //private BankLocation selectedBankLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolBarClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        banks = DbManager.getInstance().getAllBanks();
        //bankLocations = DbManager.getInstance().getAllBankLocation();

        setBankDetail();
        setBranchDetail();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_bank;
    }

    @Override
    protected String getToolbarTitle() {
        return "Add a Bank";
    }

    /**
     * check and get the bank data when user creates/Add bank
     */
    public void sendBankDetail() {
        setEmptyError();
        getBankDetail();
        checkEmptyData();

    }

    /**
     * set error in the {@link TextInputLayout} if the value is empty
     *
     * @param errorText      Name of the field which cannot be empty
     * @param txtInputLayout {@link TextInputLayout} which is empty
     */
    private void setError(String errorText, TextInputLayout txtInputLayout) {
        if (txtInputLayout != null) {
            txtInputLayout.setError(errorText);
        }
    }

    /**
     * Get the user input data to add an bank account from respective {@link EditText}
     */
    private void getBankDetail() {
        //getSelectedBankDetail();
        accountHolderName = edtAccountHolderName.getText().toString().trim();
        accountNumber = edtAccountNumber.getText().toString().trim();
    }

    /**
     * Check if user input data is empty or not
     * if empty set error data else submit the data to add an account
     */
    private void checkEmptyData() {
        if (selectedBank == null) {
            Toast.makeText(AddBankActivity.this, "Bank name cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (accountHolderName.isEmpty()) {
            setError(getString(R.string.account) + getString(R.string.holder) + getString(R.string.name) + getString(R.string.not_empty), inputLayoutAccountHolderName);
        } else if (accountNumber.isEmpty()) {
            setError(getString(R.string.account) + getString(R.string.number) + getString(R.string.not_empty), inputLayoutAccountNumber);
        }
//        else if (selectedBankLocation == null) {
//            Toast.makeText(AddBankActivity.this, "Bank branch cannot be empty", Toast.LENGTH_SHORT).show();
//        }
        else {
            setEmptyError();
            Toast.makeText(AddBankActivity.this, "Bank account has been added", Toast.LENGTH_SHORT).show();
            DbManager.getInstance().addUserBank(
                    PrefManager.getInstance().getRegisteredUserId(), selectedBank.getBankId(),
                    0,
                    accountHolderName, accountNumber);
//            startActivity(new Intent(AddBankActivity.this, MainActivity.class));

            if (getIntent().hasExtra(Constants.KEY_ACTIVITY)) {
                Intent intent = new Intent(AddBankActivity.this, DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                onBackPressed();
            }
        }
    }

    /**
     * method to set error text empty if condition matches
     */
    private void setEmptyError() {
        inputLayoutBankName.setError("");
        inputLayoutAccountHolderName.setError("");
        inputLayoutAccountNumber.setError("");
        //inputLayoutBankBranch.setError("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
//                return true;
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onToolbarClicked() {
        Timber.d("Hello there");


    }

    /**
     * Set value of the banks for the {@link Spinner}
     */
    private void setBranchDetail() {

        SpinnerAdapter bankNameAdapter = new SpinnerAdapter(this, R.layout.bankdetail, bankName);
        spinBankName.setAdapter(bankNameAdapter);

//        SpinnerAdapter bankBranchAdapter = new SpinnerAdapter(this, R.layout.bankdetail, bankLocationName);
//        spinBankBranch.setAdapter(bankBranchAdapter);

        getSelectedBankDetail();


    }

    /**
     * TODO implement db
     * Set bank detail for demo only fetch data from database alter
     */
    private void setBankDetail() {
        bankName = new ArrayList<>();
        for (Bank bank : banks) {
            bankName.add(bank.getBankName());
        }
        bankName.add(0, getString(R.string.hint_select_bank));
//        bankLocationName = new ArrayList<>();
//        for (BankLocation bankLocation : bankLocations)
//            bankLocationName.add(bankLocation.getLocationName());
//        bankLocationName.add(0, getString(R.string.hint_select_bank_location));
    }

    private void getSelectedBankDetail() {
        spinBankName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                // TODO Auto-generated method stub
                Timber.d("OnItemSelected Bank =%d", position);
                if (position == 0) {
                    selectedBank = null;
                } else {
                    selectedBank = banks.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


//        spinBankBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                       int position, long arg3) {
//                // TODO Auto-generated method stub
//                Timber.d("OnItemSelected Bank  Location =%d", position);
//                if (position == 0) {
//                    selectedBankLocation = null;
//                } else {
//                    selectedBankLocation = bankLocations.get(position);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//                // TODO Auto-generated method stub
//
//            }
//        });
    }

    @OnClick({R.id.btn_connect_bank})
    public void connectBank() {
        sendBankDetail();

    }
}
