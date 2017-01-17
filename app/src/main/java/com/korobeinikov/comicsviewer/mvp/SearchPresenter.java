package com.korobeinikov.comicsviewer.mvp;

import com.korobeinikov.comicsviewer.network.MarvelService;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public class SearchPresenter implements SearchContract.Presenter<SearchContract.View> {

    private SearchContract.View mView;
    private MarvelService mMarvelService;

    public SearchPresenter(MarvelService service) {
        mMarvelService = service;
    }

    @Override
    public void onSearchSubmit() {

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
