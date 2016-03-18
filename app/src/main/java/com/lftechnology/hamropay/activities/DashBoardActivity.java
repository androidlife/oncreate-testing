package com.lftechnology.hamropay.activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.activities.base.BaseActivity;
import com.lftechnology.hamropay.adapter.DashboardPagerAdapter;
import com.lftechnology.hamropay.utils.RecyclerViewScrollListener;

import butterknife.Bind;
import butterknife.OnClick;

public class DashBoardActivity extends BaseActivity implements RecyclerViewScrollListener.ScrollListener {

    @Bind(R.id.viewpager)
    ViewPager viewPager;

    @Bind(R.id.sliding_tabs)
    TabLayout slidingTabLayout;

    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dash_board;
    }

    int tabPosition = 0;

    @Override
    protected String getToolbarTitle() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewPager.setAdapter(new DashboardPagerAdapter(getSupportFragmentManager(),
                DashBoardActivity.this));
        slidingTabLayout.setupWithViewPager(viewPager);
        controlFabVisibility();

        slidingTabLayout.getTabAt(0).setIcon(R.drawable.ic_tab_selected_recent);
//        slidingTabLayout.getTabAt(1).setIcon(R.drawable.ic_tab_selected_profile);
        slidingTabLayout.getTabAt(1).setIcon(R.drawable.ic_tab_selected_contacts);
//        slidingTabLayout.getTabAt(3).setIcon(R.drawable.ic_tab_selected_settings);

        slidingTabLayout.getTabAt(0).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
//        slidingTabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#a8a8a8"), PorterDuff.Mode.SRC_IN);
        slidingTabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#a8a8a8"), PorterDuff.Mode.SRC_IN);
//        slidingTabLayout.getTabAt(3).getIcon().setColorFilter(Color.parseColor("#a8a8a8"), PorterDuff.Mode.SRC_IN);


        slidingTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();

                controlFabVisibility();
                tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#a8a8a8"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void controlFabVisibility() {
        switch (tabPosition) {
            case 0:
                floatingActionButton.setVisibility(View.VISIBLE);
                floatingActionButton.setImageDrawable(ContextCompat.getDrawable(DashBoardActivity.this, R.drawable.ic_create_package));
                floatingActionButton.getDrawable().setTint(Color.WHITE);
                break;
            case 1:
                floatingActionButton.setVisibility(View.VISIBLE);
                floatingActionButton.setImageDrawable(ContextCompat.getDrawable(DashBoardActivity.this, R.drawable.ic_person));
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.fab})
    public void onClick(View view) {
        switch (tabPosition) {
            case 0:
                break;
            case 2:
                Toast.makeText(DashBoardActivity.this, "Add Contact", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onScrolled(boolean favVisibility) {
        floatingActionButton.setVisibility(favVisibility ? View.VISIBLE : View.INVISIBLE);
    }
}
