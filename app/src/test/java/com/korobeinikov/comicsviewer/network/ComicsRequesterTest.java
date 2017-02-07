package com.korobeinikov.comicsviewer.network;

import com.korobeinikov.comicsviewer.dagger.DaggerTestNetworkComponent;
import com.korobeinikov.comicsviewer.dagger.TestNetworkComponent;
import com.korobeinikov.comicsviewer.model.MarvelData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Dmitriy_Korobeinikov.
 */
public class ComicsRequesterTest {

    @Inject
    ComicsRequester mComicsRequester;

    @Before
    public void setUp() {
        TestNetworkComponent component = DaggerTestNetworkComponent.builder().build();
        component.inject(this);

        RxJavaHooks.setOnIOScheduler(scheduler -> Schedulers.immediate());
    }

    @After
    public void tearDown() {
        RxJavaHooks.reset();
    }

    /**
     * The default number of items per page is 20 thereby using an offset of 10 should lead to the duplicates between first and second requests.
     */
    @Test
    public void duplicatedItemsShouldNotBeEmitted() {
        Observable<MarvelData.ComicInfo> firstPage = getComicsResults("sp", 0);
        Observable<MarvelData.ComicInfo> secondPage = getComicsResults("sp", 10);

        getCombinedResultsAsList(firstPage, secondPage).subscribe(comicList -> {
            assertEquals(30, comicList.size());
        });
    }

    /**
     * The default number of items per page is 20 thereby using an offset of 20 should lead to a fully new page of items.
     */
    @Test
    public void nextPageShouldContainOnlyNewItems() {
        Observable<MarvelData.ComicInfo> firstPage = getComicsResults("sp", 0);
        Observable<MarvelData.ComicInfo> secondPage = getComicsResults("sp", 20);

        getCombinedResultsAsList(firstPage, secondPage).subscribe(comicList -> {
            assertEquals(40, comicList.size());
        });
    }

    private Observable<MarvelData.ComicInfo> getComicsResults(String query, int offset) {
        return mComicsRequester.findComicsByKeyword(query, offset)
                .flatMap(response -> Observable.from(response.data.results));
    }

    private Observable<List<Object>> getCombinedResultsAsList(Observable<?> first, Observable<?> second) {
        return Observable.merge(first, second)
                .distinct()
                .toList()
                .observeOn(Schedulers.immediate());
    }
}