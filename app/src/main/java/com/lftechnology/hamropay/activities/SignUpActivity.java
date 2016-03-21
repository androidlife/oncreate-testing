package com.lftechnology.hamropay.activities;

import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.activities.base.BaseActivity;
import com.lftechnology.hamropay.fragment.NumberVerificationFragment;
import com.lftechnology.hamropay.fragment.SignUpDetailFragment;
import com.lftechnology.hamropay.fragment.SignUpOthersFragment;
import com.lftechnology.hamropay.fragment.SignUpPhotoFragment;
import com.lftechnology.hamropay.interfaces.OnFragmentInteractionListener;
import com.lftechnology.hamropay.model.Constants;
import com.lftechnology.hamropay.model.UserInfo;

public class SignUpActivity extends BaseActivity implements OnFragmentInteractionListener {

    private String currentTag = Constants.SIGN_UP_DETAIL_FRAGMENT;
    private UserInfo userInfo = new UserInfo();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected String getToolbarTitle() {
        return null;
    }


    @Override
    protected void onResume() {
        super.onResume();
        //currentTag = Constants.SIGN_UP_PHOTO_FRAGMENT;
        onFragmentInteraction(userInfo, currentTag);

    }

    @Override
    public void onFragmentInteraction(UserInfo userInfo, String tag) {
        currentTag = tag;
        this.userInfo = userInfo;
        switch (tag) {
            case Constants.SIGN_UP_DETAIL_FRAGMENT:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, SignUpDetailFragment.newInstance(userInfo), tag)
                        .commit();
                break;
            case Constants.SIGN_UP_PHOTO_FRAGMENT:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, SignUpPhotoFragment.newInstance(userInfo), tag)
                        .addToBackStack(tag)
                        .commit();
                break;
            case Constants.SIGN_UP_OTHERS_FRAGMENT:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, SignUpOthersFragment.newInstance(userInfo), tag)
                        .addToBackStack(tag)
                        .commit();
                break;
            case Constants.NUMBER_VERIFICATION_FRAGMENT:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, NumberVerificationFragment.newInstance(userInfo, Constants.CODE), tag)
                        .addToBackStack(tag)
                        .commit();
                break;
        }
    }

}
