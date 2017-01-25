package com.korobeinikov.comicsviewer.dagger.module;

import com.korobeinikov.comicsviewer.dagger.scope.PerActivity;
import com.korobeinikov.comicsviewer.model.MarvelData;
import com.korobeinikov.comicsviewer.mvp.presenter.SearchPresenter;
import com.korobeinikov.comicsviewer.network.ComicsRequester;
import com.korobeinikov.comicsviewer.realm.ComicRepository;

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
    public SearchPresenter providesSearchPresenter(ComicsRequester requester, ComicRepository repository, MarvelData marvelData) {
        return new SearchPresenter(requester, repository, marvelData);
    }
}