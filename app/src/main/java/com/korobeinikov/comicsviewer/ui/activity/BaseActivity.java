package com.korobeinikov.comicsviewer.ui.activity;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.korobeinikov.comicsviewer.R;
import com.korobeinikov.comicsviewer.network.NetworkAvailabilityMonitor;

import javax.inject.Inject;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */

public abstract class BaseActivity extends AppCompatActivity implements NetworkAvailabilityMonitor.OnNetworkAvailabilityListener {

    @Inject
    protected NetworkAvailabilityMonitor mNetworkMonitor;

    private Snackbar mSnackbar;

    @Override
    protected void onResume() {
        super.onResume();
        mNetworkMonitor.register(this, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mNetworkMonitor.unregister(this);
    }

    @Override
    public void networkAvailable() {
        if (mSnackbar != null && mSnackbar.isShown()) {
            mSnackbar.dismiss();
        }
    }

    @Override
    public void networkUnavailable() {
        if (mSnackbar == null || !mSnackbar.isShown()) {
            mSnackbar = Snackbar.make(getRootView(), R.string.no_network_connection, Snackbar.LENGTH_INDEFINITE);
            mSnackbar.show();
        }
    }

    protected abstract View getRootView();
}
