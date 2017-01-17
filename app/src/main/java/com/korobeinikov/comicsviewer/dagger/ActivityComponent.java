package com.korobeinikov.comicsviewer.dagger;

import com.korobeinikov.comicsviewer.ui.MainActivity;

import dagger.Subcomponent;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
}
