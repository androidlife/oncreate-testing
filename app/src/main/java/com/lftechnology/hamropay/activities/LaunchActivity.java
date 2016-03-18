package com.lftechnology.hamropay.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lftechnology.hamropay.HamroPayApplication;
import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.activities.base.BaseActivity;
import com.lftechnology.hamropay.utils.ResourceUtils;

import butterknife.Bind;
import butterknife.OnClick;

public class LaunchActivity extends BaseActivity {

    @Bind(R.id.tv_tagline)
    TextView tvTagline;
    @Bind(R.id.tv_welcome)
    TextView tvWelcome;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected String getToolbarTitle() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ResourceUtils.getScreenHeight() < 1800) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.setMargins(0, ResourceUtils.dpToPixels(40), 0, 0);
            tvWelcome.setLayoutParams(layoutParams);

        }
        tvTagline.setTypeface(HamroPayApplication.getBlackJackFont());
    }

    @OnClick({R.id.tv_sign_up, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sign_up:
                startActivity(new Intent(LaunchActivity.this, SignUpActivity.class));
                finish();
                break;
            case R.id.btn_login:
                // TEMP navigation; login interface
                startActivity(new Intent(LaunchActivity.this, SignUpActivity.class));
                finish();
                break;
        }
    }

}
