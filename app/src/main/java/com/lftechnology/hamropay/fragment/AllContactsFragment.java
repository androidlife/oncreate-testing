package com.lftechnology.hamropay.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.adapter.ContactsRecyclerViewAdapter;
import com.lftechnology.hamropay.db.DbManager;
import com.lftechnology.hamropay.db.models.User;
import com.lftechnology.hamropay.fragment.base.ContactBaseFragment;
import com.lftechnology.hamropay.utils.RecyclerViewScrollListener;

import butterknife.Bind;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllContactsFragment extends ContactBaseFragment {

    @Bind(R.id.rv_all_contacts)
    RecyclerView rvAllContacts;

    ContactsRecyclerViewAdapter contactsRecyclerViewAdapter;

    public AllContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvAllContacts.setLayoutManager(layoutManager);
        contactsRecyclerViewAdapter = new ContactsRecyclerViewAdapter(getActivity(), getData());
        rvAllContacts.setAdapter(contactsRecyclerViewAdapter);
        rvAllContacts.addOnScrollListener(new RecyclerViewScrollListener((RecyclerViewScrollListener.ScrollListener) getActivity()));
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_all_contacts;
    }

    private RealmResults<User> getData() {
        return DbManager.getInstance().getAllUsers();
    }

}
