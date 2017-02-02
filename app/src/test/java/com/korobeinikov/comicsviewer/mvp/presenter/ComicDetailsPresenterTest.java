package com.korobeinikov.comicsviewer.mvp.presenter;

import com.korobeinikov.comicsviewer.mvp.view.ComicDetailView;
import com.korobeinikov.comicsviewer.realm.ComicRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Dmitriy_Korobeinikov.
 */
@RunWith(MockitoJUnitRunner.class)
public class ComicDetailsPresenterTest {

    @Mock
    private ComicDetailView mView;
    @Mock
    private ComicRepository mComicRepository;

    private ComicDetailsPresenter mPresenter;

    @Before
    public void setUp() {
        mPresenter = new ComicDetailsPresenter(mComicRepository);
        mPresenter.attachView(mView);
    }

    @Test
    public void beforeBeingDetachedViewShouldResetAnimatedDrawables() {
        mPresenter.detachView();
        verify(mView).resetAnimatedDrawables();
    }

    @Test
    public void addComicToFavoritesAndStartAnimation() {
        mPresenter.onAddToFavouritesClick(any());
        verify(mComicRepository).addComic(any());
        verify(mView).startAnimations();
    }

    @Test
    public void addToFavoritesButtonShouldBeShown() {
        when(mComicRepository.isAddedById(anyInt())).thenReturn(false);
        mPresenter.updateCircleButton(anyInt());
        verify(mView).showAddToFavouritesButton();
    }

    @Test
    public void addToFavoritesButtonShouldBeHide() {
        when(mComicRepository.isAddedById(anyInt())).thenReturn(true);
        mPresenter.updateCircleButton(anyInt());
        verify(mView).hideAddToFavouritesButton();
    }
}