package com.korobeinikov.comicsviewer.dagger.component;

import com.korobeinikov.comicsviewer.dagger.module.ActivityModule;
import com.korobeinikov.comicsviewer.dagger.module.AppModule;
import com.korobeinikov.comicsviewer.dagger.module.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
@Component(modules = {
        AppModule.class,
        NetworkModule.class
})
@Singleton
public interface AppComponent {

    ActivityComponent plus(ActivityModule activityModule);
}
