package com.korobeinikov.comicsviewer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.korobeinikov.comicsviewer.ComicsViewerApplication;
import com.korobeinikov.comicsviewer.R;
import com.korobeinikov.comicsviewer.dagger.ComponentOwner;
import com.korobeinikov.comicsviewer.dagger.component.ActivityComponent;
import com.korobeinikov.comicsviewer.dagger.module.ActivityModule;
import com.korobeinikov.comicsviewer.model.RealmComicInfo;
import com.korobeinikov.comicsviewer.realm.ComicRepository;
import com.lapism.searchview.SearchView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

// TODO: 1/26/2017 Extract common logic to base class
public class MainActivity extends AppCompatActivity implements ComponentOwner<ActivityComponent> {

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
    protected ComicRepository mComicRepository;

    private FragmentManager mFragmentManager;
    private TextView mFavouritesCounter;
    private RealmResults<RealmComicInfo> mRealmComics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        openSearchFragment();
        injectSelf();
        initViews();
    }

    private void openSearchFragment() {
        mFragmentManager = getSupportFragmentManager();
        if (mFragmentManager.findFragmentById(R.id.container) == null) {
            SearchFragment fragment = SearchFragment.newInstance();
            mFragmentManager.beginTransaction().add(R.id.container, fragment, SearchFragment.TAG).commit();
        }
    }

    // TODO: 1/30/2017 Implement via Injector
    private void injectSelf() {
        if (sActivityComponent == null) {
            sActivityComponent = ComicsViewerApplication.getAppComponent().plus(new ActivityModule());
        }
        sActivityComponent.inject(this);
    }

    private void initViews() {
        setSupportActionBar(mToolbar);
        setupSearchView();
        setupDrawer();
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
                ((SearchFragment) mFragmentManager.findFragmentByTag(SearchFragment.TAG)).onSearchSubmit(query);
                mSearchView.close(true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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
        listenForRealmComicsUpdates();
    }

    public void listenForRealmComicsUpdates() {
        mRealmComics = mComicRepository.getAllComics();
        mRealmComics.addChangeListener(result -> {
            updateFavouritesCount();
        });
        updateFavouritesCount();
    }

    private void updateFavouritesCount() {
        int count = mRealmComics.size();
        mFavouritesCounter.setText(count > 0 ? String.valueOf(count) : null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            sActivityComponent = null;
        }
    }

    @Override
    public ActivityComponent getComponent() {
        return sActivityComponent;
    }

}
