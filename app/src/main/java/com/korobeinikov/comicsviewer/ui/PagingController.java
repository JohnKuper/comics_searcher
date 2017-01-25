package com.korobeinikov.comicsviewer.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */

public class PagingController {

    private static final int LOADING_THRESHOLD = 5;

    private Callbacks mCallbacks;

    private PagingController(RecyclerView recyclerView, Callbacks callbacks) {
        mCallbacks = callbacks;
        recyclerView.addOnScrollListener(mOnScrollListener);
    }

    public static void connectTo(RecyclerView recyclerView, Callbacks callbacks) {
        new PagingController(recyclerView, callbacks);
    }

    @SuppressWarnings("FieldCanBeLocal")
    private final RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int totalItemCount = recyclerView.getLayoutManager().getItemCount();
            int lastVisiblePosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            if ((totalItemCount - lastVisiblePosition) <= LOADING_THRESHOLD) {
                if (!mCallbacks.isLoading() && mCallbacks.hasMoreData()) {
                    mCallbacks.onLoadMore();
                }
            }
        }
    };

    public interface Callbacks {
        void onLoadMore();

        boolean isLoading();

        boolean hasMoreData();
    }
}