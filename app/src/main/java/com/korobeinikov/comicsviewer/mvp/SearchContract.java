package com.korobeinikov.comicsviewer.mvp;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public interface SearchContract {

    interface View extends BaseView {
        void updateSearchList();
    }

    interface Presenter<T extends View> extends BasePresenter<T> {
        void onSearchSubmit();
    }
}
