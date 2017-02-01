package com.korobeinikov.comicsviewer.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.korobeinikov.comicsviewer.ComicsViewerApplication;

/**
 * Created by Dmitriy Korobeinikov.
 * Copyright (C) 2016 SportingBet. All rights reserved.
 */
public class NetworkAvailabilityMonitor {

    private OnNetworkAvailabilityListener mListener;

    private boolean mRegistered;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            notifyListener();
        }
    };

    private void notifyListener() {
        boolean isAvailable = isNetworkAvailable();
        if (isAvailable) {
            mListener.networkAvailable();
        } else {
            mListener.networkUnavailable();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) ComicsViewerApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void register(Context context, OnNetworkAvailabilityListener listener) {
        mListener = listener;
        context.registerReceiver(mReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        mRegistered = true;
        notifyListener();
    }

    public void unregister(Context context) {
        if (mRegistered) {
            context.unregisterReceiver(mReceiver);
            mRegistered = false;
        }
    }

    public interface OnNetworkAvailabilityListener {

        void networkAvailable();

        void networkUnavailable();
    }
}
