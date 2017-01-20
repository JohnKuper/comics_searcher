package com.korobeinikov.comicsviewer.mvp;

import com.korobeinikov.comicsviewer.model.ComicsResponse;
import com.korobeinikov.comicsviewer.model.MarvelData;
import com.korobeinikov.comicsviewer.network.ComicRequester;

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
    private ComicRequester mComicRequester;
    private Observable<ComicsResponse> mComicRequestCache;
    private Subscription mSubscription;

    public SearchPresenter(ComicRequester requester) {
        mComicRequester = requester;
    }

    @Override
    public void onResume(SearchContract.View view) {
        mView = view;
        if (mComicRequestCache != null) {
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

    @Override
    public void onSearchSubmit(String keyword) {
        mComicRequestCache = mComicRequester.findComicsByKeyword(keyword).cache();
        startRequest();
    }

    private void startRequest() {
        mView.showProgress(true);
        mSubscription = mComicRequestCache.subscribe(mComicsObserver);
    }

    @Override
    public void onListItemClick(MarvelData.Result result) {
        mView.openDetailedInformation(result);
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
            mView.swapResults(response.data.results);
        }
    };
}
