package com.korobeinikov.comicsviewer.mvp.presenter;

import android.widget.ImageView;

import com.korobeinikov.comicsviewer.adapter.FavouritesAdapter;
import com.korobeinikov.comicsviewer.model.RealmComicInfo;
import com.korobeinikov.comicsviewer.mvp.view.FavouritesView;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public class FavouritesPresenter extends BasePresenter<FavouritesView> implements FavouritesAdapter.ClickListener {

    @Override
    public void onListItemClick(ImageView thumbnail, RealmComicInfo comicInfo) {
        mView.openFullPosterActivity(thumbnail, comicInfo);
    }
}
