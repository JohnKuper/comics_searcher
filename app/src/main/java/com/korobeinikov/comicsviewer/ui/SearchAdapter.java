package com.korobeinikov.comicsviewer.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.korobeinikov.comicsviewer.R;
import com.korobeinikov.comicsviewer.model.ComicImageVariant;
import com.korobeinikov.comicsviewer.model.MarvelData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchItemVH> {

    private Context mContext;
    private List<MarvelData.Result> mResultsList;
    private ClickListener mClickListener;

    public interface ClickListener {
        void onListItemClick(MarvelData.Result result);
    }

    public SearchAdapter(Context context, ClickListener listener) {
        mContext = context;
        mClickListener = listener;
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

    private String getShortInfo(MarvelData.Result result) {
        String price = String.valueOf(result.getFirstPrice().price);
        return (mContext.getString(R.string.short_comic_info, result.format, price));
    }

    private String addImageVariant(String initialURL, String extension) {
        return initialURL + "/" + ComicImageVariant.STANDARD_MEDIUM.toString().toLowerCase() + "." + extension;
    }

    public void setResults(List<MarvelData.Result> resultsList) {
        mResultsList.clear();
        mResultsList.addAll(resultsList);
        notifyDataSetChanged();
    }

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    class SearchItemVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.ivThumbnail)
        ImageView ivThumbnail;
        @BindView(R.id.tvComicTitle)
        TextView tvTitle;
        @BindView(R.id.tvShortInfo)
        TextView tvShortInfo;
        @BindView(R.id.ibAddToFavourites)
        ImageButton ibToFavourites;

        public SearchItemVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rlSearchListItem:
                    mClickListener.onListItemClick(mResultsList.get(getAdapterPosition()));
                    break;
            }
        }
    }
}
