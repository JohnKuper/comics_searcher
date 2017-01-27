package com.korobeinikov.comicsviewer.model;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.ThumbnailRealmProxy;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */

@Parcel(implementations = ThumbnailRealmProxy.class,
        value = Parcel.Serialization.BEAN,
        analyze = Thumbnail.class)
public class Thumbnail extends RealmObject {

    public String path;
    public String extension;

    public String getFullPath(ComicImageVariant variant) {
        return path + "/" + variant.toString().toLowerCase() + "." + extension;
    }
}
