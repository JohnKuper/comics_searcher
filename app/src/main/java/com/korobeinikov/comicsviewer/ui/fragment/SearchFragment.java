package com.korobeinikov.comicsviewer.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.korobeinikov.comicsviewer.ComicsViewerApplication;
import com.korobeinikov.comicsviewer.R;
import com.korobeinikov.comicsviewer.adapter.PagingController;
import com.korobeinikov.comicsviewer.adapter.SearchAdapter;
import com.korobeinikov.comicsviewer.dagger.component.SearchComponent;
import com.korobeinikov.comicsviewer.model.MarvelData;
import com.korobeinikov.comicsviewer.mvp.presenter.SearchPresenter;
import com.korobeinikov.comicsviewer.mvp.view.SearchListView;
import com.korobeinikov.comicsviewer.realm.ComicRepository;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.korobeinikov.comicsviewer.R.id.recycler_view;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public class SearchFragment extends BaseFragment implements SearchListView {
    public static final String TAG = "SearchFragment";

    private static SearchComponent sFragmentComponent;

    @BindView(recycler_view)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.progress_bar)
    protected ProgressBar mProgressBar;

    @Inject
    protected SearchPresenter mPresenter;
    @Inject
    protected ComicRepository mComicRepository;

    private SearchAdapter mSearchAdapter;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectSelf();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        initViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActionBar().setTitle(R.string.search);
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        releaseSelf();
    }

    private void releaseSelf() {
        if (getActivity().isFinishing()) {
            sFragmentComponent = null;
        }
    }

    private void injectSelf() {
        if (sFragmentComponent == null) {
            sFragmentComponent = ComicsViewerApplication.getAppComponent().plus();
        }
        sFragmentComponent.inject(this);
    }

    private void initViews() {
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mSearchAdapter = new SearchAdapter(getContext(), mComicRepository);
        mSearchAdapter.setClickListener(mPresenter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mSearchAdapter);
        PagingController.connectTo(mRecyclerView, mPresenter);
    }

    public void onSearchSubmit(String query) {
        mPresenter.onSearchSubmit(query);
    }

    ///////////////////////////////////////////////////////////////////////////
    // BEGIN: Commands from Presenter
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void updateResults(MarvelData marvelData) {
        mSearchAdapter.updateResults(marvelData);
    }

    @Override
    public void addResults(MarvelData marvelData) {
        mSearchAdapter.addResults(marvelData);
    }

    @Override
    public void openDetailedInformation(MarvelData.ComicInfo comicInfo, int position) {
        ComicDetailsFragment dialogFragment = ComicDetailsFragment.newInstance(comicInfo);
        dialogFragment.show(getActivity().getSupportFragmentManager(), null);
    }

    @Override
    public void notifyItemChanged(int position) {
        mSearchAdapter.notifyItemChanged(position);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), R.string.request_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress(boolean isShown) {
        mProgressBar.setVisibility(isShown ? View.VISIBLE : View.GONE);
        mRecyclerView.setVisibility(isShown ? View.GONE : View.VISIBLE);
    }
    ///////////////////////////////////////////////////////////////////////////
    // END: Commands from Presenter
    ///////////////////////////////////////////////////////////////////////////
}
