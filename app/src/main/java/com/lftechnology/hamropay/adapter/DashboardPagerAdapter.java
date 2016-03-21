package com.lftechnology.hamropay.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lftechnology.hamropay.fragment.ContactsFragment;
import com.lftechnology.hamropay.fragment.RecentFragment;
import com.lftechnology.hamropay.model.TabData;

import java.util.ArrayList;

/**
 * Created by rajesh on 2/15/16.
 */
public class DashboardPagerAdapter extends FragmentStatePagerAdapter {

    private Fragment fragment;
    private ArrayList<TabData> tabList;

    public DashboardPagerAdapter(FragmentManager fm, ArrayList<TabData> tabList) {
        super(fm);
        this.tabList = tabList;
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
