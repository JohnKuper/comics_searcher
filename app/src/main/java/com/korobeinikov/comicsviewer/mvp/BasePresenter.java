package com.korobeinikov.comicsviewer.mvp;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public interface BasePresenter<T> {
    void onAttachView(T view);

    void onDetachView();
}

