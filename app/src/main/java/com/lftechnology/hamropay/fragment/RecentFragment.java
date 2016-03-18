package com.lftechnology.hamropay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.adapter.RecentAdapter;
import com.lftechnology.hamropay.db.DbManager;
import com.lftechnology.hamropay.db.models.Transaction;
import com.lftechnology.hamropay.db.models.TransactionListItem;
import com.lftechnology.hamropay.fragment.base.BaseFragment;
import com.lftechnology.hamropay.utils.RecyclerViewScrollListener;

import java.util.ArrayList;

import butterknife.Bind;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link RecentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecentFragment extends BaseFragment {
    private static final String FRAGMENT_INDICATOR = "fragment_indicator";
    private String fragmentIndicator;

    @Bind(R.id.recent_recycler_view)
    RecyclerView recentRecyclerView;

    RecentAdapter recentAdapter;

    @Bind(R.id.empty_layout)
    RelativeLayout emptyLayout;

    RealmResults<Transaction> transactions;

    public RecentFragment() {
        // Required empty public constructor
    }


    public static RecentFragment newInstance(String fragmentIndicator) {
        RecentFragment fragment = new RecentFragment();
        Bundle args = new Bundle();
        args.putString(FRAGMENT_INDICATOR, fragmentIndicator);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragmentIndicator = getArguments().getString(FRAGMENT_INDICATOR);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        ArrayList<TransactionListItem> listItems = DbManager.getInstance().getUsersHavingTransaction();
        if (listItems.size() > 0) {
            emptyLayout.setVisibility(View.GONE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recentRecyclerView.setLayoutManager(layoutManager);
            recentAdapter = new RecentAdapter(getActivity(), listItems);
            recentRecyclerView.setAdapter(recentAdapter);
            recentRecyclerView.addOnScrollListener(new RecyclerViewScrollListener((RecyclerViewScrollListener.ScrollListener) getActivity()));
        } else {
            emptyLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_recent;
    }


}
