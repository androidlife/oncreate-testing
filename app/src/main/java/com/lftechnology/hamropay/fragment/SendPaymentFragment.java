package com.lftechnology.hamropay.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lftechnology.hamropay.MessageModel;
import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.db.models.User;
import com.lftechnology.hamropay.db.DbManager;
import com.lftechnology.hamropay.events.ChatEvents;
import com.lftechnology.hamropay.events.FragmentTransactions;
import com.lftechnology.hamropay.fragment.SelectServiceDialog;
import com.lftechnology.hamropay.interfaces.DatePickerListener;
import com.lftechnology.hamropay.messagemodels.PaymentRequestModel;
import com.lftechnology.hamropay.utils.GeneralUtils;

import java.text.DateFormatSymbols;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Gets payment information from used to send payment
 */
public class SendPaymentFragment extends Fragment implements DatePickerListener {

    @Bind(R.id.edt_payment_amount)
    EditText edtPaymentAmount;

    @Bind(R.id.edt_additional_notes)
    EditText edtAdditionalNotes;

    @Bind(R.id.edt_send_date)
    EditText edtSendDate;

    @Bind(R.id.edt_bank_name)
    EditText edtBankName;

    @Bind(R.id.edt_service_name)
    EditText edtServiceName;

    FragmentTransactions fragmentTransactions;
    ChatEvents chatEvents;
    User selectedUser;

    @Bind(R.id.wrapper_service)
    TextInputLayout wrapperService;

    private FragmentManager fragmentManager;

    public SendPaymentFragment(User user, FragmentManager fragmentManager) {
        this.selectedUser = user;
        this.fragmentManager = fragmentManager;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.send_payment_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtSendDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    chatEvents.showDatePicker();
                } else {
                    if (!edtSendDate.getText().toString().isEmpty())
                        edtSendDate.setError(null);
                }
            }
        });

        //TODO add bank name

        if (selectedUser.getType() == User.TYPE_SERVICE) {
            wrapperService.setVisibility(View.VISIBLE);
        }

        edtServiceName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    final SelectServiceDialog selectServiceDialog = new SelectServiceDialog(getContext(), selectedUser);
                    selectServiceDialog.show();
                    selectServiceDialog.setCancelable(false);

                    selectServiceDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            Timber.e("Dismissed");
                            edtServiceName.setText(selectServiceDialog.selectedService);
                        }
                    });
                }
            }
        });

        //edtBankName.setText("");
        edtBankName.setText(DbManager.getInstance().getUserBankName());

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        fragmentTransactions = (FragmentTransactions) activity;
        chatEvents = (ChatEvents) activity;
    }

    @OnClick({R.id.iv_cancel_payment, R.id.iv_send_payment, R.id.edt_send_date, R.id.edt_service_name})
    public void setOnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_cancel_payment:
                fragmentTransactions.loadChatBarFragment();
                break;
            case R.id.iv_send_payment:
                validateFormCompletion();
                break;
            case R.id.edt_send_date:
                chatEvents.showDatePicker();
                break;
            case R.id.edt_service_name:
                final SelectServiceDialog selectServiceDialog = new SelectServiceDialog(getContext(), selectedUser);
                selectServiceDialog.show();
                selectServiceDialog.setCancelable(false);

                selectServiceDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        Timber.e("Dismissed");
                        edtServiceName.setText(selectServiceDialog.selectedService);
                    }
                });
                break;
        }
    }

    /**
     * Check for empty fields
     */
    private void validateFormCompletion() {
        if (edtPaymentAmount.getText().toString().isEmpty()) {
            edtPaymentAmount.setError("Please enter payment amount");
        }else if (Integer.parseInt(edtPaymentAmount.getText().toString())== 0) {
            edtPaymentAmount.setError("Amount can't be zero.");
        }else if (edtSendDate.getText().toString().isEmpty()) {
            edtSendDate.setError("Please enter send date");
        } else if (edtBankName.getText().toString().isEmpty()) {
            edtBankName.setError("No bank selected");
        } else {
//            chatEvents.sendPayment(new MessageModel(MessageModel.MessageTypes.SEND_PAYMENT,false, GeneralUtils.getFormattedDate(),selectedUser.getUserName(),));
//            chatEvents.paymentAcceptDecline(PaymentRequestModel.PaymentStatus.ACCEPTED, edtPaymentAmount.getText().toString(),edtBankName.getText().toString(),edtServiceName.getText().toString());
            chatEvents.sendPayment(MessageModel.MessageTypes.SEND_PAYMENT, edtPaymentAmount.getText().toString(), edtBankName.getText().toString(), edtServiceName.getText().toString());
            Log.e("+++",edtServiceName.getText().toString());
            fragmentTransactions.loadChatBarFragment();
        }
    }

    @Override
    public void onDateSet(int year, int monthOfYear, int dayOfMonth) {
        edtSendDate.setText(new StringBuilder().append(dayOfMonth).append(" ")
                .append(new DateFormatSymbols().getMonths()[monthOfYear - 1]));
    }

}
