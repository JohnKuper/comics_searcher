package com.korobeinikov.comicsviewer.dagger;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */

public interface ComponentOwner<T> {
    T getComponent();
}
