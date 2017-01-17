package com.korobeinikov.comicsviewer.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.korobeinikov.comicsviewer.R;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public class RecyclerVH extends RecyclerView.ViewHolder {

    private final TextView mItemTextView;

    public RecyclerVH(final View parent, TextView itemTextView) {
        super(parent);
        mItemTextView = itemTextView;
    }

    public static RecyclerVH newInstance(View parent) {
        TextView itemTextView = (TextView) parent.findViewById(R.id.tvListItem);
        return new RecyclerVH(parent, itemTextView);
    }

    public void setItemText(CharSequence text) {
        mItemTextView.setText(text);
    }

}
