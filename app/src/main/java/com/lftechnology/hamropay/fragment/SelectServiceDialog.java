package com.lftechnology.hamropay.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.db.DbManager;
import com.lftechnology.hamropay.db.models.User;
import com.lftechnology.hamropay.db.models.UserServices;

import java.util.ArrayList;

/**
 * Dialog to show available service respective to the the user selected
 */
public class SelectServiceDialog extends Dialog {
//    String[] serviceList = new String[]{"PSTN Landline", "Volume Based ADSL", "Unlimited ADSL", "NT Prepaid Recharge", "NT Postpaid Recharge"};

    ArrayList<UserServices> serviceList;
    User selectedUser;
    Button btnOk;
    RadioGroup radioGroup;
    public String selectedService;

    public SelectServiceDialog(Context context, User user) {
        super(context);
        selectedUser = user;
       serviceList = DbManager.getInstance().getAllUserServices(selectedUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.select_service_dialog);

        btnOk = (Button) findViewById(R.id.btn_ok);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedService = serviceList.get(radioGroup.getCheckedRadioButtonId()).getServiceName();
                dismiss();
            }
        });

        addRadioButtons(serviceList.size());
    }

    /**
     * Add radio buttons to radio group dynamically
     *
     * @param numberOfItems
     */
    public void addRadioButtons(int numberOfItems) {
        for (int row = 0; row < 1; row++) {
            for (int i = 0; i < numberOfItems; i++) {
                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setId((row * 2) + i);
                radioButton.setText(serviceList.get(i).getServiceName());
                ((ViewGroup) findViewById(R.id.radiogroup)).addView(radioButton);
            }
        }

        radioGroup.check(radioGroup.getChildAt(0).getId());

//        radioGroup.setD
    }

}
