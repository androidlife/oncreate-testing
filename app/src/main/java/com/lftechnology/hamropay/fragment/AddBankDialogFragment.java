package com.lftechnology.hamropay.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.lftechnology.hamropay.activities.AddBankActivity;
import com.lftechnology.hamropay.R;


public class AddBankDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.add_bank_dialog, null);
        builder.setView(dialogView);

        Button btnAddBank = (Button) dialogView.findViewById(R.id.btn_add_bank);
        btnAddBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddBankActivity.class));
                dismiss();
            }
        });

        return builder.create();
    }

}
