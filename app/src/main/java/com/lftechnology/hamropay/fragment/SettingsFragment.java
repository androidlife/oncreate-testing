package com.lftechnology.hamropay.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.fragment.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends BaseFragment {

//    @Bind(R.id.switch_host)
//    SwitchCompat switchHost;
//    @Bind(R.id.ll_settings_connectivity)
//    LinearLayout llSettingsConnectivity;

    private int mPage;
    private boolean isHost;

    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        isHost = PrefManager.getInstance().isHost();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        switchHost.setChecked(isHost);
//        switchHost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                isHost = isChecked;
//                updatePref();
//            }
//        });
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_settings;
    }

    /*@OnClick(R.id.ll_settings_connectivity)
    public void onClick(View view) {
        if (isHost) {
            isHost = false;
        } else {
            isHost = true;
        }
        updatePref();
        switchHost.setChecked(isHost);
    }*/

//    private void updatePref() {
//        PrefManager.getInstance().setHost(isHost);
//    }

}
