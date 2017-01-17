package com.korobeinikov.comicsviewer.dagger;

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
