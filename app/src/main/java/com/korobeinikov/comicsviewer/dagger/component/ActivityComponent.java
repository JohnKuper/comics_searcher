package com.korobeinikov.comicsviewer.dagger.component;

import com.korobeinikov.comicsviewer.dagger.module.ActivityModule;
import com.korobeinikov.comicsviewer.dagger.module.FragmentModule;
import com.korobeinikov.comicsviewer.dagger.scope.PerActivity;
import com.korobeinikov.comicsviewer.ui.activity.FullPosterActivity;
import com.korobeinikov.comicsviewer.ui.activity.MainActivity;

import dagger.Subcomponent;

/**
 * Created by Dmitriy_Korobeinikov.
 */

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(FullPosterActivity fullPosterActivity);

    FragmentComponent plus(FragmentModule fragmentModule);
}
