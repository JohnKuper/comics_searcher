package com.korobeinikov.comicsviewer.mvp.presenter;

import com.korobeinikov.comicsviewer.mvp.view.ComicDetailView;

import java.util.Random;

import static com.korobeinikov.comicsviewer.util.VersionHelper.isMarshmallow;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */

public class ComicDetailsPresenter extends BasePresenter<ComicDetailView> {

    public void onAddToFavouritesClick() {
        if (isMarshmallow()) {
            mView.startAnimationsAfterM();
        } else {
            mView.startAnimationsBeforeM();
        }
    }

    public void onGotoComicClick() {
        mView.openComic();
    }

    public void updateCircleButton() {
        if (!isFavourite()) {
            mView.showAddToFavouritesButton();
        } else {
            mView.hideAddToFavouritesButton();
        }
    }

    @Override
    public void detachView() {
        mView.resetAnimatedDrawables();
        super.detachView();
    }

    //// TODO: 1/23/2017 Change to real request to database
    private boolean isFavourite() {
        return new Random().nextBoolean();
    }
}
