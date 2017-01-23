package com.korobeinikov.comicsviewer.util;

import android.os.Build;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */

public final class VersionHelper {

    private VersionHelper() {
    }

    public static boolean isMarshmallow() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.M;
    }
}
