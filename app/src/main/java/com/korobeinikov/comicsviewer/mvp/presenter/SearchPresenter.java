package com.korobeinikov.comicsviewer.mvp.presenter;

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
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public class SearchPresenter extends BasePresenter<SearchListView> implements SearchAdapter.ClickListener, PagingController.Callbacks {
    private static final String TAG = "SearchPresenter";

    private ComicsRequester mComicsRequester;
    private Observable<ComicsResponse> mCachedRequest;
    private Subscription mSubscription;
    private MarvelData mFetchedMarvelData;
    private String mLastKeyword;

    private ComicRepository mComicRepository;
    private RealmResults<RealmComicInfo> mRealmComics;

    private int mLastClickedPosition;

    public SearchPresenter(ComicsRequester requester, ComicRepository repository, MarvelData marvelData) {
        mComicsRequester = requester;
        mComicRepository = repository;
        mFetchedMarvelData = marvelData;
    }

    @Override
    public void attachView(SearchListView view) {
        super.attachView(view);
        if (mFetchedMarvelData.results.size() > 0) {
            mView.updateResults(mFetchedMarvelData);
        }
        if (mComicsRequester.isLoading()) {
            subscribeForComics();
        }
        listenForRealmComicsUpdates();
    }

    private void listenForRealmComicsUpdates() {
        mRealmComics = mComicRepository.getAllComics();
        mRealmComics.addChangeListener(element -> mView.getSearchAdapter().notifyItemChanged(mLastClickedPosition));
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    private void subscribeForComics() {
        if (mFetchedMarvelData.offset == 0) {
            mView.showProgress(true);
        }
        mSubscription = mCachedRequest.subscribe(mComicsObserver);
    }

    public void onSearchSubmit(String keyword) {
        mLastKeyword = keyword;
        mFetchedMarvelData.clear();
        mCachedRequest = mComicsRequester.findComicsByKeyword(keyword, 0).cache();
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
            mView.showProgress(false);
        }

        @Override
        public void onNext(ComicsResponse response) {
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
    };

    @Override
    public void onLoadMore() {
        mCachedRequest = mComicsRequester.findComicsByKeyword(mLastKeyword, mFetchedMarvelData.getNextOffset()).cache();
        mSubscription = mCachedRequest.subscribe(mComicsObserver);
    }

    @Override
    public boolean isLoading() {
        return mComicsRequester.isLoading();
    }

    @Override
    public boolean hasMoreData() {
        return mFetchedMarvelData.hasMoreData();
    }
}
