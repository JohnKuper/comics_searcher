package com.korobeinikov.comicsviewer.mvp;

import android.os.SystemClock;
import android.util.Log;

import com.korobeinikov.comicsviewer.model.ComicsResponse;
import com.korobeinikov.comicsviewer.model.MarvelData;
import com.korobeinikov.comicsviewer.network.MarvelService;
import com.korobeinikov.comicsviewer.util.MD5HashHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public class SearchPresenter implements SearchContract.Presenter {

    private static final String TAG = SearchPresenter.class.getSimpleName();

    private SearchContract.View mView;
    private MarvelService mMarvelService;

    public SearchPresenter(MarvelService service) {
        mMarvelService = service;
    }

    @Override
    public void onSearchSubmit(String query) {
        mView.showProgress(true);
        Call<ComicsResponse> comicsResponse = mMarvelService.findComics(query, SystemClock.elapsedRealtime()
                , MD5HashHelper.computeMarvelMD5hash());

        comicsResponse.enqueue(new Callback<ComicsResponse>() {
            @Override
            public void onResponse(Call<ComicsResponse> call, Response<ComicsResponse> response) {
                Log.d(TAG, "onResponse()");
                mView.showProgress(false);
                if (response.body() != null && response.body().data != null) {
                    mView.updateSearchList(response.body().data.results);
                }
            }

            @Override
            public void onFailure(Call<ComicsResponse> call, Throwable t) {
                Log.d(TAG, "onFailure()");
                mView.showProgress(false);
            }
        });
    }

    @Override
    public void onListItemClick(MarvelData.Result result) {
        mView.openDetailedInformation(result);
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
