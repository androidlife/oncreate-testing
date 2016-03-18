package com.lftechnology.hamropay.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.db.models.TransactionListItem;
import com.lftechnology.hamropay.db.models.User;
import com.lftechnology.hamropay.model.Constants;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 */
public class RecentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<TransactionListItem> userList;
    private Context context;

    public RecentAdapter(Context context, ArrayList<TransactionListItem> userList) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;

        view = layoutInflater.inflate(R.layout.custom_recent_contact_layout, parent, false);
        viewHolder = new RecentAdapterHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        setData(holder, position);
    }

    private void setData(final RecyclerView.ViewHolder holder, int position) {
        RecentAdapterHolder recentAdapterHolder = ((RecentAdapterHolder) holder);

        final TransactionListItem listItem = userList.get(position);
        //Transaction transaction = user.getTransactionRealmList().get(0);


        recentAdapterHolder.getTvRecentName().setText(listItem.serviceName);
        recentAdapterHolder.getTvRecentDescription().setText(listItem.snippet);
        //date

        recentAdapterHolder.getTvRecentLog().setText(listItem.dateString);



        int userType = listItem.serviceType;
        int color = 0;

        if (userType == User.TYPE_NORMAL) {
            color = ContextCompat.getColor(context, R.color.colorPrimary);
            //recentAdapterHolder.getIvRecentProfilePic().setBorderWidth(0);
        }
        if (userType == User.TYPE_STORE) {
            color = ContextCompat.getColor(context, R.color.colorAccent);
            //recentAdapterHolder.getIvRecentProfilePic().setBorderWidth(ResourceUtils.dpToPixels(1));
        }
        if (userType == User.TYPE_SERVICE) {
            color = ContextCompat.getColor(context, R.color.storeTypeColor);
            //recentAdapterHolder.getIvRecentProfilePic().setBorderWidth(ResourceUtils.dpToPixels(1));
        }

        recentAdapterHolder.getIvRecentUnreadIndicator().setVisibility(listItem.isComplete ? View.VISIBLE : View.GONE);
        //recentAdapterHolder.getIvRecentProfilePic().setBorderColor(color);

        Glide.with(context)
                .load(Constants.ASSETS_IMAGE_DIRECTORY + listItem.servicePhoto)
                .placeholder(R.drawable.ic_noprofilepicture)
                .error(R.drawable.ic_noprofilepicture)
                .centerCrop()
                .dontAnimate()
                .into(recentAdapterHolder.getIvRecentProfilePic());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class RecentAdapterHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_recent_profile_pic)
        ImageView ivRecentProfilePic;

        @Bind(R.id.tv_recent_name)
        TextView tvRecentName;

        @Bind(R.id.tv_recent_description)
        TextView tvRecentDescription;

        @Bind(R.id.tv_recent_log)
        TextView tvRecentLog;

        @Bind(R.id.iv_recent_unread_indicator)
        ImageView ivRecentUnreadIndicator;

        public RecentAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public ImageView getIvRecentProfilePic() {
            return ivRecentProfilePic;
        }

        public TextView getTvRecentName() {
            return tvRecentName;
        }

        public TextView getTvRecentDescription() {
            return tvRecentDescription;
        }

        public TextView getTvRecentLog() {
            return tvRecentLog;
        }

        public ImageView getIvRecentUnreadIndicator() {
            return ivRecentUnreadIndicator;
        }
    }
}
