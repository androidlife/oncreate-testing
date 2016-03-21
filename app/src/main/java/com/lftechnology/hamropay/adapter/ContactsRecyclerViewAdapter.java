package com.lftechnology.hamropay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.lftechnology.hamropay.adapter.delegates.NotRegisteredUserDelegate;
import com.lftechnology.hamropay.adapter.delegates.RegisteredUserDelegate;
import com.lftechnology.hamropay.adapter.delegates.ServiceUserDelegate;
import com.lftechnology.hamropay.adapter.delegates.StoreUserDelegate;
import com.lftechnology.hamropay.adapter.delegates.base.AdapterDelegatesManager;
import com.lftechnology.hamropay.db.models.User;
import com.lftechnology.hamropay.db.models.UserTemp;
import com.lftechnology.hamropay.model.Constants;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

/**
 *
 */
public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<UserTemp> users;
    private Context context = null;
    private final AdapterDelegatesManager adapterDelegatesManager = new AdapterDelegatesManager();


    public ContactsRecyclerViewAdapter(Context context, RealmResults<User> users) {
        this.users = new ArrayList<>(users.size());
        for (User user : users) {
            UserTemp userTemp = new UserTemp();
            //userTemp.setUserBank(user.getUserBank());
            userTemp.setLocalPhoto(Constants.ASSETS_IMAGE_DIRECTORY + user.getLocalPhoto());
            userTemp.setPhoneNumber(user.getPhoneNumber());
            userTemp.setFrequency(user.getFrequency());
            userTemp.setUserId(user.getUserId());
            userTemp.setUserName(user.getUserName());
            userTemp.setType(user.getType());
            this.users.add(userTemp);
        }
        this.context = context;

        adapterDelegatesManager.addDelegate(new RegisteredUserDelegate(context, User.TYPE_NORMAL));
        adapterDelegatesManager.addDelegate(new NotRegisteredUserDelegate(context, User.TYPE_NOT_JOINED));
        adapterDelegatesManager.addDelegate(new ServiceUserDelegate(context, User.TYPE_SERVICE));
        adapterDelegatesManager.addDelegate(new StoreUserDelegate(context, User.TYPE_STORE));
    }

    @Override
    public int getItemViewType(int position) {
        return adapterDelegatesManager.getItemViewType(users, position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return adapterDelegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        adapterDelegatesManager.onBindViewHolder(users, position, holder);
    }


    @Override
    public int getItemCount() {
        return users == null ? 0 : users.size();
    }


}
