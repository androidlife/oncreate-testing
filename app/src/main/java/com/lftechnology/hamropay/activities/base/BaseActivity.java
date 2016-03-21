package com.lftechnology.hamropay.activities.base;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.canelmas.let.AskPermission;
import com.canelmas.let.DeniedPermission;
import com.canelmas.let.Let;
import com.canelmas.let.RuntimePermissionListener;
import com.canelmas.let.RuntimePermissionRequest;
import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.db.DbManager;
import com.lftechnology.hamropay.db.OnDbLoadedFromAssets;
import com.lftechnology.hamropay.interfaces.ToolBarClickListener;

import java.util.List;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 */
public abstract class BaseActivity extends AppCompatActivity implements RuntimePermissionListener {


    private ToolBarClickListener toolBarClickListener;
    public Toolbar toolBar;

    protected abstract int getLayoutId();

    protected abstract String getToolbarTitle();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        configureToolbar();
        ButterKnife.bind(this);
        registerToDb(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Timber.d("onResume()");
        registerToDb(true);
    }


    private void registerToDb(boolean register) {
        if (register) {
            DbManager.getInstance().register();
        } else {
            DbManager.getInstance().unregister();
        }
    }

    @Override
    protected void onPause() {
        registerToDb(false);
        super.onPause();
    }

    /**
     * implement {@link Toolbar} to the child Activities
     */
    private void configureToolbar() {
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        if (toolBar != null) {
            setSupportActionBar(toolBar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            String title = getToolbarTitle();
            if (title != null) {
                TextView txtToolBarTitle = (TextView) toolBar.findViewById(R.id.toolBarTitle);
                txtToolBarTitle.setText(title);

            }
        }
    }

    /**
     * Set icon in the toolbar for {@link com.lftechnology.hamropay.activities.AddBankActivity} and if required for another activities and perform click action
     *
     * @param imagePath path of the icon that needs to be shown
     */
    public void setToolbarImage(int imagePath) {

        final ImageView toolBarImage = (ImageView) toolBar.findViewById(R.id.img_send_bank_data);

        if (imagePath == 0) {
            toolBarImage.setVisibility(View.GONE);
            return;

        }
        toolBarImage.setBackgroundResource(imagePath);
        toolBarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toolBarClickListener != null) {
                    toolBarClickListener.onToolbarClicked();
                }
            }
        });


    }

    /**
     * set title to {@link Toolbar}
     *
     * @param toolBarTitle String to be set as title in toolBar
     */
    @Nullable
    public void setToolBarTitle(String toolBarTitle) {
        if (toolBar != null) {
            TextView txtToolBarTitle = (TextView) toolBar.findViewById(R.id.toolBarTitle);
            txtToolBarTitle.setText(toolBarTitle);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setToolBarClickListener(ToolBarClickListener toolBarClickListener) {
        this.toolBarClickListener = toolBarClickListener;
    }

    //All set for loading db from assets

    private OnDbLoadedFromAssets onDbLoadedFromAssets = new OnDbLoadedFromAssets() {
        @Override
        public void notifyDbLoadedFromAssets(boolean success) {
            notifyDbLoaded(success);
        }
    };

    private void checkIfDbLoadedFromAssets() {
        registerToDb(true);
        initDbLoadFromAssets();
        DbManager.getInstance().checkIfDbIsLoadedFromAssets(onDbLoadedFromAssets);
    }

    public void initDbLoadFromAssets() {
        // init progress bar
        Timber.d("Db load from asset start");
    }

    public void notifyDbLoaded(boolean success) {
        //do whateever you want with this , go to next activity or exit
        Timber.d("Db loaded from assets = %b", success);
    }

    //start with permission check
    @AskPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.GET_ACCOUNTS})
    public void initPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PermissionChecker.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PermissionChecker.PERMISSION_GRANTED) {
            checkIfDbLoadedFromAssets();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Let.handle(this, requestCode, permissions, grantResults);
        //right now not checking whether permission is granted or not
        //checkIfDbLoadedFromAssets();
        //Timber.d("onRequestPermission result");
    }

    @Override
    public void onShowPermissionRationale(List<String> permissions, final RuntimePermissionRequest request) {
        /**
         * you may show permission rationales in a dialog, wait for user confirmation and retry the permission
         * request by calling request.retry()
         */
    }

    @Override
    public void onPermissionDenied(List<DeniedPermission> deniedPermissionList) {
        /**
         * Do whatever you need to do about denied permissions:
         *   - update UI
         *   - if permission is denied with 'Never Ask Again', prompt a dialog to tell user
         *   to go to the app settings screen in order to grant again the permission denied
         */
    }


}
