package com.lftechnology.hamropay.adapter.delegates;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.adapter.delegates.base.AbsAdapterDelegate;
import com.lftechnology.hamropay.adapter.delegates.base.BaseViewHolder;
import com.lftechnology.hamropay.db.models.UserTemp;

import java.util.List;

import butterknife.Bind;

/**
 */
public class NotRegisteredUserDelegate extends AbsAdapterDelegate<List<UserTemp>> {

    public NotRegisteredUserDelegate(Context context, int viewType) {
        super(context,viewType);
    }

    @Override
    public boolean isForViewType(@NonNull List<UserTemp> items, int position) {
        return items.get(position).getType() == viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new NotRegisteredViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_layout, parent, false)
        );

    }

    @Override
    public void onBindViewHolder(@NonNull final List<UserTemp> items, final int position, @NonNull RecyclerView.ViewHolder holder) {
        NotRegisteredViewHolder registeredViewHolder = ((NotRegisteredViewHolder) holder);
        registeredViewHolder.tvName.setText(items.get(position).getUserName());
        loadImage(registeredViewHolder.ivSingleUserProfile, items.get(position).getLocalPhoto(), 0);
        addClickListener(registeredViewHolder.itemView, items.get(position));
    }


    public static class NotRegisteredViewHolder extends BaseViewHolder {
        @Bind(R.id.tv_single_user_name)
        public TextView tvName;

        @Bind(R.id.iv_single_user_profile)
        public ImageView ivSingleUserProfile;

        public NotRegisteredViewHolder(View itemView) {
            super(itemView);
        }
    }
}
