package com.korobeinikov.comicsviewer.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.korobeinikov.comicsviewer.ComicsViewerApplication;
import com.korobeinikov.comicsviewer.R;
import com.korobeinikov.comicsviewer.dagger.ComponentOwner;
import com.korobeinikov.comicsviewer.dagger.component.ActivityComponent;
import com.korobeinikov.comicsviewer.dagger.module.ActivityModule;
import com.korobeinikov.comicsviewer.model.RealmComicInfo;
import com.korobeinikov.comicsviewer.realm.ComicRepository;
import com.korobeinikov.comicsviewer.ui.UINavigator;
import com.korobeinikov.comicsviewer.ui.fragment.AboutFragment;
import com.korobeinikov.comicsviewer.ui.fragment.FavouritesFragment;
import com.korobeinikov.comicsviewer.ui.fragment.SearchFragment;
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
    protected UINavigator mUINavigator;
    @Inject
    protected ComicRepository mComicRepository;

    private TextView mFavouritesCounter;
    private RealmResults<RealmComicInfo> mRealmComics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        injectSelf();
        initViews();
    }

    // TODO: 1/30/2017 Implement via Injector
    private void injectSelf() {
        sActivityComponent = ComicsViewerApplication.getAppComponent().plus(new ActivityModule(this));
        sActivityComponent.inject(this);
    }

    private void initViews() {
        setSupportActionBar(mToolbar);
        setupSearchView();
        setupDrawer();
        mUINavigator.start();
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
                mUINavigator.getSearchFragment().onSearchSubmit(query);
                mSearchView.close(true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    // TODO: 1/26/2017 To Presenter
    private void setupDrawer() {
        mFavouritesCounter = (TextView) mNavigationView.getMenu().findItem(R.id.action_favourites).getActionView();
        mNavigationView.setNavigationItemSelectedListener(item -> {
            mDrawerLayout.closeDrawers();
            switch (item.getItemId()) {
                case R.id.action_search:
                    mSearchView.setVisibility(View.VISIBLE);
                    mUINavigator.openFragment(SearchFragment.TAG);
                    return true;
                case R.id.action_favourites:
                    mSearchView.setVisibility(View.GONE);
                    mUINavigator.openFragment(FavouritesFragment.TAG);
                    return true;
                case R.id.action_about:
                    mSearchView.setVisibility(View.GONE);
                    mUINavigator.openFragment(AboutFragment.TAG);
                    return true;
                default:
                    return true;
            }
        });
        listenForRealmComicsUpdates();
    }

    public void listenForRealmComicsUpdates() {
        mRealmComics = mComicRepository.getAllComics();
        mRealmComics.addChangeListener(result -> updateFavouritesCount());
        updateFavouritesCount();
    }

    private void updateFavouritesCount() {
        int count = mRealmComics.size();
        mFavouritesCounter.setText(count > 0 ? String.valueOf(count) : null);
    }

    @Override
    public ActivityComponent getComponent() {
        return sActivityComponent;
    }

}
