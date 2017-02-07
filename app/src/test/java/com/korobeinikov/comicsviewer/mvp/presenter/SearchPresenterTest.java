package com.korobeinikov.comicsviewer.mvp.presenter;

import com.korobeinikov.comicsviewer.dagger.DaggerTestNetworkComponent;
import com.korobeinikov.comicsviewer.dagger.TestNetworkComponent;
import com.korobeinikov.comicsviewer.model.ComicsResponse;
import com.korobeinikov.comicsviewer.model.MarvelData;
import com.korobeinikov.comicsviewer.model.RealmComicInfo;
import com.korobeinikov.comicsviewer.mvp.view.SearchListView;
import com.korobeinikov.comicsviewer.network.ComicsRequester;
import com.korobeinikov.comicsviewer.network.MarvelService;
import com.korobeinikov.comicsviewer.realm.ComicRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.inject.Inject;
import javax.inject.Named;

import io.realm.RealmResults;
import rx.Observable;
import rx.observers.TestSubscriber;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
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

    @Inject
    ComicsRequester mComicsRequester;
    @Named("MockService")
    @Inject
    MarvelService mMarvelService;

    private TestSubscriber<ComicsResponse> mComicsTestSubscriber;
    private SearchPresenter mSearchPresenter;
    private MarvelData mMarvelData = new MarvelData();

    @Before
    public void setUp() {
        TestNetworkComponent component = DaggerTestNetworkComponent.builder().build();
        component.inject(this);

        when(mComicRepository.getAllComics()).thenReturn(mRealmComics);
        mSearchPresenter = new SearchPresenter(mComicsRequester, mComicRepository, mMarvelData);
        mSearchPresenter.attachView(mView);
        mComicsTestSubscriber = new TestSubscriber<>();

        RxJavaHooks.setOnIOScheduler(scheduler -> Schedulers.immediate());
    }

    @After
    public void tearDown() {
        RxJavaHooks.reset();
    }

    @Test
    public void comicsShouldBeRetrieved() {
        Observable<ComicsResponse> responseObservable = mComicsRequester.findComicsByKeyword("sp", 0).observeOn(Schedulers.immediate());
        responseObservable.subscribe(mComicsTestSubscriber);

        mComicsTestSubscriber.assertCompleted();
        mComicsTestSubscriber.assertValueCount(1);
    }

    @Test
    public void resultsShouldBeUpdatedAfterNewSearch() throws Exception {
        mMarvelService.findComics("", 0, "", 0).subscribe(response -> {
            mSearchPresenter.onResponseReceived(response);
            InOrder inOrder = inOrder(mView);
            inOrder.verify(mView).showProgress(false);
            inOrder.verify(mView).updateResults(any());
            assertEquals(response.data.results.size(), mMarvelData.results.size());
        });
    }

    @Test
    public void resultsShouldBeMergedAfterRetrievingNextPage() throws Exception {
        mMarvelService.findComics("", 0, "", 0).subscribe(response -> mSearchPresenter.onResponseReceived(response));
        mMarvelService.findComics("", 0, "", 5).subscribe(response -> {
            mSearchPresenter.onResponseReceived(response);
            MarvelData newMarvelData = response.data;

            verify(mView).addResults(any());
            assertEquals(newMarvelData.results.size() * 2, mMarvelData.results.size());
            assertEquals(newMarvelData.offset, mMarvelData.offset);
            assertEquals(newMarvelData.total, mMarvelData.total);
        });
    }
}