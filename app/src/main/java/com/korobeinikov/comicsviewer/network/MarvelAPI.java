package com.korobeinikov.comicsviewer.network;

import com.korobeinikov.comicsviewer.model.AuthorizationData;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public interface MarvelAPI {

    @POST
    Call<AuthorizationData> basicAuth(@Header("Authorization") String base64Credentials);
}
