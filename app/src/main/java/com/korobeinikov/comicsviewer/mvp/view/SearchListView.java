package com.korobeinikov.comicsviewer.mvp.view;

import com.korobeinikov.comicsviewer.model.MarvelData;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */

public interface SearchListView {

    void showProgress(boolean isShown);

    void refreshResults(MarvelData marvelData);

    void addResults(MarvelData marvelData);

    void openDetailedInformation(MarvelData.ComicInfo comicInfo, int position);

}
