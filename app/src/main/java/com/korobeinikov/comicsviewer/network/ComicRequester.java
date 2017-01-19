package com.korobeinikov.comicsviewer.network;

import android.os.SystemClock;

import com.korobeinikov.comicsviewer.model.ComicsResponse;
import com.korobeinikov.comicsviewer.util.MD5HashHelper;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public class ComicRequester {

    private MarvelService mMarvelService;

    public ComicRequester(MarvelService marvelService) {
        mMarvelService = marvelService;
    }

    public void findComicsByKeyword(String keyword, Callback<ComicsResponse> callback) {
        Call<ComicsResponse> comicsResponse = mMarvelService.findComics(keyword, SystemClock.elapsedRealtime()
                , MD5HashHelper.computeMarvelMD5hash());
        comicsResponse.enqueue(callback);
    }
}
