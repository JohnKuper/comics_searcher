package com.korobeinikov.comicsviewer.dagger;

import com.korobeinikov.comicsviewer.mvp.SearchPresenter;
import com.korobeinikov.comicsviewer.network.ComicRequester;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
@Module
public class ActivityModule {

    @Provides
    @PerActivity
    public SearchPresenter providesSearchPresenter(ComicRequester requester) {
        return new SearchPresenter(requester);
    }
}