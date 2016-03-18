package com.lftechnology.hamropay.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * {@link FragmentPagerAdapter} for sticker view pager
 */
public class StickerViewPagerAdapter extends FragmentStatePagerAdapter {

    public StickerViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return new StickerFragment();
    }

    @Override
    public int getCount() {
        return 1;
    }
}
