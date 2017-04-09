package ru.vitalydemidov.newsapp.articles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import ru.vitalydemidov.newsapp.NewsApp;
import ru.vitalydemidov.newsapp.R;
import ru.vitalydemidov.newsapp.util.ActivityUtils;

@UiThread
public class ArticlesActivity extends AppCompatActivity {

    private static final String EXTRA_SOURCE_ID = "ru.vitalydemidov.newsapp.extra_source_id";
    private static final String EXTRA_SOURCE_TITLE = "ru.vitalydemidov.newsapp.extra_source_title";


    @Inject
    ArticlesPresenter mArticlesPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articles_activity);

        setupToolbar();

        String sourceId = getIntent().getStringExtra(EXTRA_SOURCE_ID);

        // Set up Fragment (View)
        ArticlesFragment articlesFragment =
                (ArticlesFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (articlesFragment == null) {
            articlesFragment = ArticlesFragment.newInstance(sourceId);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    articlesFragment, R.id.content_frame);
        }

        // Create Presenter
        DaggerArticlesComponent.builder()
                .newsRepositoryComponent(((NewsApp) getApplication()).getNewsRepositoryComponent())
                .articlesPresenterModule(new ArticlesPresenterModule(articlesFragment, sourceId))
                .build()
                .inject(this);

    }


    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra(EXTRA_SOURCE_TITLE));
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
