package com.korobeinikov.comicsviewer.dagger;

import com.korobeinikov.comicsviewer.mvp.LoginPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
@Module
public class ActivityModule {

    @Provides
    public LoginPresenter providerLoginPresenter() {
        return new LoginPresenter();
    }
}
