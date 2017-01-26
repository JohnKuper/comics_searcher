package com.korobeinikov.comicsviewer.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */

public class RealmComicInfo extends RealmObject {

    public static final String ID = "id";

    @PrimaryKey
    private int id;
    private String path;
    private String extension;

    public static RealmComicInfo from(MarvelData.ComicInfo comicInfo) {
        RealmComicInfo realmInfo = new RealmComicInfo();
        realmInfo.setId(comicInfo.id);
        realmInfo.setPath(comicInfo.thumbnail.path);
        realmInfo.setExtension(comicInfo.thumbnail.extension);
        return realmInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
