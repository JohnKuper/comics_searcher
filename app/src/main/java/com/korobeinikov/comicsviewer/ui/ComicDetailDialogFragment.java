package com.korobeinikov.comicsviewer.ui;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetBehavior.BottomSheetCallback;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.korobeinikov.comicsviewer.R;
import com.korobeinikov.comicsviewer.model.ComicImageVariant;
import com.korobeinikov.comicsviewer.model.MarvelData;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.design.widget.BottomSheetBehavior.STATE_HIDDEN;
import static android.view.animation.AnimationUtils.loadAnimation;
import static com.korobeinikov.comicsviewer.util.StringHelper.getCorrectDescription;
import static com.korobeinikov.comicsviewer.util.StringHelper.getFullPathToImage;
import static com.korobeinikov.comicsviewer.util.StringHelper.getShortInfo;
import static com.korobeinikov.comicsviewer.util.VersionHelper.isMarshmallow;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public class ComicDetailDialogFragment extends BottomSheetDialogFragment {

    public static final String ARG_COMIC_DETAILS = "ARG_COMIC_DETAILS";

    @BindView(R.id.ivThumbnail)
    protected ImageView mThumbnail;
    @BindView(R.id.ibAddToFavourites)
    protected ImageButton mAddToFavourites;
    @BindView(R.id.ibGotoComic)
    protected ImageButton mGotoComic;
    @BindView(R.id.tvComicTitle)
    protected TextView mTitle;
    @BindView(R.id.tvShortInfo)
    protected TextView mShortInfo;
    @BindView(R.id.tvDescription)
    protected TextView mDescription;

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        resetAnimatedDrawables();
    }

    /**
     * AnimatedVectorDrawable behaves weird after being played once. It can be loaded into view in the final state,
     * instead of the starting state. {@link AnimatedVectorDrawable#reset()} solves this problem, but this method is
     * not available below 23 SDK Marshmallow.
     */
    private void resetAnimatedDrawables() {
        if (isMarshmallow()) {
            ((AnimatedVectorDrawable) mAddToFavourites.getDrawable()).reset();
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

        mAddToFavourites.setOnClickListener(v -> startAnimations());
        mGotoComic.setOnClickListener(v -> {
            // TODO: 1/23/2017 Open specific comic
            Toast.makeText(getContext(), "Open comic", Toast.LENGTH_SHORT).show();
        });

        updateAddToFavouritesButton();
    }

    private void updateAddToFavouritesButton() {
        if (isFavourite()) {
            mAddToFavourites.setVisibility(View.INVISIBLE);
            mGotoComic.setVisibility(View.VISIBLE);
        } else {
            mAddToFavourites.setVisibility(View.VISIBLE);
            mGotoComic.setVisibility(View.INVISIBLE);
        }
    }

    private void startAnimations() {
        mAddToFavourites.setClickable(false);
        if (isMarshmallow()) {
            AnimatedVectorDrawable animDrawable = (AnimatedVectorDrawable) mAddToFavourites.getDrawable();
            animDrawable.start();
            animDrawable.registerAnimationCallback(new Animatable2.AnimationCallback() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onAnimationEnd(Drawable drawable) {
                    mAddToFavourites.startAnimation(setupAndGetScaleDownAnimation());
                }
            });
        } else {
            mAddToFavourites.startAnimation(setupAndGetScaleDownAnimation());
        }
    }

    private Animation setupAndGetScaleDownAnimation() {
        Animation scaleDown = loadAnimation(getActivity(), R.anim.scale_down);
        Animation scaleUp = loadAnimation(getContext(), R.anim.scale_up);
        scaleDown.setAnimationListener(new AnimationListenerAdapter() {
            @Override
            public void onAnimationEnd(Animation animation) {
                mGotoComic.startAnimation(scaleUp);
                mGotoComic.setVisibility(View.VISIBLE);
            }
        });
        return scaleDown;
    }

    //// TODO: 1/23/2017 Change to real request to database
    private boolean isFavourite() {
        return new Random().nextBoolean();
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
}
