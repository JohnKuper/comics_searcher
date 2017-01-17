package com.korobeinikov.comicsviewer.network;

import com.korobeinikov.comicsviewer.model.ComicsResponse;
import com.korobeinikov.comicsviewer.model.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public interface MarvelService {

    @GET("comics?apikey=" + Constants.PUBLIC_MARVEL_KEY)
    Call<ComicsResponse> findComics(@Query("titleStartsWith") String title, @Query("ts") long timestamp, @Query("hash") String hash);
}
