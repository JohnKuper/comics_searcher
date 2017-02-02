package com.korobeinikov.comicsviewer.mvp.presenter;

import com.korobeinikov.comicsviewer.model.RealmComicInfo;
import com.korobeinikov.comicsviewer.mvp.view.MainContainerView;
import com.korobeinikov.comicsviewer.realm.ComicRepository;
import com.korobeinikov.comicsviewer.ui.UINavigator.FragmentTag;

import io.realm.RealmResults;

/**
 * Created by Dmitriy_Korobeinikov.
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
        mView.loadMainScreen();
        listenForRealmComicsUpdates();
    }

    @Override
    public void detachView() {
        super.detachView();
        mRealmComics.removeChangeListeners();
    }

    private void listenForRealmComicsUpdates() {
        mRealmComics = mComicRepository.getAllComics();
        mRealmComics.addChangeListener(element -> mView.updateFavouritesCount(mRealmComics.size()));
        mView.updateFavouritesCount(mRealmComics.size());
    }

    public void onNavMenuItemClicked(@FragmentTag String tag) {
        mView.openFragment(tag);
    }

    public void onSearchSubmit(String query) {
        mView.handleQuery(query);
        mView.closeSearchView();
    }
}
