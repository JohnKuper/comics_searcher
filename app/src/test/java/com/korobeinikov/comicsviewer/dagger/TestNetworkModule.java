package com.korobeinikov.comicsviewer.dagger;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.korobeinikov.comicsviewer.dagger.module.NetworkModule;
import com.korobeinikov.comicsviewer.model.ComicsResponse;
import com.korobeinikov.comicsviewer.network.ComicsRequester;
import com.korobeinikov.comicsviewer.network.MarvelService;

import java.io.IOException;
import java.net.URL;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Observable;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Dmitriy_Korobeinikov.
 */

@Module
public class TestNetworkModule extends NetworkModule {
    private static final String TAG = "TestNetworkModule";

    private static final String NEW_REQUEST_FILE_NAME = "first_page_response.json";
    private static final String NEXT_PAGE_REQUEST_FILE_NAME = "next_page_response.json";

    @Named("MockService")
    @Provides
    public MarvelService provideTestMarvelServiceService(ObjectMapper mapper) {
        ComicsResponse firstPageResponse = getMockResponse(mapper, NEW_REQUEST_FILE_NAME);
        ComicsResponse nextPageResponse = getMockResponse(mapper, NEXT_PAGE_REQUEST_FILE_NAME);
        Observable<ComicsResponse> firstPageObservable = Observable.just(firstPageResponse);
        Observable<ComicsResponse> nextPageObservable = Observable.just(nextPageResponse);
        Observable<ComicsResponse> errorObservable = Observable.error(new IOException());

        MarvelService service = mock(MarvelService.class);
        when(service.findComics(anyString(), anyLong(), anyString(), same(0))).thenReturn(firstPageObservable);
        when(service.findComics(anyString(), anyLong(), anyString(), intThat(argument -> argument > 0))).thenReturn(nextPageObservable);
        when(service.findComics(eq("error"), anyLong(), anyString(), anyInt())).thenReturn(errorObservable);
        return service;
    }

    @Named("MockRequester")
    @Provides
    public ComicsRequester provideTestComicsRequester(@Named("MockService") MarvelService service) {
        return new ComicsRequester(service);
    }

    private URL getFileFromResource(String fileName) {
        return ClassLoader.getSystemResource(fileName);
    }

    private ComicsResponse getMockResponse(ObjectMapper mapper, String fileName) {
        ComicsResponse response = null;
        try {
            response = mapper.readValue(getFileFromResource(fileName), ComicsResponse.class);
        } catch (IOException e) {
            Log.e(TAG, "Error during getting the mock response", e);
        }
        return response;
    }
}
