package com.korobeinikov.comicsviewer.mvp;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public interface LoginContract {

    interface View extends BaseView {
        void setProgressVisibility(boolean isShown);

        void showLoginResult(String text);

        void clearFields();

        String getPassword();

        String getUserName();
    }

    interface Presenter<T extends BaseView> extends BasePresenter<T> {
        void onLoginClick();

        void onCancelClick();
    }
}
