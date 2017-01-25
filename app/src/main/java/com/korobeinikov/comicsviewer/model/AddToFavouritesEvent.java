package com.korobeinikov.comicsviewer.model;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */

public class AddToFavouritesEvent {

    private int mPosition;

    public AddToFavouritesEvent(int position) {
        mPosition = position;
    }

    public int getPosition() {
        return mPosition;
    }
}
