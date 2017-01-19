package com.korobeinikov.comicsviewer.dagger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korobeinikov.comicsviewer.BuildConfig;
import com.korobeinikov.comicsviewer.network.ComicRequester;
import com.korobeinikov.comicsviewer.network.MarvelService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
@Module
public class NetworkModule {

    @Provides
    public ObjectMapper provideObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    @Provides
    public OkHttpClient providerOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    @Provides
    public Retrofit.Builder provideRetrofitBuilder(ObjectMapper mapper, OkHttpClient client) {
        return new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .client(client);
    }

    @Provides
    @Singleton
    public MarvelService provideMarvelServiceService(Retrofit.Builder retrofitBuilder) {
        retrofitBuilder.baseUrl(BuildConfig.MARVEL_ENDPOINT);
        return retrofitBuilder.build().create(MarvelService.class);
    }

    @Provides
    public ComicRequester provideComicRequester(MarvelService service) {
        return new ComicRequester(service);
    }
}
