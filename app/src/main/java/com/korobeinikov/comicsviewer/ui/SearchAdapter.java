package com.korobeinikov.comicsviewer.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.korobeinikov.comicsviewer.R;
import com.korobeinikov.comicsviewer.model.ComicImageVariant;
import com.korobeinikov.comicsviewer.model.MarvelData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchItemVH> {

    private Context mContext;
    private List<MarvelData.Result> mResultsList;

    public SearchAdapter(Context context) {
        mContext = context;
        mResultsList = new ArrayList<>();
    }

    @Override
    public SearchItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.search_list_item, parent, false);
        return new SearchItemVH(view);
    }

    @Override
    public void onBindViewHolder(SearchItemVH holder, int position) {
        MarvelData.Result result = mResultsList.get(position);
        holder.tvTitle.setText(result.title);
        holder.tvShortInfo.setText(getShortInfo(result));
        holder.ibToFavourites.setImageDrawable(mContext.getDrawable(R.drawable.ic_add));

        MarvelData.Thumbnail thumbnail = result.thumbnail;
        Picasso.with(mContext).load(addImageVariant(thumbnail.path, thumbnail.extension))
                .into(holder.ivThumbnail);
    }

    @Override
    public int getItemCount() {
        return mResultsList.size();
    }

    public void setResults(List<MarvelData.Result> resultsList) {
        mResultsList.clear();
        mResultsList.addAll(resultsList);
        notifyDataSetChanged();
    }

    private String addImageVariant(String initialURL, String extension) {
        return initialURL + "/" + ComicImageVariant.STANDARD_MEDIUM.toString().toLowerCase() + "." + extension;
    }

    private String getShortInfo(MarvelData.Result result) {
        String price = String.valueOf(result.getFirstPrice().price);
        return (mContext.getString(R.string.short_comic_info, result.format, price));
    }
}
