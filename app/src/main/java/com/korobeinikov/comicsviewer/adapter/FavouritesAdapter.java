package com.korobeinikov.comicsviewer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.korobeinikov.comicsviewer.R;
import com.korobeinikov.comicsviewer.model.RealmComicInfo;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

import static com.korobeinikov.comicsviewer.model.Thumbnail.STANDARD_FANTASTIC;


/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */

public class FavouritesAdapter extends RealmRecyclerViewAdapter<RealmComicInfo, FavouritesAdapter.FavouriteItemVH> {

    private ClickListener mClickListener;

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
        String fullPath = comicInfo.getThumbnail().getFullPath(STANDARD_FANTASTIC);
        ViewCompat.setTransitionName(holder.thumbnail, String.valueOf(position) + "_image");
        Picasso.with(context).load(fullPath).into(holder.thumbnail);
    }

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    protected class FavouriteItemVH extends RecyclerView.ViewHolder {

        @BindView(R.id.ivThumbnail)
        ImageView thumbnail;

        public FavouriteItemVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            thumbnail.setOnClickListener(view -> {
                int position = getAdapterPosition();
                RealmComicInfo comicInfo = getItem(position);
                mClickListener.onListItemClick(thumbnail, comicInfo);
            });
        }
    }

    public interface ClickListener {
        void onListItemClick(ImageView thumbnail, RealmComicInfo comicInfo);
    }
}
