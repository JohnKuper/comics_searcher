package com.korobeinikov.comicsviewer.network;

import com.korobeinikov.comicsviewer.model.ComicsResponse;

import java.util.Random;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.korobeinikov.comicsviewer.util.MD5HashHelper.computeMarvelMD5hash;

/**
 * Created by Dmitriy_Korobeinikov.
 */
public class ComicsRequester {

    private MarvelService mMarvelService;

    private boolean mIsLoading;

    public ComicsRequester(MarvelService marvelService) {
        mMarvelService = marvelService;
    }

    public Observable<ComicsResponse> findComicsByKeyword(String keyword, int offset) {
        long timeStamp = new Random().nextInt(1000);
        return mMarvelService.findComics(keyword, timeStamp, computeMarvelMD5hash(timeStamp), offset)
                .subscribeOn(Schedulers.io())
                .retry(3)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> mIsLoading = true)
                .doOnTerminate(() -> mIsLoading = false);
    }

    public boolean isLoading() {
        return mIsLoading;
    }

}
