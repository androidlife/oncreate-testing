package com.lftechnology.hamropay.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.activities.base.BaseActivity;
import com.lftechnology.hamropay.db.PrefManager;
import com.lftechnology.hamropay.widgets.CircleLogoLoader;

import butterknife.Bind;

public class SplashActivity extends BaseActivity implements CircleLogoLoader.OnAnimationCompleteListener {

    @Bind(R.id.iv_logo)
    CircleLogoLoader ivLogo;
    private int count = 2;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected String getToolbarTitle() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(false){
            startActivity(new Intent(this,DashBoardActivity.class));
            this.finish();
            return;
        }
        initPermission();
    }

    @Override
    public void initDbLoadFromAssets() {
        super.initDbLoadFromAssets();
        ivLogo.setOnAnimationCompleteListener(this);
        ivLogo.animateProgress();
    }

    @Override
    public void notifyDbLoaded(boolean success) {
        super.notifyDbLoaded(success);
        if (!success) {
            Toast.makeText(this, getString(R.string.error_loading_db_from_assets), Toast.LENGTH_SHORT).show();
            this.finish();
        } else {
            checkAndNavigateToSecondScreen();
        }
    }

    private void checkAndNavigateToSecondScreen() {
        count--;
        if (count == 0) {
            count = 2;
            if (PrefManager.getInstance().getRegisteredUser() == null) {
                startActivity(new Intent(this, LaunchActivity.class));
                finish();
            } else {
                //go to dashboard activity
                startActivity(new Intent(this, DashBoardActivity.class));
                finish();
            }


        }
    }


    @Override
    public void onAnimationComplete() {
        checkAndNavigateToSecondScreen();
    }
}
