package com.lftechnology.hamropay.adapter.delegates.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by laaptu on 3/21/16.
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    public View itemView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
    }
}
