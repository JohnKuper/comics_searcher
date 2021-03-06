package com.korobeinikov.comicsviewer.dagger.component;

import com.korobeinikov.comicsviewer.dagger.module.ActivityModule;
import com.korobeinikov.comicsviewer.dagger.module.AppModule;
import com.korobeinikov.comicsviewer.dagger.module.NetworkModule;
import com.korobeinikov.comicsviewer.dagger.module.RealmModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Dmitriy_Korobeinikov.
 */
@Component(modules = {
        AppModule.class,
        NetworkModule.class,
        RealmModule.class
})
@Singleton
public interface AppComponent {

    ActivityComponent plus(ActivityModule activityModule);

    SearchComponent plus();
}
