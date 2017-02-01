package com.korobeinikov.comicsviewer.util;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public final class AnimationUtils {

    private AnimationUtils() {
    }

    public static void animateReveal(Context context, View view, @ColorRes int color) {
        int centerX = (view.getLeft() + view.getRight()) / 2;
        int centerY = (view.getTop() + view.getBottom()) / 2;
        animateRevealColorFromCoordinates(context, view, color, centerX, centerY);
    }

    private static void animateRevealColorFromCoordinates(Context context, View view, @ColorRes int color, int centerX, int centerY) {
        float finalRadius = (float) Math.hypot(view.getWidth(), view.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, 0, finalRadius);
        view.setBackgroundColor(ContextCompat.getColor(context, color));
        anim.setDuration(750);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
    }

    public static class AnimationListenerAdapter implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
