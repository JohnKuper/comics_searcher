package com.korobeinikov.comicsviewer.mvp.presenter;

/**
 * Created by Dmitriy_Korobeinikov.
 */
public abstract class BasePresenter<T> {

    protected T mView;

    public void attachView(T view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
    }
}

