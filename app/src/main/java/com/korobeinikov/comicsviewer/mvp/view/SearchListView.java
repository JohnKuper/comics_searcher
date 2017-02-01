package com.korobeinikov.comicsviewer.mvp.view;

import com.korobeinikov.comicsviewer.adapter.SearchAdapter;
import com.korobeinikov.comicsviewer.model.MarvelData;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public interface SearchListView {

    SearchAdapter getSearchAdapter();

    void showProgress(boolean isShown);

    void updateResults(MarvelData marvelData);

    void addResults(MarvelData marvelData);

    void openDetailedInformation(MarvelData.ComicInfo comicInfo, int position);

}
