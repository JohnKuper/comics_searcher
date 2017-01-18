package com.korobeinikov.comicsviewer.util;

import android.content.Context;

import com.korobeinikov.comicsviewer.R;
import com.korobeinikov.comicsviewer.model.ComicImageVariant;
import com.korobeinikov.comicsviewer.model.MarvelData;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public final class StringHelper {

    private StringHelper() {
    }

    public static String getShortInfo(Context context, MarvelData.Result result) {
        String price = String.valueOf(result.getFirstPrice().price);
        return (context.getString(R.string.short_comic_info, result.format, price));
    }

    public static String getFullPathToImage(MarvelData.Thumbnail thumbnail, ComicImageVariant variant) {
        return thumbnail.path + "/" + variant.toString().toLowerCase() + "." + thumbnail.extension;
    }
}
