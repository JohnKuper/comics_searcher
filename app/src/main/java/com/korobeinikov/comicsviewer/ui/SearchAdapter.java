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
import com.korobeinikov.comicsviewer.model.MarvelData;
import com.korobeinikov.comicsviewer.util.StringHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.korobeinikov.comicsviewer.model.ComicImageVariant.STANDARD_MEDIUM;

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
        holder.tvShortInfo.setText(StringHelper.getShortInfo(mContext, result));
        holder.ibToFavourites.setImageDrawable(mContext.getDrawable(R.drawable.ic_add));

        Picasso.with(mContext)
                .load(StringHelper.getFullPathToImage(result.thumbnail, STANDARD_MEDIUM))
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
