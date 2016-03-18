package com.lftechnology.hamropay.adapter;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lftechnology.hamropay.fragment.ContactsFragment;
import com.lftechnology.hamropay.fragment.RecentFragment;
import com.lftechnology.hamropay.model.TabData;

import java.util.ArrayList;

/**
 * Created by rajesh on 2/15/16.
 */
public class DashboardPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    private Fragment fragment;
    private TabLayout tabLayout;
    private ArrayList<TabData> tabList;
    private OnDashBoardTabChangeListener listener;

    public interface OnDashBoardTabChangeListener {
        void onDashboardTabChanged(int position);
    }

    public DashboardPagerAdapter(FragmentManager fm, ArrayList<TabData> tabList) {
        super(fm);
        this.tabList = tabList;

    }

    public void setOnDashboardTabChangeListener(OnDashBoardTabChangeListener listener) {
        this.listener = listener;
    }

    public void initTabs(TabLayout tabLayout, OnDashBoardTabChangeListener listener) {
        this.tabLayout = tabLayout;
        setOnDashboardTabChangeListener(listener);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            TabData tabData = tabList.get(i);
            tab.setIcon(tabData.selected ? tabList.get(i).selectedDrawableId : tabList.get(i).unselectedDrawableId);
        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                tabList.get(tabPosition).selected = true;
                tab.setIcon(tabList.get(tabPosition).selectedDrawableId);
                if (DashboardPagerAdapter.this.listener != null)
                    DashboardPagerAdapter.this.listener.onDashboardTabChanged(tabPosition);
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

    @Override
    public int getCount() {
        return tabList.size();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                fragment = RecentFragment.newInstance(null);
                break;
            case 1:
                fragment = ContactsFragment.newInstance(0);
                break;
        }
        return fragment;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
