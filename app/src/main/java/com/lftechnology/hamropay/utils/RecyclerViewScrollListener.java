package com.lftechnology.hamropay.utils;

import android.support.v7.widget.RecyclerView;

/**
 * Created by rajesh on 2/19/16.
 */
public class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    ScrollListener scrollListener;

    public RecyclerViewScrollListener(ScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        scrollListener.onScrolled(dy > 0 ? false : true);
    }

    public interface ScrollListener {
        void onScrolled(boolean favVisibility);
    }
}
