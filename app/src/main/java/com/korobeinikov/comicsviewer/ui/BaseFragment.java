package com.korobeinikov.comicsviewer.ui;

import android.support.v4.app.Fragment;

import com.korobeinikov.comicsviewer.dagger.ComponentOwner;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */

public class BaseFragment extends Fragment {

    @SuppressWarnings("unchecked")
    protected <T> T getComponent(Class<T> componentType) {
        return ((ComponentOwner<T>) getActivity()).getComponent();
    }
}
