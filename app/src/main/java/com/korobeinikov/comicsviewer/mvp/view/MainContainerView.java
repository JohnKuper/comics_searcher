package com.korobeinikov.comicsviewer.mvp.view;

import com.korobeinikov.comicsviewer.ui.UINavigator;
import com.lapism.searchview.SearchView;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */

public interface MainContainerView {

    SearchView getSearchView();

    UINavigator getUINavigator();

    void updateFavouritesCount(int count);
}
