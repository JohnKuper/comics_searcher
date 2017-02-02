package com.korobeinikov.comicsviewer.mvp.presenter;

import android.widget.ImageView;

import com.korobeinikov.comicsviewer.model.RealmComicInfo;
import com.korobeinikov.comicsviewer.mvp.view.FavouritesView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Dmitriy_Korobeinikov.
 */
@RunWith(MockitoJUnitRunner.class)
public class FavoritesPresenterTest {

    @Mock
    private FavouritesView mView;

    private FavoritesPresenter mPresenter;

    @Before
    public void setUp() {
        mPresenter = new FavoritesPresenter();
        mPresenter.attachView(mView);
    }

    @Test
    public void viewShouldBeSavedAndDeleted() {
        assertNotNull(mPresenter.mView);

        mPresenter.detachView();
        assertNull(mPresenter.mView);
    }

    @Test
    public void fullPosterActivityIsOpenedAfterClickingOnFavoritesListItem() {
        mPresenter.onListItemClick(mock(ImageView.class), mock(RealmComicInfo.class));
        verify(mView).openFullPosterActivity(any(ImageView.class), any(RealmComicInfo.class));
    }
}