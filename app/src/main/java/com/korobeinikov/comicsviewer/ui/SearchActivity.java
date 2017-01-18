package com.korobeinikov.comicsviewer.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.korobeinikov.comicsviewer.ComicsViewerApplication;
import com.korobeinikov.comicsviewer.R;
import com.korobeinikov.comicsviewer.dagger.ActivityModule;
import com.korobeinikov.comicsviewer.model.MarvelData;
import com.korobeinikov.comicsviewer.mvp.SearchContract;
import com.korobeinikov.comicsviewer.mvp.SearchPresenter;
import com.lapism.searchview.SearchView;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements SearchContract.View, SearchAdapter.ClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Inject
    SearchPresenter mPresenter;
    private SearchAdapter mSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ComicsViewerApplication.getAppComponent().plus(new ActivityModule()).inject(this);
        setSupportActionBar(mToolbar);
        setupSearchView();
        setupRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.setView(this);
        mPresenter.onSearchSubmit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                mSearchView.open(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchAdapter = new SearchAdapter(this);
        mSearchAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mSearchAdapter);
    }

    private void setupSearchView() {
        mSearchView.setVersion(SearchView.VERSION_MENU_ITEM);
        mSearchView.setTheme(SearchView.THEME_LIGHT);
        mSearchView.setVersionMargins(SearchView.VERSION_MARGINS_MENU_ITEM);
        mSearchView.setVoice(false);

        mSearchView.setHint(R.string.search_hint);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.onSearchSubmit();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onListItemClick(MarvelData.Result result) {
        mPresenter.onListItemClick(result);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Commands from Presenter
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void updateSearchList(List<MarvelData.Result> results) {
        mSearchAdapter.setResults(results);
    }

    @Override
    public void openDetailedInformation(MarvelData.Result result) {
        Bundle args = new Bundle();
        args.putParcelable(ComicDetailDialogFragment.ARG_COMIC_DETAILS, Parcels.wrap(result));
        ComicDetailDialogFragment dialogFragment = ComicDetailDialogFragment.newInstance(args);
        dialogFragment.show(getSupportFragmentManager(), null);
    }
}
