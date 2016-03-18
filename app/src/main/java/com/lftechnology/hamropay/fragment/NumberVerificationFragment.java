package com.lftechnology.hamropay.fragment;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;

import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.fragment.base.BaseFragment;
import com.lftechnology.hamropay.interfaces.OnFragmentInteractionListener;
import com.lftechnology.hamropay.model.Constants;
import com.lftechnology.hamropay.model.UserInfo;
import com.lftechnology.hamropay.utils.GeneralUtility;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NumberVerificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NumberVerificationFragment extends BaseFragment {
    private static final String USER_INFO = "user_info";
    private static final String VERIFICATION_CODE = "verification_code";

    @Bind(R.id.ed_code_one)
    EditText edCodeOne;
    @Bind(R.id.ed_code_two)
    EditText edCodeTwo;
    @Bind(R.id.ed_code_three)
    EditText edCodeThree;
    @Bind(R.id.ed_code_four)
    EditText edCodeFour;

    private UserInfo userInfo;
    private OnFragmentInteractionListener mListener;
    private Context context;
    private String code;

    public NumberVerificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userInfo Parameter 1.
     * @param code
     * @return A new instance of fragment SignUpPhotoFragment.
     */
    public static NumberVerificationFragment newInstance(UserInfo userInfo, String code) {
        NumberVerificationFragment fragment = new NumberVerificationFragment();
        Bundle args = new Bundle();
        args.putParcelable(USER_INFO, userInfo);
        args.putString(VERIFICATION_CODE, code);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userInfo = getArguments().getParcelable(USER_INFO);
            code = getArguments().getString(VERIFICATION_CODE);
        }
    }

    @OnClick(R.id.btn_verification_continue)
    public void onClick(View view) {
        if (mListener != null) {
            // TODO: 2/16/2016  Temporary progress field
            checkVerificationCode();
        }
    }

    private void checkVerificationCode() {
        GeneralUtility.showProgressDialog(context, "Verifying ...");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                GeneralUtility.stopProgressDialog();
                if (code.equals(Constants.CODE)) {
                    NotificationManager notificationManager =
                            (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);

                    notificationManager.cancel(Constants.NOTIFICATION_ID);
                    mListener.onFragmentInteraction(userInfo, Constants.SIGN_UP_PHOTO_FRAGMENT);
                } else {
                    // Invalid id...
                }
            }
        }, 2000);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_number_verification;
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edCodeOne.requestFocus();
        if (code != null) {
            edCodeOne.setText(code.substring(0, 1));
            edCodeTwo.setText(code.substring(1, 2));
            edCodeThree.setText(code.substring(2, 3));
            edCodeFour.setText(code.substring(3));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
