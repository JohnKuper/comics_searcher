package com.korobeinikov.comicsviewer.mvp;

import com.korobeinikov.comicsviewer.model.MarvelData;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public interface SearchContract {

    interface View extends BaseView {
        void refreshResults(MarvelData marvelData);

        void addResults(MarvelData marvelData);

        void openDetailedInformation(MarvelData.Result result);
    }

    interface Presenter extends BasePresenter<View> {
        void onSearchSubmit(String query);

        void onListBottomReached();

        void onListItemClick(MarvelData.Result result);
    }
}
