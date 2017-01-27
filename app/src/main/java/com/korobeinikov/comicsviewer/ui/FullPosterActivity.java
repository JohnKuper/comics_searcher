package com.korobeinikov.comicsviewer.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.korobeinikov.comicsviewer.R;
import com.korobeinikov.comicsviewer.util.AnimationUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FullPosterActivity extends AppCompatActivity {

    public static final String EXTRA_POSTER_URL = "EXTRA_POSTER_URL";
    public static final String THUMBNAIL_TRANSITION_NAME = "thumbnail";

    @BindView(R.id.fullPosterRoot)
    protected LinearLayout mRoot;
    @BindView(R.id.ivFullPoster)
    protected ImageView mFullPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_poster);
        ButterKnife.bind(this);
        startRevealAnimation();
        loadFullPoster();
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
}
