package com.lftechnology.hamropay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lftechnology.hamropay.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ManasShrestha on 2/18/16.
 */
public class StickerFragment extends Fragment {
    @Bind(R.id.rv_stickers)
    RecyclerView rvStickers;

    int stickerType;

    public StickerFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sticker_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("====", "onViewCreatedFragmnet");
        rvStickers.setAdapter(new StickerAdapter(getActivity(),stickerType));
        rvStickers.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("====","fragment destroy");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("====", "on resume");
    }
}
