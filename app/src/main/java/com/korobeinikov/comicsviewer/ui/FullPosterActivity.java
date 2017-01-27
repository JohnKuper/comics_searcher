package com.korobeinikov.comicsviewer.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.korobeinikov.comicsviewer.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FullPosterActivity extends AppCompatActivity {

    public static final String EXTRA_POSTER_URL = "EXTRA_POSTER_URL";
    public static final String THUMBNAIL_TRANSITION_NAME = "thumbnail";

    @BindView(R.id.ivFullPoster)
    protected ImageView mFullPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_poster);
        ButterKnife.bind(this);
        showFullPoster();
    }

    private void showFullPoster() {
        String fullPath = getIntent().getStringExtra(EXTRA_POSTER_URL);
        Picasso.with(this).load(fullPath).into(mFullPoster);
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
        super.onBackPressed();
    }
}
