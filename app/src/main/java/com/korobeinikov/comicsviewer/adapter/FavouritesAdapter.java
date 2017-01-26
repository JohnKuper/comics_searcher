package com.korobeinikov.comicsviewer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.korobeinikov.comicsviewer.R;
import com.korobeinikov.comicsviewer.model.ComicImageVariant;
import com.korobeinikov.comicsviewer.model.RealmComicInfo;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

import static com.korobeinikov.comicsviewer.util.StringHelper.getFullPathToImage;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */

public class FavouritesAdapter extends RealmRecyclerViewAdapter<RealmComicInfo, FavouritesAdapter.FavouriteItemVH> {

    public FavouritesAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<RealmComicInfo> data) {
        super(context, data, true);
    }

    @Override
    public FavouriteItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_favourites, parent, false);
        return new FavouriteItemVH(view);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onBindViewHolder(FavouriteItemVH holder, int position) {
        RealmComicInfo comicInfo = getItem(position);
        String fullPath = getFullPathToImage(comicInfo.getThumbnail(), ComicImageVariant.STANDARD_FANTASTIC);
        Picasso.with(context).load(fullPath).into(holder.thumbnail);
    }

    protected class FavouriteItemVH extends RecyclerView.ViewHolder {

        @BindView(R.id.ivThumbnail)
        ImageView thumbnail;

        public FavouriteItemVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
