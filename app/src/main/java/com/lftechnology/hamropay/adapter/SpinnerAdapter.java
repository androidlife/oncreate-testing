package com.lftechnology.hamropay.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.activities.AddBankActivity;

import java.util.ArrayList;

/**
 * Adapter class extends with ArrayAdapter
 */
public class SpinnerAdapter extends ArrayAdapter<String> {

    private Activity activity;
    private ArrayList data;
    LayoutInflater inflater;

    public SpinnerAdapter(
            AddBankActivity activitySpinner,
            int textViewResourceId,
            ArrayList objects
    ) {
        super(activitySpinner, textViewResourceId, objects);

        /********** Take passed values **********/
        activity = activitySpinner;
        data = objects;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    /**
     * This function called for each row ( Called data.size() times )
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(R.layout.bankdetail, parent, false);

        /***** Get each Model object from Arraylist ********/
//        bankDetail = null;
//        bankDetail = (BankDetail) data.get(position);
//
        TextView bankName = (TextView) row.findViewById(R.id.txt_bank_name);

//        TextView bankBranch = (TextView) row.findViewById(R.id.txt_bank_branch);
//
//        if (position == 0) {
//
//            // Default selected Spinner item
//            bankName.setText("Please select Bank");
////            bankBranch.setText("Please select a branch");
//        } else {
//            // Set values for spinner each row
//            bankBranch.setText(bankDetail.bankBranch);
//            bankName.setText(bankDetail.bankName);
//
//        }

        bankName.setText(getItem(position));

        return row;
    }
}