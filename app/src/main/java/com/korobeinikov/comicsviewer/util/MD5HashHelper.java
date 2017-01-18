package com.korobeinikov.comicsviewer.util;

import android.os.SystemClock;
import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.korobeinikov.comicsviewer.model.Constants.PRIVATE_MARVEL_KEY;
import static com.korobeinikov.comicsviewer.model.Constants.PUBLIC_MARVEL_KEY;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public final class MD5HashHelper {

    private static final String TAG = MD5HashHelper.class.getSimpleName();

    private MD5HashHelper() {
    }

    public static String computeMarvelMD5hash() {
        String timestamp = String.valueOf(SystemClock.elapsedRealtime());
        return computeMD5hash(timestamp + PRIVATE_MARVEL_KEY + PUBLIC_MARVEL_KEY);
    }

    public static String computeMD5hash(String from) {
        try {
            MessageDigest mg = MessageDigest.getInstance("MD5");
            mg.update(from.getBytes());
            byte[] digestBytes = mg.digest();
            BigInteger bigInt = new BigInteger(1, digestBytes);
            String hashText = bigInt.toString(16);
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
            return hashText;

        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Error during MD5 hash computation", e);
        }
        return null;
    }
}
