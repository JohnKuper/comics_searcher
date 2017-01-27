package com.korobeinikov.comicsviewer.mvp.view;

import android.widget.ImageView;

import com.korobeinikov.comicsviewer.model.RealmComicInfo;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */

public interface FavouritesView {

    void openFullPosterActivity(ImageView thumbnail, RealmComicInfo comicInfo);
}
