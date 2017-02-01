package com.korobeinikov.comicsviewer.mvp.presenter;

import com.korobeinikov.comicsviewer.model.MarvelData;
import com.korobeinikov.comicsviewer.mvp.view.ComicDetailView;
import com.korobeinikov.comicsviewer.realm.ComicRepository;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public class ComicDetailsPresenter extends BasePresenter<ComicDetailView> {

    private ComicRepository mComicRepository;

    public ComicDetailsPresenter(ComicRepository comicRepository) {
        mComicRepository = comicRepository;
    }

    @Override
    public void detachView() {
        mView.resetAnimatedDrawables();
        super.detachView();
    }

    public void onAddToFavouritesClick(MarvelData.ComicInfo comicInfo) {
        mComicRepository.addComic(comicInfo);
        mView.startAnimations();
    }

    public void onGotoComicClick(MarvelData.ComicInfo comicInfo) {
        mView.openComic(comicInfo);
    }

    public void updateCircleButton(int comicId) {
        if (!mComicRepository.isAddedById(comicId)) {
            mView.showAddToFavouritesButton();
        } else {
            mView.hideAddToFavouritesButton();
        }
    }
}
