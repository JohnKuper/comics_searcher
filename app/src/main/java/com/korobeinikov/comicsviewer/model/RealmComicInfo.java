package com.korobeinikov.comicsviewer.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public class RealmComicInfo extends RealmObject {

    public static final String ID = "id";

    @PrimaryKey
    private int id;
    private Thumbnail thumbnail;

    public static RealmComicInfo from(MarvelData.ComicInfo comicInfo) {
        RealmComicInfo realmInfo = new RealmComicInfo();
        realmInfo.setId(comicInfo.id);
        realmInfo.setThumbnail(comicInfo.thumbnail);
        return realmInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }
}
