package com.lftechnology.hamropay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.adapter.ContactsPagerAdapter;
import com.lftechnology.hamropay.fragment.base.BaseFragment;

import butterknife.Bind;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsFragment extends BaseFragment {
    private static final String SELECTED_TAB = "selected_tab";
    private int selectedTabIndex;

    @Bind(R.id.contacts_sliding_tab)
    TabLayout contactsSlidingTabLayout;

    @Bind(R.id.contacts_viewPager)
    ViewPager contactsViewPager;

    public ContactsFragment() {
        // Required empty public constructor
    }

    public static ContactsFragment newInstance(int selectedIndex) {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        args.putInt(SELECTED_TAB, selectedIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedTabIndex = getArguments().getInt(SELECTED_TAB);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private ContactsPagerAdapter contactsPagerAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        contactsPagerAdapter = new ContactsPagerAdapter(getFragmentManager(), getActivity());
        contactsViewPager.setAdapter(contactsPagerAdapter);
        contactsSlidingTabLayout.setupWithViewPager(contactsViewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contactsPagerAdapter.removeAllFragments();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_contacts;
    }
}
