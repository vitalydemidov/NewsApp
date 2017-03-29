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

import ru.vitalydemidov.newsapp.R;
import ru.vitalydemidov.newsapp.data.source.SourcesRepository;
import ru.vitalydemidov.newsapp.data.source.local.SourcesLocalDataSource;
import ru.vitalydemidov.newsapp.data.source.remote.SourcesRemoteDataSource;
import ru.vitalydemidov.newsapp.util.ActivityUtils;

/**
 * Created by vitalydemidov on 23/01/2017.
 */

@UiThread
public class SourcesActivity extends AppCompatActivity {

    @NonNull
    private Toolbar mToolbar;


    @NonNull
    private DrawerLayout mDrawerLayout;


    @NonNull
    private SourcesPresenter mSourcesPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sources_activity);

        setupToolbar();
        setupNavigationDrawer(mToolbar);
        setupNavigationView();

        // Set up Fragment (View)
        SourcesFragment sourcesFragment =
                (SourcesFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (sourcesFragment == null) {
            sourcesFragment = SourcesFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), sourcesFragment, R.id.content_frame);
        }

        // Create Presenter
        mSourcesPresenter = new SourcesPresenter(SourcesRepository.getInstance(SourcesRemoteDataSource.getInstance(),
                SourcesLocalDataSource.getInstance()), sourcesFragment);
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


    private void setupNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    SourcesFilterCategory filterCategory = SourcesFilterCategory.CATEGORY_ALL;
                    SourcesFilterLanguage filterLanguage = SourcesFilterLanguage.LANGUAGE_ALL;
                    SourcesFilterCountry filterCountry = SourcesFilterCountry.COUNTRY_ALL;
                    int titleRes = R.string.navigation_view_category_all;

                    switch (menuItem.getItemId()) {

                        // categories
                        case R.id.navigation_view_category_all:
                            filterCategory = SourcesFilterCategory.CATEGORY_ALL;
                            titleRes = R.string.activity_sources_label;
                            break;
                        case R.id.navigation_view_category_business:
                            filterCategory = SourcesFilterCategory.CATEGORY_BUSINESS;
                            titleRes = R.string.navigation_view_category_business;
                            break;
                        case R.id.navigation_view_category_entertainment:
                            filterCategory = SourcesFilterCategory.CATEGORY_ENTERTAINMENT;
                            titleRes = R.string.navigation_view_category_entertainment;
                            break;
                        case R.id.navigation_view_category_gaming:
                            filterCategory = SourcesFilterCategory.CATEGORY_GAMING;
                            titleRes = R.string.navigation_view_category_gaming;
                            break;
                        case R.id.navigation_view_category_general:
                            filterCategory = SourcesFilterCategory.CATEGORY_GENERAL;
                            titleRes = R.string.navigation_view_category_general;
                            break;
                        case R.id.navigation_view_category_music:
                            filterCategory = SourcesFilterCategory.CATEGORY_MUSIC;
                            titleRes = R.string.navigation_view_category_music;
                            break;
                        case R.id.navigation_view_category_politics:
                            filterCategory = SourcesFilterCategory.CATEGORY_POLITICS;
                            titleRes = R.string.navigation_view_category_politics;
                            break;
                        case R.id.navigation_view_category_science_and_nature:
                            filterCategory = SourcesFilterCategory.CATEGORY_SCIENCE_AND_NATURE;
                            titleRes = R.string.navigation_view_category_science_and_nature;
                            break;
                        case R.id.navigation_view_category_sport:
                            filterCategory = SourcesFilterCategory.CATEGORY_SPORT;
                            titleRes = R.string.navigation_view_category_sport;
                            break;
                        case R.id.navigation_view_category_technology:
                            filterCategory = SourcesFilterCategory.CATEGORY_TECHNOLOGY;
                            titleRes = R.string.navigation_view_category_technology;
                            break;

                        // languages
                        case R.id.navigation_view_language_all:
                            filterLanguage = SourcesFilterLanguage.LANGUAGE_ALL;
                            break;
                        case R.id.navigation_view_language_en:
                            filterLanguage = SourcesFilterLanguage.LANGUAGE_ENGLISH;
                            break;
                        case R.id.navigation_view_language_de:
                            filterLanguage = SourcesFilterLanguage.LANGUAGE_GERMAN;
                            break;
                        case R.id.navigation_view_language_fr:
                            filterLanguage = SourcesFilterLanguage.LANGUAGE_FRENCH;
                            break;

                        // countries
                        case R.id.navigation_view_country_all:
                            filterCountry = SourcesFilterCountry.COUNTRY_ALL;
                            break;
                        case R.id.navigation_view_country_au:
                            filterCountry = SourcesFilterCountry.COUNTRY_AUSTRALIA;
                            break;
                        case R.id.navigation_view_country_de:
                            filterCountry = SourcesFilterCountry.COUNTRY_GERMANY;
                            break;
                        case R.id.navigation_view_country_gb:
                            filterCountry = SourcesFilterCountry.COUNTRY_GREAT_BRITAIN;
                            break;
                        case R.id.navigation_view_country_in:
                            filterCountry = SourcesFilterCountry.COUNTRY_INDIA;
                            break;
                        case R.id.navigation_view_country_it:
                            filterCountry = SourcesFilterCountry.COUNTRY_ITALY;
                            break;
                        case R.id.navigation_view_country_us:
                            filterCountry = SourcesFilterCountry.COUNTRY_UNITED_STATES;
                            break;
                    }

                    mToolbar.setTitle(titleRes);

                    mSourcesPresenter.loadSources(filterCategory.getTitle(),
                                                  filterLanguage.getTitle(),
                                                  filterCountry.getTitle());

                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
        );
    }

}
