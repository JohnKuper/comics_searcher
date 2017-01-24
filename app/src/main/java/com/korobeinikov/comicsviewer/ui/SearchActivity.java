package com.korobeinikov.comicsviewer.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.korobeinikov.comicsviewer.ComicsViewerApplication;
import com.korobeinikov.comicsviewer.R;
import com.korobeinikov.comicsviewer.dagger.ComponentOwner;
import com.korobeinikov.comicsviewer.dagger.component.ActivityComponent;
import com.korobeinikov.comicsviewer.dagger.module.ActivityModule;
import com.korobeinikov.comicsviewer.model.MarvelData;
import com.korobeinikov.comicsviewer.mvp.presenter.SearchPresenter;
import com.korobeinikov.comicsviewer.mvp.view.SearchListView;
import com.lapism.searchview.SearchView;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.korobeinikov.comicsviewer.R.id.recyclerView;

public class SearchActivity extends AppCompatActivity implements ComponentOwner<ActivityComponent>,
        SearchListView, SearchAdapter.ClickListener {

    private static ActivityComponent sActivityComponent;
    private static final int LOADING_THRESHOLD = 5;

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.searchView)
    protected SearchView mSearchView;
    @BindView(recyclerView)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.progressBar)
    protected ProgressBar mProgressBar;

    @Inject
    protected SearchPresenter mPresenter;

    private SearchAdapter mSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        injectSelf();
        initViews();
    }

    private void injectSelf() {
        if (sActivityComponent == null) {
            sActivityComponent = ComicsViewerApplication.getAppComponent().plus(new ActivityModule());
        }
        sActivityComponent.inject(this);
    }

    private void initViews() {
        setSupportActionBar(mToolbar);
        setupSearchView();
        setupRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            sActivityComponent = null;
        }
        mPresenter.detachView();
    }

    @Override
    public ActivityComponent getComponent() {
        return sActivityComponent;
    }

    private void setupRecyclerView() {
        mSearchAdapter = new SearchAdapter(this);
        mSearchAdapter.setClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mSearchAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = recyclerView.getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();

                int firstVisibleItemPosition;
                firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if ((totalItemCount - visibleItemCount) <= (firstVisibleItemPosition + LOADING_THRESHOLD)) {
                    mPresenter.onListBottomReached();
                }
            }
        });
    }

    private void setupSearchView() {
        mSearchView.setVersion(SearchView.VERSION_TOOLBAR);
        mSearchView.setVersionMargins(SearchView.VERSION_MARGINS_MENU_ITEM);
        mSearchView.setTheme(SearchView.THEME_LIGHT);
        mSearchView.setVoice(false);
        mSearchView.setHint(R.string.search_hint);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.onSearchSubmit(query);
                mSearchView.close(true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onListItemClick(MarvelData.ComicInfo comicInfo) {
        mPresenter.onListItemClick(comicInfo);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Commands from Presenter
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void refreshResults(MarvelData marvelData) {
        mSearchAdapter.swapResults(marvelData);
    }

    @Override
    public void addResults(MarvelData marvelData) {
        mSearchAdapter.addResults(marvelData);
    }

    @Override
    public void openDetailedInformation(MarvelData.ComicInfo comicInfo) {
        Bundle args = new Bundle();
        args.putParcelable(ComicDetailsFragment.ARG_COMIC_DETAILS, Parcels.wrap(comicInfo));
        ComicDetailsFragment dialogFragment = ComicDetailsFragment.newInstance(args);
        dialogFragment.show(getSupportFragmentManager(), null);
    }

    @Override
    public void showProgress(boolean isShown) {
        mProgressBar.setVisibility(isShown ? View.VISIBLE : View.GONE);
        mRecyclerView.setVisibility(isShown ? View.GONE : View.VISIBLE);
    }
}
