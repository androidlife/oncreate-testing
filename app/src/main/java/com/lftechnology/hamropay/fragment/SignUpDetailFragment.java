package com.lftechnology.hamropay.fragment;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.fragment.base.BaseFragment;
import com.lftechnology.hamropay.interfaces.ActionListener;
import com.lftechnology.hamropay.interfaces.OnFragmentInteractionListener;
import com.lftechnology.hamropay.model.Constants;
import com.lftechnology.hamropay.model.UserInfo;
import com.lftechnology.hamropay.utils.GeneralUtility;
import com.lftechnology.hamropay.utils.GeneralUtils;
import com.lftechnology.hamropay.utils.ResourceUtils;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignUpDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpDetailFragment extends BaseFragment {
    private static final String USER_INFO = "user_info";

    @Bind(R.id.wrapper_username)
    TextInputLayout wrapperUsername;
    @Bind(R.id.wrapper_email)
    TextInputLayout wrapperEmail;
    @Bind(R.id.wrapper_phone)
    TextInputLayout wrapperPhone;
    @Bind(R.id.ll_form)
    LinearLayout llForm;
    @Bind(R.id.ed_user_email)
    AutoCompleteTextView userEmail;

    private UserInfo userInfo;

    private OnFragmentInteractionListener mListener;

    private String userName = null;
    private String email = null;
    private String phoneNumber = null;
    private Context context;
    private boolean validPhone = false;
    private boolean validName = false;
    private boolean validEmail = true;

    public SignUpDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userInfo Parameter 1.
     * @return A new instance of fragment SignUpPhotoFragment.
     */
    public static SignUpDetailFragment newInstance(UserInfo userInfo) {
        SignUpDetailFragment fragment = new SignUpDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(USER_INFO, userInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userInfo = getArguments().getParcelable(USER_INFO);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_sign_up_detail;
    }

    @OnClick(R.id.btn_detail_continue)
    public void onClick(View view) {
        if (mListener != null) {
            if (validName && validPhone && validEmail) {
                userInfo.setName(userName);
                if (email != null)
                    userInfo.setEmail(email);
                userInfo.setPhone(phoneNumber);
                // TODO: 2/16/2016  Temporary progress field
                GeneralUtility.showProgressDialog(context, "Sending verification code ...");
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Constants.CODE = String.valueOf(generateRandomNumber());
                        GeneralUtility.stopProgressDialog();
                        WriteSms("Your Verification code is " + Constants.CODE, "11058");
                    }
                }, 3000);
            } else {
                validatePhone();
                validateName();
                validateEmail();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (ResourceUtils.getScreenHeight() < 1800) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            layoutParams.setMargins(0, ResourceUtils.dpToPixels(15), 0, 0);
            llForm.setLayoutParams(layoutParams);
        }

        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.GET_ACCOUNTS) ==
                PermissionChecker.PERMISSION_GRANTED) {
            Pattern emailPattern = Patterns.EMAIL_ADDRESS;
            Account[] accounts = AccountManager.get(context).getAccounts();
            ArrayList<String> emailNames = new ArrayList<>();
            for (Account account : accounts) {
                if (emailPattern.matcher(account.name).matches() && !emailNames.contains(account.name))
                    emailNames.add(account.name);
            }
            if (emailNames.size() > 0) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_dropdown_item_1line, emailNames);
                userEmail.setAdapter(adapter);
            }

        }

        //
        wrapperEmail.getEditText().setText(userInfo.getEmail());
        wrapperPhone.getEditText().setText(userInfo.getPhone());
        wrapperUsername.getEditText().setText(userInfo.getName());

        wrapperEmail.getEditText().setOnEditorActionListener(new ActionListener() {
            @Override
            public void validate() {
                validateEmail();
            }
        });
        wrapperPhone.getEditText().setOnEditorActionListener(new ActionListener() {
            @Override
            public void validate() {
                validatePhone();
            }
        });
        wrapperUsername.getEditText().setOnEditorActionListener(new ActionListener() {
            @Override
            public void validate() {
                validateName();
            }
        });
    }


    private void validateName() {
        userName = wrapperUsername.getEditText().getText().toString();
        if (TextUtils.isEmpty(userName)) {
            validName = false;
            wrapperUsername.setError("Please enter a user name.");
        } else {
            validName = true;
            wrapperUsername.setError(null);
            wrapperUsername.setErrorEnabled(false);
        }
    }

    private void validatePhone() {
        phoneNumber = wrapperPhone.getEditText().getText().toString();
        System.out.println("Validation " + GeneralUtils.numberValidation(phoneNumber));
        if (TextUtils.isEmpty(phoneNumber)) {
            validPhone = false;
            wrapperPhone.setError("Please enter a phone number.");
        } else if (!GeneralUtils.numberValidation(phoneNumber)) {
            validPhone = false;
            wrapperPhone.setError("Please enter a valid number which begins from 9.");
        } else {
            validPhone = true;
            wrapperPhone.setError(null);
            wrapperPhone.setErrorEnabled(false);
        }
    }

    private void validateEmail() {
        email = wrapperEmail.getEditText().getText().toString();
        if (!TextUtils.isEmpty(email)) {
            if (!GeneralUtility.validateEmail(email)) {
                validEmail = false;
                wrapperEmail.setError("Please enter a valid email Id.");
            } else {
                validEmail = true;
                wrapperEmail.setError(null);
                wrapperEmail.setErrorEnabled(false);
            }
        } else {
            validEmail = true;
            wrapperEmail.setError(null);
            wrapperEmail.setErrorEnabled(false);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        userInfo.setName(userName);
        userInfo.setEmail(email);
        userInfo.setPhone(phoneNumber);
        outState.putParcelable(USER_INFO, userInfo);
        super.onSaveInstanceState(outState);
    }

    private int generateRandomNumber() {
        int min = 1000, max = 9999;
        return (int) (min + Math.random() * (max - min + 1));
    }

    //Write to the default sms app
    private void WriteSms(String message, String phoneNumber) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_stat_name)
                        .setContentTitle(phoneNumber)
                        .setContentText(message);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
        notificationManager.notify(Constants.NOTIFICATION_ID, mBuilder.build());

        mListener.onFragmentInteraction(userInfo, Constants.NUMBER_VERIFICATION_FRAGMENT);
    }


}
