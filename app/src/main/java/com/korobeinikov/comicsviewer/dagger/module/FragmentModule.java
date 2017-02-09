package com.korobeinikov.comicsviewer.dagger.module;

import com.korobeinikov.comicsviewer.dagger.scope.PerFragment;
import com.korobeinikov.comicsviewer.mvp.presenter.ComicDetailsPresenter;
import com.korobeinikov.comicsviewer.mvp.presenter.FavoritesPresenter;
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
    public ComicDetailsPresenter providesComicDetailsPresenter(ComicRepository comicRepository) {
        return new ComicDetailsPresenter(comicRepository);
    }

    @Provides
    @PerFragment
    public FavoritesPresenter providesFavouritesPresenter() {
        return new FavoritesPresenter();
    }
}
