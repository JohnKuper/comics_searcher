package com.korobeinikov.comicsviewer.mvp.presenter;

import android.view.View;

import com.korobeinikov.comicsviewer.model.RealmComicInfo;
import com.korobeinikov.comicsviewer.mvp.view.MainContainerView;
import com.korobeinikov.comicsviewer.realm.ComicRepository;
import com.korobeinikov.comicsviewer.ui.UINavigator.FragmentTag;
import com.korobeinikov.comicsviewer.ui.fragment.SearchFragment;

import io.realm.RealmResults;

import static android.view.View.GONE;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */

public class MainContainerPresenter extends BasePresenter<MainContainerView> {

    private ComicRepository mComicRepository;
    private RealmResults<RealmComicInfo> mRealmComics;

    public MainContainerPresenter(ComicRepository comicRepository) {
        mComicRepository = comicRepository;
    }

    @Override
    public void attachView(MainContainerView view) {
        super.attachView(view);
        mView.getUINavigator().start();
        toggleSearchViewVisibility(mView.getUINavigator().isSearchFragmentOnTop());
        listenForRealmComicsUpdates();
    }

    private void listenForRealmComicsUpdates() {
        mRealmComics = mComicRepository.getAllComics();
        mRealmComics.addChangeListener(result -> mView.updateFavouritesCount(mRealmComics.size()));
        mView.updateFavouritesCount(mRealmComics.size());
    }

    private void toggleSearchViewVisibility(boolean isShown) {
        mView.getSearchView().setVisibility(isShown ? View.VISIBLE : GONE);
    }

    public void onNavMenuItemClicked(@FragmentTag String tag) {
        toggleSearchViewVisibility(tag.equals(SearchFragment.TAG));
        mView.getUINavigator().openFragment(tag);
    }

    public void onSearchSubmit(String query) {
        mView.getUINavigator().getSearchFragment().onSearchSubmit(query);
        mView.getSearchView().close(true);
    }
}
