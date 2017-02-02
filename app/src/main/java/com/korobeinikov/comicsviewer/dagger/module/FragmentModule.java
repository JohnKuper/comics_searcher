package com.korobeinikov.comicsviewer.dagger.module;

import com.korobeinikov.comicsviewer.dagger.scope.PerFragment;
import com.korobeinikov.comicsviewer.model.MarvelData;
import com.korobeinikov.comicsviewer.mvp.presenter.ComicDetailsPresenter;
import com.korobeinikov.comicsviewer.mvp.presenter.FavoritesPresenter;
import com.korobeinikov.comicsviewer.mvp.presenter.SearchPresenter;
import com.korobeinikov.comicsviewer.network.ComicsRequester;
import com.korobeinikov.comicsviewer.realm.ComicRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Dmitriy_Korobeinikov.
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
    public FavoritesPresenter providesFavouritesPresenter() {
        return new FavoritesPresenter();
    }

    @Provides
    public MarvelData providesMarvelData() {
        return new MarvelData();
    }
}
