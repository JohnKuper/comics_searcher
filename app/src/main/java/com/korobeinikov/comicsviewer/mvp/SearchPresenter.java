package com.korobeinikov.comicsviewer.mvp;

import com.korobeinikov.comicsviewer.model.ComicsResponse;
import com.korobeinikov.comicsviewer.model.MarvelData;
import com.korobeinikov.comicsviewer.network.ComicRequester;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public class SearchPresenter implements SearchContract.Presenter, Callback<ComicsResponse> {

    private static final String TAG = SearchPresenter.class.getSimpleName();

    private SearchContract.View mView;
    private ComicRequester mComicRequester;

    public SearchPresenter(ComicRequester requester) {
        mComicRequester = requester;
    }

    @Override
    public void onSearchSubmit(String keyword) {
        mView.showProgress(true);
        mComicRequester.findComicsByKeyword(keyword, this);
    }

    @Override
    public void onResponse(Call<ComicsResponse> call, Response<ComicsResponse> response) {
        mView.showProgress(false);
        if (response.body() != null && response.body().data != null) {
            mView.updateSearchList(response.body().data.results);
        }
    }

    @Override
    public void onFailure(Call<ComicsResponse> call, Throwable t) {
        mView.showProgress(false);
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
