package com.lftechnology.hamropay.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.activities.base.BaseActivity;
import com.lftechnology.hamropay.db.models.User;
import com.lftechnology.hamropay.utils.Extras;
import com.lftechnology.hamropay.utils.GeneralUtils;

import org.parceler.Parcels;

import java.util.StringTokenizer;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Send invitation ot the user requesting him/her to use HamroPay
 */
public class InviteFriend extends BaseActivity {

    String number, message = "";

    @Bind(R.id.btn_send_invitation)
    Button btnSendInvitation;

    @Bind(R.id.edt_enter_invite_message)
    EditText edtInviteMessage;

    @Bind(R.id.edt_enter_invite_phone_number)
    EditText edtPhoneNumber;

    @Bind(R.id.wrapper_phone_number)
    TextInputLayout wrapperPhoneNumber;

    @Bind(R.id.wrapper_message)
    TextInputLayout wrapperMessage;

    User selectedUser;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invite_friend;
    }

    @Override
    protected String getToolbarTitle() {
        return "Invite a Friend";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedUser = Parcels.unwrap(getIntent().getParcelableExtra(Extras.SELECTED_USER));
        if (selectedUser != null) {
            edtPhoneNumber.setText(getPhonenumber(selectedUser.getPhoneNumber()));
            setToolBarTitle("Invite " + selectedUser.getUserName());
        } else {
            setToolBarTitle("Invite A Friend ");
        }
        edtInviteMessage.setText("Hi There. Please try our HamroPay App for easier transaction of money. ");

    }

    @OnClick(R.id.btn_send_invitation)
    public void sendInvitation() {
        setEmptyError();
        checkInviteData();
        checkEmpty();
    }

    /**
     * get the values entered by user for the invitation to be sent
     */
    private void checkInviteData() {
        number = edtPhoneNumber.getText().toString().trim();
        message = edtInviteMessage.getText().toString().trim();
    }

    /**
     * Check if the assigned values are empty or not
     */
    private void checkEmpty() {
        if (TextUtils.isEmpty(number)) {
            setErrorMessage("Number ", wrapperPhoneNumber);
        } else if (TextUtils.isEmpty(message)) {
            setErrorMessage("Message ", wrapperMessage);
        } else {
            if (!GeneralUtils.numberValidation(number)) {
                wrapperPhoneNumber.setError("Number must start with 9");
            } else {
                Toast.makeText(InviteFriend.this, "Invitation sent."/* + selectedUser.getUserName()*/, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(InviteFriend.this, DashBoardActivity.class));
                finish();
            }

        }
    }

    /**
     * Set error message in respective {@link TextInputLayout}
     *
     * @param message        to be shown as error message as String
     * @param txtInputLayout {@link TextInputLayout} where error message is to be shown
     */
    private void setErrorMessage(String message, TextInputLayout txtInputLayout) {

        if (txtInputLayout != null) {
            txtInputLayout.setError(message + getString(R.string.not_empty));
        }

    }

    private void setEmptyError() {
        wrapperPhoneNumber.setError("");
        wrapperMessage.setError("");
    }

    private String getPhonenumber(String phoneNumber) {
        return phoneNumber.substring(4);
    }
}
