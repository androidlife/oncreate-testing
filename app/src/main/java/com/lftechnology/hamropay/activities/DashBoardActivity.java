package com.lftechnology.hamropay.activities;

import android.graphics.Color;
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
import com.lftechnology.hamropay.model.TabData;
import com.lftechnology.hamropay.utils.RecyclerViewScrollListener;

import java.util.ArrayList;

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

    private ArrayList<TabData> tabList = new ArrayList<TabData>() {{
        add(new TabData("Recent", true, R.drawable.ic_tab_selected_recent, R.drawable.ic_tab_recent));
        add(new TabData("Contacts", false, R.drawable.ic_tab_selected_contacts, R.drawable.ic_tab_contacts));
    }};

    int tabPosition = 0;

    @Override
    protected String getToolbarTitle() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DashboardPagerAdapter adapter = new DashboardPagerAdapter(getSupportFragmentManager(), tabList);
        viewPager.setAdapter(adapter);
        slidingTabLayout.setupWithViewPager(viewPager);
        initTabBehaviour();
        controlFabVisibility();


    }

    private void initTabBehaviour() {
        for (int i = 0; i < slidingTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = slidingTabLayout.getTabAt(i);
            TabData tabData = tabList.get(i);
            tab.setIcon(tabData.selected ? tabList.get(i).selectedDrawableId : tabList.get(i).unselectedDrawableId);
        }
        slidingTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                tabList.get(tabPosition).selected = true;
                tab.setIcon(tabList.get(tabPosition).selectedDrawableId);
                onDashboardTabChanged(tabPosition);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tabList.get(tab.getPosition()).selected = false;
                tab.setIcon(tabList.get(tab.getPosition()).unselectedDrawableId);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void onDashboardTabChanged(int position) {
        tabPosition = position;
        controlFabVisibility();
        viewPager.setCurrentItem(position);
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
