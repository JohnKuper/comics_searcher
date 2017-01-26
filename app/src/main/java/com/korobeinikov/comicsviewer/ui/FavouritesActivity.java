package com.korobeinikov.comicsviewer.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewTreeObserver;

import com.korobeinikov.comicsviewer.ComicsViewerApplication;
import com.korobeinikov.comicsviewer.R;
import com.korobeinikov.comicsviewer.adapter.FavouritesAdapter;
import com.korobeinikov.comicsviewer.dagger.module.ActivityModule;
import com.korobeinikov.comicsviewer.realm.ComicRepository;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */

public class FavouritesActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;

    @Inject
    protected ComicRepository mComicRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        ButterKnife.bind(this);
        ComicsViewerApplication.getAppComponent().plus(new ActivityModule()).inject(this);
        initViews();
    }

    private void initViews() {
        setupToolbar();
        setupRecyclerView();
    }

    @SuppressWarnings("ConstantConditions")
    private void setupToolbar() {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setTitle(R.string.favourites);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void setupRecyclerView() {
        FavouritesAdapter adapter = new FavouritesAdapter(this, mComicRepository.getAllComics());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, null, 0, 0);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int recyclerWidth = mRecyclerView.getMeasuredWidth();
                float thumbnailWidth = getResources().getDimension(R.dimen.favourites_thumbnail_width);
                int newSpanCount = (int) Math.floor(recyclerWidth / thumbnailWidth);
                gridLayoutManager.setSpanCount(newSpanCount);
                gridLayoutManager.requestLayout();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
