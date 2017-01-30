package com.korobeinikov.comicsviewer.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.korobeinikov.comicsviewer.mvp.presenter.MainContainerPresenter;
import com.korobeinikov.comicsviewer.realm.ComicRepository;
import com.korobeinikov.comicsviewer.ui.UINavigator;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
@Module
public class ActivityModule {

    public AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        mActivity = activity;
    }

    @Provides
    public AppCompatActivity providesActivity() {
        return mActivity;
    }

    @Provides
    public MainContainerPresenter providesMainContainerPresenter(ComicRepository repository) {
        return new MainContainerPresenter(repository);
    }

    @Provides
    public UINavigator providesUINavigator(AppCompatActivity activity) {
        return new UINavigator(activity.getSupportFragmentManager());
    }
}