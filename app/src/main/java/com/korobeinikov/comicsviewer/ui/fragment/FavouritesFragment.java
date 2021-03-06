package com.korobeinikov.comicsviewer.ui.fragment;

import android.app.ActivityOptions;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.korobeinikov.comicsviewer.R;
import com.korobeinikov.comicsviewer.adapter.FavouritesAdapter;
import com.korobeinikov.comicsviewer.dagger.component.ActivityComponent;
import com.korobeinikov.comicsviewer.dagger.module.FragmentModule;
import com.korobeinikov.comicsviewer.model.RealmComicInfo;
import com.korobeinikov.comicsviewer.mvp.presenter.FavoritesPresenter;
import com.korobeinikov.comicsviewer.mvp.view.FavouritesView;
import com.korobeinikov.comicsviewer.realm.ComicRepository;
import com.korobeinikov.comicsviewer.ui.activity.FullPosterActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.korobeinikov.comicsviewer.model.Thumbnail.STANDARD_FANTASTIC;
import static com.korobeinikov.comicsviewer.ui.activity.FullPosterActivity.THUMBNAIL_TRANSITION_NAME;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public class FavouritesFragment extends BaseFragment implements FavouritesView {
    public static final String TAG = "FavouritesFragment";

    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    @Inject
    protected ComicRepository mComicRepository;
    @Inject
    protected FavoritesPresenter mPresenter;

    public static FavouritesFragment newInstance() {
        return new FavouritesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(ActivityComponent.class).plus(new FragmentModule()).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        setupRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActionBar().setTitle(R.string.favourites);
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    private void setupRecyclerView() {
        FavouritesAdapter adapter = new FavouritesAdapter(getContext(), mComicRepository.getAllComics());
        adapter.setClickListener(mPresenter);
        int spanCount = getResources().getInteger(R.integer.grid_layout_manager_span);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), spanCount);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void openFullPosterActivity(ImageView thumbnail, RealmComicInfo comicInfo) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), thumbnail, THUMBNAIL_TRANSITION_NAME);
        FullPosterActivity.start(getContext(), comicInfo.getThumbnail().getFullPath(STANDARD_FANTASTIC), options.toBundle());
    }
}
