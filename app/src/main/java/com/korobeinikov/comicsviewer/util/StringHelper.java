package com.korobeinikov.comicsviewer.util;

import android.content.Context;

import com.korobeinikov.comicsviewer.R;
import com.korobeinikov.comicsviewer.model.MarvelData;

/**
 * Created by Dmitriy_Korobeinikov.
 */
public final class StringHelper {

    private StringHelper() {
    }

    public static String getShortInfo(Context context, MarvelData.ComicInfo comicInfo) {
        String price = String.valueOf(comicInfo.getFirstPrice().price);
        return (context.getString(R.string.short_comic_info, comicInfo.format, price));
    }

    public static String getCorrectDescription(Context context, String description) {
        return isEmpty(description) ? context.getString(R.string.no_description) : description;
    }

    public static boolean isEmpty(String string) {
        return string == null || string.trim().length() == 0;
    }
}
