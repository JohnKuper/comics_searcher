package com.korobeinikov.comicsviewer.dagger;

import com.korobeinikov.comicsviewer.mvp.SearchPresenter;
import com.korobeinikov.comicsviewer.network.MarvelService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
@Module
public class ActivityModule {

    @Provides
    public SearchPresenter providesSearchPresenter(MarvelService service) {
        return new SearchPresenter(service);
    }
}