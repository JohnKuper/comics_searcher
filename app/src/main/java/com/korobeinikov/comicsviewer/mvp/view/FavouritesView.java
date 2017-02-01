package com.korobeinikov.comicsviewer.mvp.view;

import android.widget.ImageView;

import com.korobeinikov.comicsviewer.model.RealmComicInfo;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public interface FavouritesView {

    void openFullPosterActivity(ImageView thumbnail, RealmComicInfo comicInfo);
}
