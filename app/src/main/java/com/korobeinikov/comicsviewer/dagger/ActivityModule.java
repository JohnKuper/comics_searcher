package com.korobeinikov.comicsviewer.dagger;

import com.korobeinikov.comicsviewer.model.MarvelData;
import com.korobeinikov.comicsviewer.mvp.SearchPresenter;
import com.korobeinikov.comicsviewer.network.ComicsRequester;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
@Module
public class ActivityModule {

    @Provides
    public MarvelData providesMarvelData() {
        return new MarvelData();
    }

    @Provides
    @PerActivity
    public SearchPresenter providesSearchPresenter(ComicsRequester requester, MarvelData marvelData) {
        return new SearchPresenter(requester, marvelData);
    }
}