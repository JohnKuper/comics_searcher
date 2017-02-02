package com.korobeinikov.comicsviewer.mvp.view;

import com.korobeinikov.comicsviewer.ui.UINavigator.FragmentTag;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public interface MainContainerView {

    void loadMainScreen();

    void updateFavouritesCount(int count);

    void toggleSearchViewVisibility(boolean isShown);

    void openFragment(@FragmentTag String tag);

    void closeSearchView();

    void handleQuery(String query);
}