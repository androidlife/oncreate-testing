package com.lftechnology.hamropay.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.activities.AddBankActivity;
import com.lftechnology.hamropay.activities.DashBoardActivity;
import com.lftechnology.hamropay.fragment.base.BaseFragment;
import com.lftechnology.hamropay.interfaces.OnFragmentInteractionListener;
import com.lftechnology.hamropay.model.Constants;
import com.lftechnology.hamropay.model.UserInfo;

import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignUpOthersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpOthersFragment extends BaseFragment {
    private static final String USER_INFO = "user_info";

    private UserInfo userInfo;

    private OnFragmentInteractionListener mListener;
    private Context context;

    public SignUpOthersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userInfo Parameter 1.
     * @return A new instance of fragment SignUpPhotoFragment.
     */
    public static SignUpOthersFragment newInstance(UserInfo userInfo) {
        SignUpOthersFragment fragment = new SignUpOthersFragment();
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
        return R.layout.fragment_sign_up_others;
    }

    @OnClick({R.id.btn_search_friends, R.id.btn_add_bank, R.id.btn_add_credit_card, R.id.tv_skip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search_friends:
            case R.id.tv_skip:
                startActivity(new Intent(getActivity(), DashBoardActivity.class));
                getActivity().finish();
                break;
            case R.id.btn_add_bank:
                startActivity(new Intent(getActivity(), AddBankActivity.class)
                        .putExtra(Constants.KEY_ACTIVITY, Constants.SIGNUP_ACTIVITY));

                break;
            case R.id.btn_add_credit_card:
                Toast.makeText(context, R.string.coming_soon, Toast.LENGTH_SHORT).show();
                break;

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
}
