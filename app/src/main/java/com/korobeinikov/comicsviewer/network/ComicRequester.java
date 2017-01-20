package com.korobeinikov.comicsviewer.network;

import android.os.SystemClock;

import com.korobeinikov.comicsviewer.model.ComicsResponse;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.korobeinikov.comicsviewer.util.MD5HashHelper.computeMarvelMD5hash;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public class ComicRequester {

    private MarvelService mMarvelService;

    public ComicRequester(MarvelService marvelService) {
        mMarvelService = marvelService;
    }

    public Observable<ComicsResponse> findComicsByKeyword(String keyword) {
        long timeStamp = SystemClock.elapsedRealtime();
        return mMarvelService.findComics(keyword, timeStamp, computeMarvelMD5hash(timeStamp))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
