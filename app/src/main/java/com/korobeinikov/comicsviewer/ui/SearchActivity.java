package com.korobeinikov.comicsviewer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.korobeinikov.comicsviewer.ComicsViewerApplication;
import com.korobeinikov.comicsviewer.R;
import com.korobeinikov.comicsviewer.adapter.PagingController;
import com.korobeinikov.comicsviewer.adapter.SearchAdapter;
import com.korobeinikov.comicsviewer.dagger.ComponentOwner;
import com.korobeinikov.comicsviewer.dagger.component.ActivityComponent;
import com.korobeinikov.comicsviewer.dagger.module.ActivityModule;
import com.korobeinikov.comicsviewer.model.MarvelData;
import com.korobeinikov.comicsviewer.mvp.presenter.SearchPresenter;
import com.korobeinikov.comicsviewer.mvp.view.SearchListView;
import com.korobeinikov.comicsviewer.realm.ComicRepository;
import com.lapism.searchview.SearchView;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

import static com.korobeinikov.comicsviewer.R.id.recyclerView;
import static com.korobeinikov.comicsviewer.ui.ComicDetailsFragment.ARG_COMIC_DETAILS;

// TODO: 1/26/2017 Extract common logic to base class
public class SearchActivity extends AppCompatActivity implements ComponentOwner<ActivityComponent>, SearchListView {

    private static ActivityComponent sActivityComponent;

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.searchView)
    protected SearchView mSearchView;
    @BindView(recyclerView)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.progressBar)
    protected ProgressBar mProgressBar;
    @BindView(R.id.navigationView)
    protected NavigationView mNavigationView;
    @BindView(R.id.drawer)
    protected DrawerLayout mDrawerLayout;

    @Inject
    protected SearchPresenter mPresenter;
    @Inject
    protected ComicRepository mComicRepository;
    @Inject
    protected Realm mRealm;

    private SearchAdapter mSearchAdapter;
    private TextView mFavouritesCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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
        setupDrawer();
    }

    private void setupDrawer() {
        mFavouritesCounter = (TextView) mNavigationView.getMenu().findItem(R.id.action_favourites).getActionView();
        mNavigationView.setNavigationItemSelectedListener(item -> {
            mDrawerLayout.closeDrawers();
            switch (item.getItemId()) {
                case R.id.action_search:
                    return true;
                case R.id.action_favourites:
                    // TODO: 1/26/2017 To Presenter
                    Intent intent = new Intent(this, FavouritesActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.action_about:
                    return true;
                default:
                    return true;
            }
        });
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
            mRealm.close();
        }
        mPresenter.detachView();
    }

    @Override
    public ActivityComponent getComponent() {
        return sActivityComponent;
    }

    private void setupRecyclerView() {
        mSearchAdapter = new SearchAdapter(this, mComicRepository);
        mSearchAdapter.setClickListener(mPresenter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mSearchAdapter);
        PagingController.connectTo(mRecyclerView, mPresenter);
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
        Bundle args = new Bundle();
        args.putParcelable(ARG_COMIC_DETAILS, Parcels.wrap(comicInfo));
        ComicDetailsFragment dialogFragment = ComicDetailsFragment.newInstance(args);
        dialogFragment.show(getSupportFragmentManager(), null);
    }

    @Override
    public SearchAdapter getSearchAdapter() {
        return mSearchAdapter;
    }

    @Override
    public void updateFavouritesCount(int count) {
        mFavouritesCounter.setText(count > 0 ? String.valueOf(count) : null);
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
