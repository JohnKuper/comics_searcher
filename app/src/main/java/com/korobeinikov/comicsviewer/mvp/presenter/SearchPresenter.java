package com.korobeinikov.comicsviewer.mvp.presenter;

import com.korobeinikov.comicsviewer.model.ComicsResponse;
import com.korobeinikov.comicsviewer.model.MarvelData;
import com.korobeinikov.comicsviewer.mvp.view.SearchListView;
import com.korobeinikov.comicsviewer.network.ComicsRequester;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public class SearchPresenter extends BasePresenter<SearchListView> {

    private static final String TAG = "SearchPresenter";

    private ComicsRequester mComicsRequester;
    private Observable<ComicsResponse> mCachedRequest;
    private Subscription mSubscription;

    private MarvelData mFetchedMarvelData;
    private String mLastKeyword;

    public SearchPresenter(ComicsRequester requester, MarvelData marvelData) {
        mComicsRequester = requester;
        mFetchedMarvelData = marvelData;
    }

    @Override
    public void attachView(SearchListView view) {
        super.attachView(view);
        if (mFetchedMarvelData.results.size() > 0) {
            mView.refreshResults(mFetchedMarvelData);
        }
        if (mComicsRequester.isLoading()) {
            subscribeForComics();
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    private void subscribeForComics() {
        if (mFetchedMarvelData.offset == 0) {
            mView.showProgress(true);
        }
        mSubscription = mCachedRequest.subscribe(mComicsObserver);
    }

    public void onSearchSubmit(String keyword) {
        mLastKeyword = keyword;
        mFetchedMarvelData.clear();
        mCachedRequest = mComicsRequester.findComicsByKeyword(keyword, 0).cache();
        subscribeForComics();
    }

    public void onListItemClick(MarvelData.Result result) {
        mView.openDetailedInformation(result);
    }

    public void onListBottomReached() {
        if (!mComicsRequester.isLoading() && mFetchedMarvelData.hasMoreData()) {
            mCachedRequest = mComicsRequester.findComicsByKeyword(mLastKeyword, mFetchedMarvelData.getNextOffset()).cache();
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
            MarvelData freshData = response.data;
            if (response.data.offset == 0) {
                mFetchedMarvelData.swapResults(freshData);
                mView.refreshResults(freshData);
            } else {
                mFetchedMarvelData.merge(freshData);
                mView.addResults(freshData);
            }
        }
    };
}