package com.korobeinikov.comicsviewer.mvp.view;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */

public interface ComicDetailView {

    void openComic();

    void showAddToFavouritesButton();

    void hideAddToFavouritesButton();

    void resetAnimatedDrawables();

    void startAnimationsAfterM();

    void startAnimationsBeforeM();
}
