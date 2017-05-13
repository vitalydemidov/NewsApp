package ru.vitalydemidov.newsapp.sources;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import ru.vitalydemidov.newsapp.NewsApp;
import ru.vitalydemidov.newsapp.R;
import ru.vitalydemidov.newsapp.articles.ArticlesActivity;
import ru.vitalydemidov.newsapp.data.Source;
import ru.vitalydemidov.newsapp.util.schedulers.BaseSchedulerProvider;

@UiThread
public class SourcesActivity extends AppCompatActivity {

    private static final String CATEGORY_FILTERING_STATE = "ru.vitalydemidov.newsapp.category_filtering_state";
    private static final String LANGUAGE_FILTERING_STATE = "ru.vitalydemidov.newsapp.language_filtering_state";
    private static final String COUNTRY_FILTERING_STATE = "ru.vitalydemidov.newsapp.country_filtering_state";
    private static final String TOOLBAR_TITLE_STATE = "ru.vitalydemidov.newsapp.toolbar_title_state";


    @NonNull
    private Disposable mDisposable;


    @Inject
    @NonNull
    SourcesViewModel mSourcesViewModel;


    @Inject
    @NonNull
    BaseSchedulerProvider mSchedulerProvider;


    @NonNull
    private DrawerLayout mDrawerLayout;


    @NonNull
    private Toolbar mToolbar;
    
    
    private String mToolbarTitle;


    @NonNull
    private SwipeRefreshLayout mSourcesSwipeRefreshLayout;


    @NonNull
    private SourcesAdapter mSourcesAdapter;


    @NonNull
    // TODO: 12/05/2017 check who should handle navigation
    private SourcesAdapter.SourceItemListener mItemListener = this::showArticlesForSourceUi;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sources_activity);
        setupToolbar();
        setupNavigationDrawer(mToolbar);
        setupNavigationView(savedInstanceState);

        DaggerSourcesComponent.builder()
                .appComponent(NewsApp.getAppComponent())
                .build()
                .inject(this);

        initViews();
        onRestoreState(savedInstanceState);
    }


    @Override
    protected void onResume() {
        super.onResume();
        bind();
    }


    @Override
    protected void onPause() {
        unBind();
        super.onPause();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CATEGORY_FILTERING_STATE, mSourcesViewModel.getFiltering().getCategoryFiltering());
        outState.putSerializable(LANGUAGE_FILTERING_STATE, mSourcesViewModel.getFiltering().getLanguageFiltering());
        outState.putSerializable(COUNTRY_FILTERING_STATE, mSourcesViewModel.getFiltering().getCountryFiltering());
        outState.putString(TOOLBAR_TITLE_STATE, mToolbarTitle);
    }


    private void onRestoreState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            FilteringContainer filtering = new FilteringContainer();
            filtering.setCategoryFiltering((SourcesCategoryFiltering) savedInstanceState.getSerializable(CATEGORY_FILTERING_STATE));
            filtering.setLanguageFiltering((SourcesLanguageFiltering) savedInstanceState.getSerializable(LANGUAGE_FILTERING_STATE));
            filtering.setCountryFiltering((SourcesCountryFiltering) savedInstanceState.getSerializable(COUNTRY_FILTERING_STATE));
            mSourcesViewModel.setFiltering(filtering);
            mToolbarTitle = savedInstanceState.getString(TOOLBAR_TITLE_STATE);
        }
        setTitle(mToolbarTitle != null ? mToolbarTitle : getString(R.string.navigation_view_category_all));
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }


    private void setupNavigationDrawer(@NonNull Toolbar toolbar) {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


    private void setupNavigationView(@Nullable Bundle savedInstanceState) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.navigation_view_category_all);
        }
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    SourcesCategoryFiltering category = null;
                    SourcesLanguageFiltering language = null;
                    SourcesCountryFiltering country = null;
                    FilteringContainer filteringContainer = new FilteringContainer();
                    int titleRes = R.string.app_name;

                    switch (menuItem.getItemId()) {

                        // categories
                        case R.id.navigation_view_category_all:
                            category = SourcesCategoryFiltering.CATEGORY_ALL;
                            titleRes = R.string.navigation_view_category_all;
                            break;
                        case R.id.navigation_view_category_business:
                            category = SourcesCategoryFiltering.CATEGORY_BUSINESS;
                            titleRes = R.string.navigation_view_category_business;
                            break;
                        case R.id.navigation_view_category_entertainment:
                            category = SourcesCategoryFiltering.CATEGORY_ENTERTAINMENT;
                            titleRes = R.string.navigation_view_category_entertainment;
                            break;
                        case R.id.navigation_view_category_gaming:
                            category = SourcesCategoryFiltering.CATEGORY_GAMING;
                            titleRes = R.string.navigation_view_category_gaming;
                            break;
                        case R.id.navigation_view_category_general:
                            category = SourcesCategoryFiltering.CATEGORY_GENERAL;
                            titleRes = R.string.navigation_view_category_general;
                            break;
                        case R.id.navigation_view_category_music:
                            category = SourcesCategoryFiltering.CATEGORY_MUSIC;
                            titleRes = R.string.navigation_view_category_music;
                            break;
                        case R.id.navigation_view_category_politics:
                            category = SourcesCategoryFiltering.CATEGORY_POLITICS;
                            titleRes = R.string.navigation_view_category_politics;
                            break;
                        case R.id.navigation_view_category_science_and_nature:
                            category = SourcesCategoryFiltering.CATEGORY_SCIENCE_AND_NATURE;
                            titleRes = R.string.navigation_view_category_science_and_nature;
                            break;
                        case R.id.navigation_view_category_sport:
                            category = SourcesCategoryFiltering.CATEGORY_SPORT;
                            titleRes = R.string.navigation_view_category_sport;
                            break;
                        case R.id.navigation_view_category_technology:
                            category = SourcesCategoryFiltering.CATEGORY_TECHNOLOGY;
                            titleRes = R.string.navigation_view_category_technology;
                            break;

                        // languages
                        case R.id.navigation_view_language_all:
                            language = SourcesLanguageFiltering.LANGUAGE_ALL;
                            titleRes = R.string.navigation_view_language_all;
                            break;
                        case R.id.navigation_view_language_en:
                            language = SourcesLanguageFiltering.LANGUAGE_ENGLISH;
                            titleRes = R.string.navigation_view_language_en;
                            break;
                        case R.id.navigation_view_language_de:
                            language = SourcesLanguageFiltering.LANGUAGE_GERMAN;
                            titleRes = R.string.navigation_view_language_de;
                            break;
                        case R.id.navigation_view_language_fr:
                            language = SourcesLanguageFiltering.LANGUAGE_FRENCH;
                            titleRes = R.string.navigation_view_language_fr;
                            break;

                        // countries
                        case R.id.navigation_view_country_all:
                            country = SourcesCountryFiltering.COUNTRY_ALL;
                            titleRes = R.string.navigation_view_country_all;
                            break;
                        case R.id.navigation_view_country_au:
                            country = SourcesCountryFiltering.COUNTRY_AUSTRALIA;
                            titleRes = R.string.navigation_view_country_au;
                            break;
                        case R.id.navigation_view_country_de:
                            country = SourcesCountryFiltering.COUNTRY_GERMANY;
                            titleRes = R.string.navigation_view_country_de;
                            break;
                        case R.id.navigation_view_country_gb:
                            country = SourcesCountryFiltering.COUNTRY_GREAT_BRITAIN;
                            titleRes = R.string.navigation_view_country_gb;
                            break;
                        case R.id.navigation_view_country_in:
                            country = SourcesCountryFiltering.COUNTRY_INDIA;
                            titleRes = R.string.navigation_view_country_in;
                            break;
                        case R.id.navigation_view_country_it:
                            country = SourcesCountryFiltering.COUNTRY_ITALY;
                            titleRes = R.string.navigation_view_country_it;
                            break;
                        case R.id.navigation_view_country_us:
                            country = SourcesCountryFiltering.COUNTRY_UNITED_STATES;
                            titleRes = R.string.navigation_view_country_us;
                            break;
                    }

                    filteringContainer.setCategoryFiltering(category);
                    filteringContainer.setLanguageFiltering(language);
                    filteringContainer.setCountryFiltering(country);
                    mSourcesViewModel.setFiltering(filteringContainer);

                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    mToolbarTitle = getString(titleRes);
                    setTitle(mToolbarTitle);
                    return true;
                }
        );
    }


    @Inject
    void setSourcesAdapter(@NonNull SourcesAdapter adapter) {
        mSourcesAdapter = adapter;
        mSourcesAdapter.setSourceItemListener(mItemListener);
    }


    private void initViews() {
        initArticlesSwipeRefreshLayout();
        initSourcesRecyclerView();
    }


    private void initArticlesSwipeRefreshLayout() {
        mSourcesSwipeRefreshLayout =
                (SwipeRefreshLayout) findViewById(R.id.sources_swipe_refresh_layout);
        // TODO: 12/05/2017 обдумать, что тут происходит и как должно быть
        mSourcesSwipeRefreshLayout.setOnRefreshListener(() -> mSourcesViewModel.setFiltering(mSourcesViewModel.getFiltering()));
        mSourcesSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }


    private void initSourcesRecyclerView() {
        RecyclerView sourcesRecyclerView = (RecyclerView) findViewById(R.id.sources_recycler_view);
        sourcesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sourcesRecyclerView.setAdapter(mSourcesAdapter);
    }


    public void showLoadingError() {
        Toast.makeText(this, R.string.sources_loading_error, Toast.LENGTH_LONG).show();
    }


    public void showLoadingProgress() {
        mSourcesSwipeRefreshLayout.setRefreshing(true);
    }


    public void hideLoadingProgress() {
        mSourcesSwipeRefreshLayout.setRefreshing(false);
    }


    public void showSources(List<Source> sources) {
        mSourcesAdapter.setSources(sources);
    }


    public void showArticlesForSourceUi(@NonNull Source selectedSource) {
        startActivity(ArticlesActivity.newIntent(this, selectedSource));
    }


    private void bind() {
        showLoadingProgress();
        mDisposable = mSourcesViewModel.loadSources()
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(
                        // onNext
                        sources -> {
                            hideLoadingProgress();
                            showSources(sources);
                        },
                        // onError
                        throwable -> {
                            hideLoadingProgress();
                            showLoadingError();
                        }
                );
    }


    private void unBind() {
        mDisposable.dispose();
    }

}
