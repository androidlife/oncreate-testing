package com.lftechnology.hamropay.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lftechnology.hamropay.GlideConfigurator;
import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.activities.FriendProfileActivity;
import com.lftechnology.hamropay.db.DbManager;
import com.lftechnology.hamropay.db.models.User;
import com.lftechnology.hamropay.db.models.UserTemp;
import com.lftechnology.hamropay.model.Constants;
import com.lftechnology.hamropay.utils.Extras;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmResults;
import timber.log.Timber;

/**
 *
 */
public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<UserTemp> users;
    public static final int VIEW_TYPE_SERVICE = 0;
    public static final int VIEW_TYPE_STORE = 1;
    public static final int VIEW_TYPE_NORMAL = 2;
    public static final int VIEW_TYPE_REGISTERED_USER = 3;
    private Context context = null;


    public ContactsRecyclerViewAdapter(Context context, RealmResults<User> users) {
        this.users = new ArrayList<UserTemp>(users.size());
        for (User user : users) {
            UserTemp userTemp = new UserTemp();
            //userTemp.setUserBank(user.getUserBank());
            userTemp.setLocalPhoto(user.getLocalPhoto());
            userTemp.setPhoneNumber(user.getPhoneNumber());
            userTemp.setFrequency(user.getFrequency());
            userTemp.setUserId(user.getUserId());
            userTemp.setUserName(user.getUserName());
            userTemp.setType(user.getType());
            this.users.add(userTemp);
        }
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        int userViewType = 0;
        int userType = 0;
        UserTemp user = users.get(position);

        userType = user.getType();
        if (userType == User.TYPE_NOT_JOINED) {
            userViewType = VIEW_TYPE_NORMAL;
        }
        if (userType == User.TYPE_SERVICE) {
            userViewType = VIEW_TYPE_SERVICE;
        }
        if (userType == User.TYPE_STORE) {
            userViewType = VIEW_TYPE_STORE;
        }
        if (userType == User.TYPE_NORMAL) {
            userViewType = VIEW_TYPE_REGISTERED_USER;
        }
        return userViewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_layout, parent, false);
                viewHolder = new NormalViewHolder(view);
                break;
            case VIEW_TYPE_STORE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_user_layout, parent, false);
                viewHolder = new StoreRecyclerViewHolder(view);
                break;
            case VIEW_TYPE_SERVICE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_user_layout, parent, false);
                viewHolder = new ServiceViewHolder(view);
                break;
            case VIEW_TYPE_REGISTERED_USER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_joined_user_layout, parent, false);
                viewHolder = new RegisteredViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Timber.d("called every time %d", position);
        switch (getItemViewType(position)) {
            case VIEW_TYPE_NORMAL:
                setSimpleUserData(holder, position);
                break;
            case VIEW_TYPE_STORE:
                setStoreUserData(holder, position);
                break;
            case VIEW_TYPE_SERVICE:
                setServiceUserData(holder, position);
                break;
            case VIEW_TYPE_REGISTERED_USER:
                setRegisteredUserData(holder, position);
                break;
        }
    }

    private void setRegisteredUserData(RecyclerView.ViewHolder holder, final int position) {
        RegisteredViewHolder registeredViewHolder = ((RegisteredViewHolder) holder);
        registeredViewHolder.getTvName().setText(users.get(position).getUserName());
        //registeredViewHolder.getProfileImageView().setBorderWidth(0);

        GlideConfigurator.loadCircularImage(context, Constants.ASSETS_IMAGE_DIRECTORY + users.get(position).getLocalPhoto(),
                context.getResources().getColor(R.color.circle_border_color_orange))
                .into(registeredViewHolder.getProfileImageView());

//        Glide.with(context)
//                .load(Constants.ASSETS_IMAGE_DIRECTORY + users.get(position).getLocalPhoto())
//                .placeholder(R.drawable.ic_noprofilepicture)
//                .error(R.drawable.ic_noprofilepicture)
//                .centerCrop()
//                .dontAnimate()
//                .into(registeredViewHolder.getProfileImageView());

        registeredViewHolder.getSingleJoinedUserContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToProfileActivity(users.get(position));
            }
        });
    }

    private void setServiceUserData(RecyclerView.ViewHolder holder, final int position) {
        ServiceViewHolder serviceViewHolder = ((ServiceViewHolder) holder);
        serviceViewHolder.getTvName().setText(users.get(position).getUserName());

        GlideConfigurator.loadCircularImage(context, Constants.ASSETS_IMAGE_DIRECTORY + users.get(position).getLocalPhoto(),
                context.getResources().getColor(R.color.circle_border_color_purple))
                .into(serviceViewHolder.getProfileImageView());


        serviceViewHolder.getServiceUserContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToProfileActivity(users.get(position));
            }
        });
    }

    private void setStoreUserData(RecyclerView.ViewHolder holder, final int position) {
        StoreRecyclerViewHolder storeRecyclerViewHolder = ((StoreRecyclerViewHolder) holder);
        storeRecyclerViewHolder.getTvName().setText(users.get(position).getUserName());

        GlideConfigurator.loadCircularImage(context, Constants.ASSETS_IMAGE_DIRECTORY + users.get(position).getLocalPhoto(),
                context.getResources().getColor(R.color.circle_border_color_purple))
                .into(storeRecyclerViewHolder.getProfileImageView());


        storeRecyclerViewHolder.getContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToProfileActivity(users.get(position));
            }
        });
    }

    private void setSimpleUserData(RecyclerView.ViewHolder holder, final int position) {
        NormalViewHolder normalViewHolder = ((NormalViewHolder) holder);
        normalViewHolder.getTvName().setText(users.get(position).getUserName());

        //normalViewHolder.getProfileImageView().setBorderWidth(0);
        GlideConfigurator.loadCircularImage(context, Constants.ASSETS_IMAGE_DIRECTORY + users.get(position).getLocalPhoto(),
                0)
                .into(normalViewHolder.getProfileImageView());

        normalViewHolder.getSingleUserContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToProfileActivity(users.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return users == null ? 0 : users.size();
    }

    public static class StoreRecyclerViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.store_user_container)
        LinearLayout container;

        @Bind(R.id.tv_sponsored_name)
        TextView tvName;

        @Bind(R.id.iv_store_profile)
        ImageView ivStoreProfile;

        public StoreRecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getTvName() {
            return tvName;
        }

        public ImageView getProfileImageView() {
            return ivStoreProfile;
        }

        public LinearLayout getContainer() {
            return container;
        }
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_vendor_name)
        TextView tvName;

        @Bind(R.id.iv_service_profile)
        ImageView ivServiceProfile;

        @Bind(R.id.service_user_container)
        LinearLayout serviceUserContainer;

        public ServiceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getTvName() {
            return tvName;
        }

        public ImageView getProfileImageView() {
            return ivServiceProfile;
        }

        public LinearLayout getServiceUserContainer() {
            return serviceUserContainer;
        }
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_single_user_name)
        TextView tvName;

        @Bind(R.id.iv_single_user_profile)
        ImageView ivSingleUserProfile;

        @Bind(R.id.single_user_container)
        LinearLayout singleUserContainer;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getTvName() {
            return tvName;
        }

        public ImageView getProfileImageView() {
            return ivSingleUserProfile;
        }

        public LinearLayout getSingleUserContainer() {
            return singleUserContainer;
        }
    }

    public void navigateToProfileActivity(UserTemp userTemp) {
        Bundle params = new Bundle();
        User user = DbManager.getInstance().getSelectedUserById(userTemp.getUserId());
        params.putParcelable(Extras.SELECTED_USER, Parcels.wrap(User.class, user));
        Intent startProfile = new Intent(context, FriendProfileActivity.class);
        startProfile.putExtras(params);
        context.startActivity(startProfile);
    }


    public static class RegisteredViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_single_user_name)
        TextView tvName;

        @Bind(R.id.iv_single_user_profile)
        ImageView ivSingleUserProfile;

        @Bind(R.id.single_joined_user_container)
        LinearLayout singleJoinedUserContainer;

        public RegisteredViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public TextView getTvName() {
            return tvName;
        }

        public ImageView getProfileImageView() {
            return ivSingleUserProfile;
        }

        public LinearLayout getSingleJoinedUserContainer() {
            return singleJoinedUserContainer;
        }

    }
}
