package com.korobeinikov.comicsviewer.mvp.presenter;

import android.support.annotation.VisibleForTesting;

import com.korobeinikov.comicsviewer.adapter.PagingController;
import com.korobeinikov.comicsviewer.adapter.SearchAdapter;
import com.korobeinikov.comicsviewer.model.ComicsResponse;
import com.korobeinikov.comicsviewer.model.MarvelData;
import com.korobeinikov.comicsviewer.model.RealmComicInfo;
import com.korobeinikov.comicsviewer.mvp.view.SearchListView;
import com.korobeinikov.comicsviewer.network.ComicsRequester;
import com.korobeinikov.comicsviewer.realm.ComicRepository;

import io.realm.RealmResults;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by Dmitriy_Korobeinikov.
 */
public class SearchPresenter extends BasePresenter<SearchListView> implements SearchAdapter.ClickListener, PagingController.Callbacks {
    private static final String TAG = "SearchPresenter";

    private ComicsRequester mComicsRequester;
    private Observable<ComicsResponse> mComicRequest;
    private Subscription mSubscription;
    private MarvelData mFetchedMarvelData;

    private ComicRepository mComicRepository;
    private RealmResults<RealmComicInfo> mRealmComics;

    private String mLastKeyword;
    private int mLastClickedPosition;

    public SearchPresenter(ComicsRequester requester, ComicRepository repository, MarvelData marvelData) {
        mComicsRequester = requester;
        mComicRepository = repository;
        mFetchedMarvelData = marvelData;
    }

    @Override
    public void attachView(SearchListView view) {
        super.attachView(view);
        restorePreviousData();
        continueLoading();
        listenForRealmComicsUpdates();
    }

    private void restorePreviousData() {
        if (mFetchedMarvelData.results.size() > 0) {
            mView.updateResults(mFetchedMarvelData);
        }
    }

    private void continueLoading() {
        if (mComicsRequester.isLoading()) {
            subscribeForComics();
        }
    }

    private void listenForRealmComicsUpdates() {
        mRealmComics = mComicRepository.getAllComics();
        mRealmComics.addChangeListener(element -> mView.notifyItemChanged(mLastClickedPosition));
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mRealmComics.removeChangeListeners();
    }

    private void subscribeForComics() {
        if (mFetchedMarvelData.offset == 0) {
            mView.showProgress(true);
        }
        mSubscription = mComicRequest
                .subscribe(mComicsObserver);
    }

    public void onSearchSubmit(String keyword) {
        mLastKeyword = keyword;
        mFetchedMarvelData.clear();
        mComicRequest = mComicsRequester.findComicsByKeyword(keyword, 0).cache();
        subscribeForComics();
    }

    @Override
    public void onListItemClick(MarvelData.ComicInfo comicInfo, int position) {
        setLastClickedPosition(position);
        mView.openDetailedInformation(comicInfo, position);
    }

    @Override
    public void onAddToFavouritesClick(MarvelData.ComicInfo comicInfo, int position) {
        setLastClickedPosition(position);
        mComicRepository.addComic(comicInfo);
    }

    @Override
    public void onDeleteFromFavouritesClick(int comicID, int position) {
        setLastClickedPosition(position);
        mComicRepository.deleteComicById(comicID);
    }

    private void setLastClickedPosition(int position) {
        mLastClickedPosition = position;
    }

    private final Observer<ComicsResponse> mComicsObserver = new Observer<ComicsResponse>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            onRequestFailed();
        }

        @Override
        public void onNext(ComicsResponse response) {
            if (mView != null) {
                onResponseReceived(response);
            }
        }
    };

    private void onRequestFailed() {
        mView.showProgress(false);
        mView.showError();
    }

    @VisibleForTesting
    protected void onResponseReceived(ComicsResponse response) {
        mView.showProgress(false);
        MarvelData freshData = response.data;
        if (freshData.offset == 0) {
            mFetchedMarvelData.updateResults(freshData);
            mView.updateResults(freshData);
        } else {
            mFetchedMarvelData.merge(freshData);
            mView.addResults(freshData);
        }
    }

    @Override
    public void onLoadMore() {
        mComicRequest = mComicsRequester.findComicsByKeyword(mLastKeyword, mFetchedMarvelData.getNextOffset()).cache();
        mSubscription = mComicRequest.subscribe(mComicsObserver);
    }

    @Override
    public boolean isLoading() {
        return mComicsRequester.isLoading();
    }

    @Override
    public boolean hasMoreData() {
        return mFetchedMarvelData.hasMoreData();
    }

    public String getLastKeyword() {
        return mLastKeyword;
    }

    public int getLastClickedPosition() {
        return mLastClickedPosition;
    }
}
