package com.korobeinikov.comicsviewer.mvp.view;

import com.korobeinikov.comicsviewer.model.MarvelData;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */

public interface ComicDetailView {

    void openComic(MarvelData.ComicInfo comicInfo);

    void showAddToFavouritesButton();

    void hideAddToFavouritesButton();

    void resetAnimatedDrawables();

    void startAnimationsAfterM();

    void startAnimationsBeforeM();

}
