package com.korobeinikov.comicsviewer.dagger.component;

import com.korobeinikov.comicsviewer.dagger.module.FragmentModule;
import com.korobeinikov.comicsviewer.dagger.scope.PerFragment;
import com.korobeinikov.comicsviewer.ui.fragment.ComicDetailsFragment;
import com.korobeinikov.comicsviewer.ui.fragment.FavouritesFragment;
import com.korobeinikov.comicsviewer.ui.fragment.SearchFragment;

import dagger.Subcomponent;

/**
 * Created by Dmitriy_Korobeinikov.
 */

@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(SearchFragment searchFragment);

    void inject(ComicDetailsFragment comicDetailDialogFragment);

    void inject(FavouritesFragment fragmentFavourites);

}
