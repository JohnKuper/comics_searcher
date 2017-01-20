package com.korobeinikov.comicsviewer.network;

import android.os.SystemClock;

import com.korobeinikov.comicsviewer.model.ComicsResponse;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.korobeinikov.comicsviewer.util.MD5HashHelper.computeMarvelMD5hash;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public class ComicsRequester {

    private MarvelService mMarvelService;
    private ComicsResponse mLastResponse;

    private boolean mIsLoading;

    public ComicsRequester(MarvelService marvelService) {
        mMarvelService = marvelService;
    }

    public Observable<ComicsResponse> findComicsByKeyword(String keyword) {
        long timeStamp = SystemClock.elapsedRealtime();
        int offset = mLastResponse == null ? 0 : mLastResponse.data.getNextOffset();
        return mMarvelService.findComics(keyword, timeStamp, computeMarvelMD5hash(timeStamp), offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mIsLoading = true;
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        mIsLoading = false;
                    }
                })
                .doOnNext(new Action1<ComicsResponse>() {
                    @Override
                    public void call(ComicsResponse response) {
                        mLastResponse = response;
                    }
                });
    }

    public void clearState() {
        mLastResponse = null;
        mIsLoading = false;
    }

    public boolean isLoading() {
        return mIsLoading;
    }

    public boolean hasMoreData() {
        return mLastResponse != null && mLastResponse.data.hasMoreData();
    }
}
