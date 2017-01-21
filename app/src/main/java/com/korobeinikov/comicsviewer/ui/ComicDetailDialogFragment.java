package com.korobeinikov.comicsviewer.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetBehavior.BottomSheetCallback;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.korobeinikov.comicsviewer.R;
import com.korobeinikov.comicsviewer.model.ComicImageVariant;
import com.korobeinikov.comicsviewer.model.MarvelData;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.design.widget.BottomSheetBehavior.STATE_HIDDEN;
import static com.korobeinikov.comicsviewer.util.StringHelper.getCorrectDescription;
import static com.korobeinikov.comicsviewer.util.StringHelper.getFullPathToImage;
import static com.korobeinikov.comicsviewer.util.StringHelper.getShortInfo;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public class ComicDetailDialogFragment extends BottomSheetDialogFragment {

    public static final String ARG_COMIC_DETAILS = "ARG_COMIC_DETAILS";

    @BindView(R.id.ivThumbnail)
    ImageView mThumbnail;
    @BindView(R.id.tvComicTitle)
    TextView mTitle;
    @BindView(R.id.tvShortInfo)
    TextView mShortInfo;
    @BindView(R.id.tvDescription)
    TextView mDescription;

    private MarvelData.Result mResult;

    public static ComicDetailDialogFragment newInstance(@NonNull Bundle args) {
        ComicDetailDialogFragment fragment = new ComicDetailDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mResult = Parcels.unwrap(getArguments().getParcelable(ARG_COMIC_DETAILS));
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_comic_detail, null);
        dialog.setContentView(contentView);
        setupViews(contentView);
        setBottomSheetCallback(contentView);
    }

    private void setBottomSheetCallback(View contentView) {
        View parentView = (View) contentView.getParent();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) parentView.getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) behavior;
            bottomSheetBehavior.setBottomSheetCallback(new BottomSheetCallback() {

                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (newState == STATE_HIDDEN) {
                        dismiss();
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        }
    }

    private void setupViews(View contentView) {
        ButterKnife.bind(this, contentView);
        Picasso.with(getContext())
                .load(getFullPathToImage(mResult.thumbnail, ComicImageVariant.STANDARD_LARGE))
                .into(mThumbnail);

        mTitle.setText(mResult.title);
        mDescription.setText(getCorrectDescription(getContext(), mResult.description));
        mShortInfo.setText(getShortInfo(getContext(), mResult));
    }
}
