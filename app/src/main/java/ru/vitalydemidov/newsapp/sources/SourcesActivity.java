package ru.vitalydemidov.newsapp.sources;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import ru.vitalydemidov.newsapp.NewsApp;
import ru.vitalydemidov.newsapp.R;
import ru.vitalydemidov.newsapp.util.ActivityUtils;

@UiThread
public class SourcesActivity extends AppCompatActivity {

    @NonNull
    private Toolbar mToolbar;


    @NonNull
    private DrawerLayout mDrawerLayout;


    @NonNull
    @Inject
    SourcesPresenter mSourcesPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sources_activity);

        setupToolbar();
        setupNavigationDrawer(mToolbar);
        setupNavigationView(savedInstanceState);

        // Set up Fragment (View)
        SourcesFragment sourcesFragment =
                (SourcesFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (sourcesFragment == null) {
            sourcesFragment = SourcesFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    sourcesFragment, R.id.content_frame);
        }

        // Create Presenter
        DaggerSourcesComponent.builder()
                .newsRepositoryComponent(((NewsApp) getApplication()).getNewsRepositoryComponent())
                .sourcesPresenterModule(new SourcesPresenterModule(sourcesFragment))
                .build()
                .inject(this);
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

                    switch (menuItem.getItemId()) {

                        // categories
                        case R.id.navigation_view_category_all:
                            category = SourcesCategoryFiltering.CATEGORY_ALL;
                            break;
                        case R.id.navigation_view_category_business:
                            category = SourcesCategoryFiltering.CATEGORY_BUSINESS;
                            break;
                        case R.id.navigation_view_category_entertainment:
                            category = SourcesCategoryFiltering.CATEGORY_ENTERTAINMENT;
                            break;
                        case R.id.navigation_view_category_gaming:
                            category = SourcesCategoryFiltering.CATEGORY_GAMING;
                            break;
                        case R.id.navigation_view_category_general:
                            category = SourcesCategoryFiltering.CATEGORY_GENERAL;
                            break;
                        case R.id.navigation_view_category_music:
                            category = SourcesCategoryFiltering.CATEGORY_MUSIC;
                            break;
                        case R.id.navigation_view_category_politics:
                            category = SourcesCategoryFiltering.CATEGORY_POLITICS;
                            break;
                        case R.id.navigation_view_category_science_and_nature:
                            category = SourcesCategoryFiltering.CATEGORY_SCIENCE_AND_NATURE;
                            break;
                        case R.id.navigation_view_category_sport:
                            category = SourcesCategoryFiltering.CATEGORY_SPORT;
                            break;
                        case R.id.navigation_view_category_technology:
                            category = SourcesCategoryFiltering.CATEGORY_TECHNOLOGY;
                            break;

                        // languages
                        case R.id.navigation_view_language_all:
                            language = SourcesLanguageFiltering.LANGUAGE_ALL;
                            break;
                        case R.id.navigation_view_language_en:
                            language = SourcesLanguageFiltering.LANGUAGE_ENGLISH;
                            break;
                        case R.id.navigation_view_language_de:
                            language = SourcesLanguageFiltering.LANGUAGE_GERMAN;
                            break;
                        case R.id.navigation_view_language_fr:
                            language = SourcesLanguageFiltering.LANGUAGE_FRENCH;
                            break;

                        // countries
                        case R.id.navigation_view_country_all:
                            country = SourcesCountryFiltering.COUNTRY_ALL;
                            break;
                        case R.id.navigation_view_country_au:
                            country = SourcesCountryFiltering.COUNTRY_AUSTRALIA;
                            break;
                        case R.id.navigation_view_country_de:
                            country = SourcesCountryFiltering.COUNTRY_GERMANY;
                            break;
                        case R.id.navigation_view_country_gb:
                            country = SourcesCountryFiltering.COUNTRY_GREAT_BRITAIN;
                            break;
                        case R.id.navigation_view_country_in:
                            country = SourcesCountryFiltering.COUNTRY_INDIA;
                            break;
                        case R.id.navigation_view_country_it:
                            country = SourcesCountryFiltering.COUNTRY_ITALY;
                            break;
                        case R.id.navigation_view_country_us:
                            country = SourcesCountryFiltering.COUNTRY_UNITED_STATES;
                            break;
                    }

                    mSourcesPresenter.setCategoryFiltering(category);
                    mSourcesPresenter.setLanguageFiltering(language);
                    mSourcesPresenter.setCountryFiltering(country);
                    mSourcesPresenter.loadSources();

                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
        );
    }

}
