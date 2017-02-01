package com.korobeinikov.comicsviewer.network;

import com.korobeinikov.comicsviewer.model.ComicsResponse;
import com.korobeinikov.comicsviewer.model.Constants;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Dmitriy_Korobeinikov.
 */
public interface MarvelService {

    @GET("comics?apikey=" + Constants.PUBLIC_MARVEL_KEY)
    Observable<ComicsResponse> findComics(@Query("titleStartsWith") String query, @Query("ts") long timestamp,
                                          @Query("hash") String hash, @Query("offset") int offset);
}
