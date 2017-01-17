package com.korobeinikov.comicsviewer.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.korobeinikov.comicsviewer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public class SearchItemVH extends RecyclerView.ViewHolder {

    @BindView(R.id.ivThumbnail)
    public ImageView mThumbnail;
    @BindView(R.id.tvComicTitle)
    public TextView mTitle;
    @BindView(R.id.tvModified)
    public TextView mModified;
    @BindView(R.id.ibAddToFavourites)
    public ImageButton mAddToFavourites;

    public SearchItemVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
