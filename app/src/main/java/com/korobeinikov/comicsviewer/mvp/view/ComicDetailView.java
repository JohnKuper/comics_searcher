package com.korobeinikov.comicsviewer.mvp.view;

import com.korobeinikov.comicsviewer.model.MarvelData;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public interface ComicDetailView {

    void openComic(MarvelData.ComicInfo comicInfo);

    void showAddToFavouritesButton();

    void hideAddToFavouritesButton();

    void resetAnimatedDrawables();

    void startAnimations();
}
