package com.korobeinikov.comicsviewer.dagger.module;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korobeinikov.comicsviewer.BuildConfig;
import com.korobeinikov.comicsviewer.network.ComicsRequester;
import com.korobeinikov.comicsviewer.network.MarvelService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
@Module
public class NetworkModule {

    @Provides
    @Singleton
    public ObjectMapper provideObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    @Provides
    @Singleton
    public OkHttpClient providerOkHttpClient() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return new OkHttpClient.Builder().addInterceptor(logInterceptor).build();
    }

    @Provides
    @Singleton
    public Retrofit.Builder provideRetrofitBuilder(ObjectMapper mapper, OkHttpClient client) {
        return new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client);
    }

    @Provides
    @Singleton
    public MarvelService provideMarvelServiceService(Retrofit.Builder retrofitBuilder) {
        retrofitBuilder.baseUrl(BuildConfig.MARVEL_ENDPOINT);
        return retrofitBuilder.build().create(MarvelService.class);
    }

    @Provides
    @Singleton
    public ComicsRequester provideComicRequester(MarvelService service) {
        return new ComicsRequester(service);
    }
}
