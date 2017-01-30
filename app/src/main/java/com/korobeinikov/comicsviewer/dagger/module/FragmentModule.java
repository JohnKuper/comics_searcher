package com.korobeinikov.comicsviewer.dagger.module;

import com.korobeinikov.comicsviewer.dagger.scope.PerFragment;
import com.korobeinikov.comicsviewer.model.MarvelData;
import com.korobeinikov.comicsviewer.mvp.presenter.ComicDetailsPresenter;
import com.korobeinikov.comicsviewer.mvp.presenter.FavouritesPresenter;
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
public class FragmentModule {

    @Provides
    @PerFragment
    public SearchPresenter providesSearchPresenter(ComicsRequester requester, ComicRepository repository, MarvelData marvelData) {
        return new SearchPresenter(requester, repository, marvelData);
    }

    @Provides
    @PerFragment
    public ComicDetailsPresenter providesComicDetailsPresenter(ComicRepository comicRepository) {
        return new ComicDetailsPresenter(comicRepository);
    }

    @Provides
    @PerFragment
    public FavouritesPresenter providesFavouritesPresenter() {
        return new FavouritesPresenter();
    }

    @Provides
    public MarvelData providesMarvelData() {
        return new MarvelData();
    }
}
