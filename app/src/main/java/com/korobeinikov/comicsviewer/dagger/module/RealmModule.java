package com.korobeinikov.comicsviewer.dagger.module;

import android.content.Context;

import com.korobeinikov.comicsviewer.realm.ComicRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */

@Module
public class RealmModule {

    @Provides
    @Singleton
    public Realm providesRealm(Context context) {
        RealmConfiguration config = new RealmConfiguration.Builder(context)
                .deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
        return Realm.getDefaultInstance();
    }

    @Provides
    @Singleton
    public ComicRepository providesComicRepository(Realm realm) {
        return new ComicRepository(realm);
    }

}
