package com.korobeinikov.comicsviewer.model;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public class User {

    private String mName;
    private String mPassword;

    public User(String name, String password) {
        mName = name;
        mPassword = password;
    }

    public boolean isValid(String name, String password) {
        return name != null && password != null && name.equals(mName) && password.equals(mPassword);
    }
}
