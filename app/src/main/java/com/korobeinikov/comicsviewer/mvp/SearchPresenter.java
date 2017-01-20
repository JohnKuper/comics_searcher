package com.korobeinikov.comicsviewer.mvp;

import com.korobeinikov.comicsviewer.model.ComicsResponse;
import com.korobeinikov.comicsviewer.model.MarvelData;
import com.korobeinikov.comicsviewer.network.ComicsRequester;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public class SearchPresenter implements SearchContract.Presenter {

    private static final String TAG = SearchPresenter.class.getSimpleName();

    private SearchContract.View mView;
    private ComicsRequester mComicsRequester;
    private Observable<ComicsResponse> mCachedRequest;
    private Subscription mSubscription;
    private String mLastKeyword;

    public SearchPresenter(ComicsRequester requester) {
        mComicsRequester = requester;
    }

    @Override
    public void onResume(SearchContract.View view) {
        mView = view;
        if (mCachedRequest != null) {
            startRequest();
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    private void startRequest() {
        mView.showProgress(true);
        mSubscription = mCachedRequest.subscribe(mComicsObserver);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Events from View
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onSearchSubmit(String keyword) {
        mLastKeyword = keyword;
        mComicsRequester.clearState();
        mCachedRequest = mComicsRequester.findComicsByKeyword(keyword).cache();
        startRequest();
    }

    @Override
    public void onListItemClick(MarvelData.Result result) {
        mView.openDetailedInformation(result);
    }

    @Override
    public void onListBottomReached() {
        if (!mComicsRequester.isLoading() && mComicsRequester.hasMoreData()) {
            mCachedRequest = mComicsRequester.findComicsByKeyword(mLastKeyword).cache();
            mSubscription = mCachedRequest.subscribe(mComicsObserver);
        }
    }

    private final Observer<ComicsResponse> mComicsObserver = new Observer<ComicsResponse>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            mView.showProgress(false);
        }

        @Override
        public void onNext(ComicsResponse response) {
            mView.showProgress(false);
            MarvelData marvelData = response.data;
            if (marvelData.offset == 0) {
                mView.swapResults(marvelData);
            } else {
                mView.addResults(marvelData);
            }
        }
    };
}
