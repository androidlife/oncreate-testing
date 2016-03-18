package com.lftechnology.hamropay.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;

public class PaymentRequestViewHolder extends RecyclerView.ViewHolder {

        @Bind(android.R.id.text1)
        TextView textView;

        public PaymentRequestViewHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this,itemView);
        }
    }