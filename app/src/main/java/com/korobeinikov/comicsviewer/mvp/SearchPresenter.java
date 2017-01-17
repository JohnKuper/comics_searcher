package com.korobeinikov.comicsviewer.mvp;

import android.os.SystemClock;
import android.util.Log;

import com.korobeinikov.comicsviewer.model.ComicsResponse;
import com.korobeinikov.comicsviewer.network.MD5HashHelper;
import com.korobeinikov.comicsviewer.network.MarvelService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public class SearchPresenter implements SearchContract.Presenter<SearchContract.View> {

    private static final String TAG = SearchPresenter.class.getSimpleName();

    private SearchContract.View mView;
    private MarvelService mMarvelService;

    public SearchPresenter(MarvelService service) {
        mMarvelService = service;
    }

    @Override
    public void onSearchSubmit() {
        Call<ComicsResponse> comicsResponse = mMarvelService.findComics("Spider", SystemClock.elapsedRealtime(), MD5HashHelper.computeMarvelMD5hash());
        comicsResponse.enqueue(new Callback<ComicsResponse>() {
            @Override
            public void onResponse(Call<ComicsResponse> call, Response<ComicsResponse> response) {
                Log.d(TAG, "onResponse()");
                mView.updateSearchList(response.body().data.results);
            }

            @Override
            public void onFailure(Call<ComicsResponse> call, Throwable t) {
                Log.d(TAG, "onFailure()");
            }
        });
    }

    @Override
    public void setView(SearchContract.View view) {
        mView = view;
    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
