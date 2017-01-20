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

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.korobeinikov.comicsviewer.model.ComicImageVariant.STANDARD_MEDIUM;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_LIST_ITEM = 1;
    private static final int VIEW_TYPE_LOADING_ITEM = 2;

    private Context mContext;
    private ArrayList<MarvelData.Result> mResultsList;
    private ClickListener mClickListener;

    private boolean mDisplayLoadingRow = true;

    public interface ClickListener {

        void onListItemClick(MarvelData.Result result);
    }

    public SearchAdapter(Context context) {
        mContext = context;
        mResultsList = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (mResultsList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else if (!isLoadingRow(position)) {
            return VIEW_TYPE_LIST_ITEM;
        } else {
            return VIEW_TYPE_LOADING_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case VIEW_TYPE_EMPTY:
                view = inflater.inflate(R.layout.search_empty_view, parent, false);
                viewHolder = new EmptyVH(view);
                break;
            case VIEW_TYPE_LIST_ITEM:
                view = inflater.inflate(R.layout.search_list_item, parent, false);
                viewHolder = new SearchItemVH(view);
                break;
            case VIEW_TYPE_LOADING_ITEM:
                view = inflater.inflate(R.layout.list_item_loading, parent, false);
                viewHolder = new LoadingVH(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof SearchItemVH) {
            SearchItemVH holder = (SearchItemVH) viewHolder;
            MarvelData.Result result = mResultsList.get(position);
            holder.tvTitle.setText(result.title);
            holder.tvShortInfo.setText(StringHelper.getShortInfo(mContext, result));
            holder.ibToFavourites.setImageDrawable(mContext.getDrawable(R.drawable.ic_add));

            Picasso.with(mContext)
                    .load(StringHelper.getFullPathToImage(result.thumbnail, STANDARD_MEDIUM))
                    .into(holder.ivThumbnail);
        }
    }

    @Override
    public int getItemCount() {
        if (mResultsList.size() == 0) {
            return 1;
        } else {
            return mDisplayLoadingRow ? mResultsList.size() + 1 : mResultsList.size();
        }
    }

    private boolean isLoadingRow(int position) {
        return mDisplayLoadingRow && position == getLoadingRowPosition();
    }

    private int getLoadingRowPosition() {
        return mDisplayLoadingRow ? getItemCount() - 1 : -1;
    }

    public void swapResults(MarvelData marvelData) {
        mResultsList.clear();
        mResultsList.addAll(marvelData.results);
        adjustLoadingItem(marvelData);
    }

    public void addResults(MarvelData marvelData) {
        int prevSize = mResultsList.size();
        ArrayList<MarvelData.Result> newResults = marvelData.results;
        mResultsList.addAll(newResults);
        notifyItemRangeInserted(prevSize, newResults.size());
        adjustLoadingItem(marvelData);
    }

    private void adjustLoadingItem(MarvelData marvelData) {
        if (!marvelData.hasMoreData()) {
            mDisplayLoadingRow = false;
            notifyDataSetChanged();
        }
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

    class EmptyVH extends RecyclerView.ViewHolder {

        public EmptyVH(View itemView) {
            super(itemView);
        }
    }

    class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}
