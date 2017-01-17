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
    }

    interface Presenter<T extends View> extends BasePresenter<T> {
        void onSearchSubmit();
    }
}
