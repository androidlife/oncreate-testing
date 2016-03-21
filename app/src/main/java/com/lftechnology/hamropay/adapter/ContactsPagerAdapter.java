package com.lftechnology.hamropay.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lftechnology.hamropay.fragment.AllContactsFragment;
import com.lftechnology.hamropay.fragment.FrequentsFragment;
import com.lftechnology.hamropay.fragment.ServicesFragment;

/**
 * Created by rajesh on 2/16/16.
 */
public class ContactsPagerAdapter extends FragmentStatePagerAdapter {
    private String tabTitles[] = new String[]{"FREQUENTS", "ALL CONTACTS", "SERVICES"};

    private FragmentManager fm;

    public ContactsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.fm = fm;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FrequentsFragment();
                break;
            case 1:
                fragment = new AllContactsFragment();
                break;
            case 2:
                fragment = new ServicesFragment();
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}
