package com.korobeinikov.comicsviewer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.korobeinikov.comicsviewer.ComicsViewerApplication;
import com.korobeinikov.comicsviewer.R;
import com.korobeinikov.comicsviewer.dagger.module.ActivityModule;
import com.korobeinikov.comicsviewer.util.AnimationUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FullPosterActivity extends BaseActivity {

    public static final String EXTRA_POSTER_URL = "EXTRA_POSTER_URL";
    public static final String THUMBNAIL_TRANSITION_NAME = "thumbnail";

    @BindView(R.id.full_poster_root)
    protected LinearLayout mRoot;
    @BindView(R.id.iv_full_poster)
    protected ImageView mFullPoster;

    public static void start(Context context, String posterURL, @Nullable Bundle bundle) {
        Intent intent = new Intent(context, FullPosterActivity.class);
        intent.putExtra(EXTRA_POSTER_URL, posterURL);
        context.startActivity(intent, bundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_poster);
        ButterKnife.bind(this);
        ComicsViewerApplication.getAppComponent().plus(new ActivityModule(this)).inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mNetworkMonitor.isNetworkAvailable()) {
            startRevealAnimation();
            loadFullPoster();
        }
    }

    private void startRevealAnimation() {
        mRoot.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
                AnimationUtils.animateReveal(FullPosterActivity.this, mRoot, R.color.colorRipple);
            }
        });
    }

    private void loadFullPoster() {
        String fullPath = getIntent().getStringExtra(EXTRA_POSTER_URL);
        Picasso.with(this).load(fullPath).into(mFullPoster);
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
        super.onBackPressed();
    }

    @Override
    protected View getRootView() {
        return mRoot;
    }
}
