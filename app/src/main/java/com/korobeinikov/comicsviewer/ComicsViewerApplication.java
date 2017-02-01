package com.korobeinikov.comicsviewer;

import android.app.Application;
import android.content.Context;

import com.korobeinikov.comicsviewer.dagger.component.AppComponent;
import com.korobeinikov.comicsviewer.dagger.component.DaggerAppComponent;
import com.korobeinikov.comicsviewer.dagger.module.AppModule;


/**
 * Created by Dmitriy_Korobeinikov.
 */
public class ComicsViewerApplication extends Application {

    private static ComicsViewerApplication sApplication;
    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        sAppComponent = buildAppComponent();
    }

    private AppComponent buildAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    public static Context getAppContext() {
        return sApplication.getApplicationContext();
    }
}
