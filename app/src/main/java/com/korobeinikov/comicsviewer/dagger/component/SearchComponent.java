package com.korobeinikov.comicsviewer.dagger.component;

import com.korobeinikov.comicsviewer.dagger.scope.PerFragment;
import com.korobeinikov.comicsviewer.ui.fragment.SearchFragment;

import dagger.Subcomponent;

/**
 * Created by Dmitriy_Korobeinikov.
 */
@PerFragment
@Subcomponent
public interface SearchComponent {
    void inject(SearchFragment searchFragment);
}
