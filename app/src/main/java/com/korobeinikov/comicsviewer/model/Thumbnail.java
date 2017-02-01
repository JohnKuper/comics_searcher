package com.korobeinikov.comicsviewer.model;

import android.support.annotation.StringDef;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.ThumbnailRealmProxy;

/**
 * Created by Dmitriy_Korobeinikov.
 */

@Parcel(implementations = ThumbnailRealmProxy.class,
        value = Parcel.Serialization.BEAN,
        analyze = Thumbnail.class)
public class Thumbnail extends RealmObject {

    @StringDef({STANDARD_MEDIUM, STANDARD_LARGE, STANDARD_FANTASTIC})
    private @interface ImageQuality {}

    public static final String STANDARD_MEDIUM = "standard_medium";
    public static final String STANDARD_LARGE = "standard_large";
    public static final String STANDARD_FANTASTIC = "standard_fantastic";

    public String path;
    public String extension;

    public String getFullPath(@ImageQuality String quality) {
        return path + "/" + quality + "." + extension;
    }
}
