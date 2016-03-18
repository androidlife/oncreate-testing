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
public class FrequentsFragment extends ContactBaseFragment {

    @Bind(R.id.frequent_recyclerView)
    RecyclerView frequentRecyclerView;

    ContactsRecyclerViewAdapter contactsRecyclerViewAdapter;

    public FrequentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        frequentRecyclerView.setLayoutManager(layoutManager);
        contactsRecyclerViewAdapter = new ContactsRecyclerViewAdapter(getActivity(), getData());
        frequentRecyclerView.setAdapter(contactsRecyclerViewAdapter);
        frequentRecyclerView.addOnScrollListener(new RecyclerViewScrollListener((RecyclerViewScrollListener.ScrollListener) getActivity()));
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_frequents;
    }

    private RealmResults<User> getData() {
        return DbManager.getInstance().getFrequentUsers();
    }

}
