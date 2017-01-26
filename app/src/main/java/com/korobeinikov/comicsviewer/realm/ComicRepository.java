package com.korobeinikov.comicsviewer.realm;

import com.korobeinikov.comicsviewer.model.MarvelData;
import com.korobeinikov.comicsviewer.model.RealmComicInfo;

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
        RealmComicInfo info = RealmComicInfo.from(comicInfo);
        mRealm.executeTransaction(realm -> realm.copyToRealm(info));
    }

    public void deleteComicById(int id) {
        mRealm.executeTransaction(realm -> realm.where(RealmComicInfo.class).equalTo(RealmComicInfo.ID, id).findFirst().deleteFromRealm());
    }

    public RealmComicInfo getComicById(int id) {
        return mRealm.where(RealmComicInfo.class).equalTo(RealmComicInfo.ID, id).findFirst();
    }

    public RealmResults<RealmComicInfo> getAllComics() {
        return mRealm.where(RealmComicInfo.class).findAll();
    }

    public boolean isAddedById(int id) {
        return getComicById(id) != null;
    }
}
