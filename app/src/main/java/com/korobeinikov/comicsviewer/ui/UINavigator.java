package com.korobeinikov.comicsviewer.ui;

import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.korobeinikov.comicsviewer.R;
import com.korobeinikov.comicsviewer.ui.fragment.FavouritesFragment;
import com.korobeinikov.comicsviewer.ui.fragment.SearchFragment;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */

// TODO: 1/30/2017 Change fragments in a more beautiful way according to the state of NavigationView
public class UINavigator {

    @StringDef({SearchFragment.TAG, FavouritesFragment.TAG})
    public @interface Tag {
    }

    private FragmentManager mFragmentManager;

    public UINavigator(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    public void start() {
        if (mFragmentManager.findFragmentById(R.id.container) == null) {
            mFragmentManager.beginTransaction().add(R.id.container, SearchFragment.newInstance(), SearchFragment.TAG).commit();
        }
    }

    public void openFragment(@Tag String fragmentTag) {
        Fragment fragment = null;
        switch (fragmentTag) {
            case SearchFragment.TAG:
                fragment = SearchFragment.newInstance();
                break;
            case FavouritesFragment.TAG:
                fragment = FavouritesFragment.newInstance();
                break;
        }
        mFragmentManager.beginTransaction().replace(R.id.container, fragment, fragmentTag).commit();
    }

    public SearchFragment getSearchFragment() {
        return (SearchFragment) mFragmentManager.findFragmentByTag(SearchFragment.TAG);
    }
}