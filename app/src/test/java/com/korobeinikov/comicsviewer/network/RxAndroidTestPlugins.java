package com.korobeinikov.comicsviewer.network;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.schedulers.Schedulers;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public class RxAndroidTestPlugins {

    private static final RxAndroidSchedulersHook sAndroidRxHook = new RxAndroidSchedulersHook() {
        @Override
        public Scheduler getMainThreadScheduler() {
            return Schedulers.immediate();
        }
    };

    public static void resetAndroidTestPlugins() {
        RxAndroidPlugins.getInstance().reset();
    }

    public static void setImmediateScheduler() {
        RxAndroidPlugins.getInstance().reset();
        RxAndroidPlugins.getInstance().registerSchedulersHook(sAndroidRxHook);
    }

}
