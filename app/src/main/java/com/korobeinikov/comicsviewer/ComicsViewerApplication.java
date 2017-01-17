package com.korobeinikov.comicsviewer;

import android.app.Application;

import com.korobeinikov.comicsviewer.dagger.AppComponent;
import com.korobeinikov.comicsviewer.dagger.AppModule;
import com.korobeinikov.comicsviewer.dagger.DaggerAppComponent;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public class ComicsViewerApplication extends Application {

    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppComponent = buildAppComponent();
    }

    private AppComponent buildAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }
}
