package com.korobeinikov.comicsviewer.mvp.presenter;

import com.korobeinikov.comicsviewer.dagger.DaggerTestNetworkComponent;
import com.korobeinikov.comicsviewer.dagger.TestNetworkComponent;
import com.korobeinikov.comicsviewer.model.MarvelData;
import com.korobeinikov.comicsviewer.model.RealmComicInfo;
import com.korobeinikov.comicsviewer.mvp.view.SearchListView;
import com.korobeinikov.comicsviewer.network.ComicsRequester;
import com.korobeinikov.comicsviewer.network.MarvelService;
import com.korobeinikov.comicsviewer.network.RxAndroidTestPlugins;
import com.korobeinikov.comicsviewer.realm.ComicRepository;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.inject.Inject;
import javax.inject.Named;

import io.realm.RealmResults;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Dmitriy_Korobeinikov.
 */
@RunWith(MockitoJUnitRunner.class)
public class SearchPresenterTest {

    @Mock
    private ComicRepository mComicRepository;
    @Mock
    private SearchListView mView;
    @Mock
    private RealmResults<RealmComicInfo> mRealmComics;

    @Named("MockRequester")
    @Inject
    ComicsRequester mComicsRequester;
    @Named("MockService")
    @Inject
    MarvelService mMarvelService;

    private SearchPresenter mSearchPresenter;
    private MarvelData mMarvelData = new MarvelData();

    @Before
    public void setUp() {
        TestNetworkComponent component = DaggerTestNetworkComponent.builder().build();
        component.inject(this);

        when(mComicRepository.getAllComics()).thenReturn(mRealmComics);
        mSearchPresenter = new SearchPresenter(mComicsRequester, mComicRepository, mMarvelData);
        mSearchPresenter.attachView(mView);
    }

    @BeforeClass
    public static void setUpClass() {
        RxJavaHooks.setOnIOScheduler(scheduler -> Schedulers.immediate());
        RxAndroidTestPlugins.setImmediateScheduler();
    }

    @AfterClass
    public static void tearDownClass() {
        RxJavaHooks.reset();
        RxAndroidTestPlugins.resetAndroidTestPlugins();
    }

    @Test
    public void newSearchRequestShouldBeInvoked_dataShouldBeUpdated() {
        mSearchPresenter.onSearchSubmit("test");

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showProgress(true);
        inOrder.verify(mView).showProgress(false);
        inOrder.verify(mView).updateResults(any());

        assertEquals("test", mSearchPresenter.getLastKeyword());
        assertEquals(5, mMarvelData.results.size());
    }

    @Test
    public void resultsShouldBeMergedAfterRetrievingNextPage() {
        mSearchPresenter.onSearchSubmit("test");
        mSearchPresenter.onLoadMore();

        verify(mView).showProgress(true);
        verify(mView, times(2)).showProgress(false);
        verify(mView).addResults(any());
        assertEquals(10, mMarvelData.results.size());
    }

    @Test
    public void errorShouldBeShownAfterUnsuccessfulRequest() {
        mSearchPresenter.onSearchSubmit("error");

        verify(mView).showProgress(true);
        verify(mView).showProgress(false);
        verify(mView).showError();
    }

    @Test
    public void detailedComicInformationShouldBeOpened() {
        mSearchPresenter.onListItemClick(new MarvelData.ComicInfo(), 5);
        verify(mView).openDetailedInformation(any(), eq(5));
        assertEquals(5, mSearchPresenter.getLastClickedPosition());
    }

    @Test
    public void comicShouldBeAdded() {
        mSearchPresenter.onAddToFavouritesClick(new MarvelData.ComicInfo(), 10);
        verify(mComicRepository).addComic(any());
        assertEquals(10, mSearchPresenter.getLastClickedPosition());
    }

    @Test
    public void comicShouldBeDeleted() {
        mSearchPresenter.onDeleteFromFavouritesClick(100, 11);
        verify(mComicRepository).deleteComicById(eq(100));
        assertEquals(11, mSearchPresenter.getLastClickedPosition());
    }
}