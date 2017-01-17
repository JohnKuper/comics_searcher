package com.korobeinikov.comicsviewer.mvp;

import android.os.Handler;
import android.os.Looper;

import com.korobeinikov.comicsviewer.model.User;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public class LoginPresenter implements LoginContract.Presenter<LoginContract.View> {

    private LoginContract.View mView;
    private Handler mHandler;
    private User mUser = new User("test", "test");

    public LoginPresenter() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void setView(LoginContract.View view) {
        mView = view;
    }

    @Override
    public void onLoginClick() {
        final String userName = mView.getUserName();
        final String pass = mView.getPassword();
        mView.setProgressVisibility(true);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mView.setProgressVisibility(false);
                if (mUser.isValid(pass, userName)) {
                    mView.showLoginResult("Success");
                } else {
                    mView.showLoginResult("Failed");
                }
            }
        }, 5000);

    }

    @Override
    public void onCancelClick() {
        mView.clearFields();
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

}
