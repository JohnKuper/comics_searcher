package com.korobeinikov.comicsviewer.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.korobeinikov.comicsviewer.ComicsViewerApplication;
import com.korobeinikov.comicsviewer.R;
import com.korobeinikov.comicsviewer.dagger.ComponentOwner;
import com.korobeinikov.comicsviewer.dagger.component.ActivityComponent;
import com.korobeinikov.comicsviewer.dagger.module.ActivityModule;
import com.korobeinikov.comicsviewer.mvp.presenter.MainContainerPresenter;
import com.korobeinikov.comicsviewer.mvp.view.MainContainerView;
import com.korobeinikov.comicsviewer.realm.ComicRepository;
import com.korobeinikov.comicsviewer.ui.UINavigator;
import com.korobeinikov.comicsviewer.ui.fragment.FavouritesFragment;
import com.korobeinikov.comicsviewer.ui.fragment.SearchFragment;
import com.lapism.searchview.SearchView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements ComponentOwner<ActivityComponent>, MainContainerView {

    private static ActivityComponent sActivityComponent;

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.search_view)
    protected SearchView mSearchView;
    @BindView(R.id.navigation_view)
    protected NavigationView mNavigationView;
    @BindView(R.id.drawer)
    protected DrawerLayout mDrawerLayout;

    @Inject
    protected MainContainerPresenter mPresenter;
    @Inject
    protected UINavigator mUINavigator;
    @Inject
    protected ComicRepository mComicRepository;

    private TextView mFavouritesCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        injectSelf();
        initViews();
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                openDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected View getRootView() {
        return mDrawerLayout;
    }

    private void injectSelf() {
        sActivityComponent = ComicsViewerApplication.getAppComponent().plus(new ActivityModule(this));
        sActivityComponent.inject(this);
    }

    private void initViews() {
        setupToolbar();
        setupSearchView();
        setupDrawer();
    }

    @SuppressWarnings("ConstantConditions")
    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mSearchView.setOnMenuClickListener(this::openDrawer);
    }

    private void openDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    private void setupDrawer() {
        mFavouritesCounter = (TextView) mNavigationView.getMenu().findItem(R.id.action_favourites).getActionView();
        mNavigationView.setNavigationItemSelectedListener(item -> {
            mDrawerLayout.closeDrawers();
            switch (item.getItemId()) {
                case R.id.action_search:
                    mPresenter.onNavMenuItemClicked(SearchFragment.TAG);
                    return true;
                case R.id.action_favourites:
                    mPresenter.onNavMenuItemClicked(FavouritesFragment.TAG);
                    return true;
                default:
                    return true;
            }
        });
    }

    @Override
    public ActivityComponent getComponent() {
        return sActivityComponent;
    }

    @Override
    public SearchView getSearchView() {
        return mSearchView;
    }

    @Override
    public UINavigator getUINavigator() {
        return mUINavigator;
    }

    @Override
    public void updateFavouritesCount(int count) {
        mFavouritesCounter.setText(count > 0 ? String.valueOf(count) : null);
    }

}
