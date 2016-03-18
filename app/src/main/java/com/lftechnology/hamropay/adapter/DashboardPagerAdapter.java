package com.lftechnology.hamropay.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.fragment.ContactFragment;
import com.lftechnology.hamropay.fragment.ContactsFragment;
import com.lftechnology.hamropay.fragment.RecentFragment;
import com.lftechnology.hamropay.fragment.SettingsFragment;

/**
 * Created by rajesh on 2/15/16.
 */
public class DashboardPagerAdapter extends FragmentPagerAdapter {
    private int[] imageResId = {
            R.drawable.ic_tab_recent,
            R.drawable.ic_tab_contacts,
            R.drawable.ic_tab_profile,
            R.drawable.ic_tab_settings
    };
    final int PAGE_COUNT = 2;
    private Context context;
    private Fragment fragment;

    public DashboardPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
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
