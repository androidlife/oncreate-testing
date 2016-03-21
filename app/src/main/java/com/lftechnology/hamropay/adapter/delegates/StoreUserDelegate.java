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
 * Created by laaptu on 3/21/16.
 */
public class StoreUserDelegate extends AbsAdapterDelegate<List<UserTemp>> {

    public StoreUserDelegate(Context context, int viewType) {
        super(context,viewType);
    }

    @Override
    public boolean isForViewType(@NonNull List<UserTemp> items, int position) {
        return items.get(position).getType() == viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new StoreViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.store_user_layout, parent, false)
        );

    }

    @Override
    public void onBindViewHolder(@NonNull List<UserTemp> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        StoreViewHolder storeViewHolder = ((StoreViewHolder) holder);
        storeViewHolder.tvName.setText(items.get(position).getUserName());
        loadImage(storeViewHolder.ivSingleUserProfile, items.get(position).getLocalPhoto(), getContext().getResources().getColor(R.color.circle_border_color_purple));
        addClickListener(storeViewHolder.itemView, items.get(position));
    }


    public static class StoreViewHolder extends BaseViewHolder {
        @Bind(R.id.tv_sponsored_name)
        public TextView tvName;

        @Bind(R.id.iv_store_profile)
        public ImageView ivSingleUserProfile;

        public StoreViewHolder(View itemView) {
            super(itemView);
        }
    }
}
