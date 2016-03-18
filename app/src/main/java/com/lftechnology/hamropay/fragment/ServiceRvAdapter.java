package com.lftechnology.hamropay.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lftechnology.hamropay.R;

/**
 * Created by ManasShrestha on 2/24/16.
 */
public class ServiceRvAdapter extends RecyclerView.Adapter<ServiceRvAdapter.ViewHolder> {

    Context context; String[] serviceList = new String[]{"PSTN Landline", "Volume Based ADSL", "Unlimited ADSL", "NT Prepaid Recharge", "NT Postpaid Recharge"};


    public ServiceRvAdapter(Context context) {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.service_items,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return serviceList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
