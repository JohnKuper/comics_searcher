package com.korobeinikov.comicsviewer.mvp;

import com.korobeinikov.comicsviewer.model.MarvelData;

import java.util.List;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public interface SearchContract {

    interface View extends BaseView {
        void updateSearchList(List<MarvelData.Result> results);

        void openDetailedInformation(MarvelData.Result result);
    }

    interface Presenter extends BasePresenter<View> {
        void onSearchSubmit(String query);

        void onListItemClick(MarvelData.Result result);
    }
}
