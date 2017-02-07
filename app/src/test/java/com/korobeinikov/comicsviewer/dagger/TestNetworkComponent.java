package com.korobeinikov.comicsviewer.dagger;

import com.korobeinikov.comicsviewer.mvp.presenter.SearchPresenterTest;
import com.korobeinikov.comicsviewer.network.ComicsRequesterTest;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Dmitriy_Korobeinikov.
 */

@Singleton
@Component(modules = TestNetworkModule.class)
public interface TestNetworkComponent {
    void inject(SearchPresenterTest searchPresenterTest);

    void inject(ComicsRequesterTest comicsRequesterTest);
}
