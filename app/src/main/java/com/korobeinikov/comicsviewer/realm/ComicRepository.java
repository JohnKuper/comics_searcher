package com.korobeinikov.comicsviewer.realm;

import com.korobeinikov.comicsviewer.model.MarvelData;
import com.korobeinikov.comicsviewer.model.RealmComicData;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */

public class ComicRepository {

    private Realm mRealm;

    public ComicRepository(Realm realm) {
        mRealm = realm;
    }

    public void addComic(MarvelData.ComicInfo comicInfo) {
        mRealm.beginTransaction();
        RealmComicData realmResult = mRealm.createObject(RealmComicData.class);
        realmResult.setId(comicInfo.id);
        realmResult.setPath(comicInfo.thumbnail.path);
        realmResult.setExtension(comicInfo.thumbnail.extension);
        mRealm.commitTransaction();
    }

    public void deleteComicById(int id) {
        mRealm.beginTransaction();
        RealmComicData comicData = mRealm.where(RealmComicData.class).equalTo(RealmComicData.ID, id).findFirst();
        comicData.removeFromRealm();
        mRealm.commitTransaction();
    }

    public RealmComicData getComicById(int id) {
        mRealm.beginTransaction();
        RealmComicData comicData = mRealm.where(RealmComicData.class).equalTo(RealmComicData.ID, id).findFirst();
        mRealm.commitTransaction();
        return comicData;
    }

    public RealmResults<RealmComicData> getAllComics() {
        return mRealm.where(RealmComicData.class).findAll();
    }

    public boolean isAddedById(int id) {
        return getComicById(id) != null;
    }
}
