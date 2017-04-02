package ru.vitalydemidov.newsapp.articles;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ru.vitalydemidov.newsapp.R;
import ru.vitalydemidov.newsapp.data.source.SourcesRepository;
import ru.vitalydemidov.newsapp.data.source.local.SourcesLocalDataSource;
import ru.vitalydemidov.newsapp.data.source.remote.SourcesRemoteDataSource;
import ru.vitalydemidov.newsapp.util.ActivityUtils;

/**
 * Created by vitalydemidov on 02/04/2017.
 */

@UiThread
public class ArticlesActivity extends AppCompatActivity {

    private static final String EXTRA_SOURCE_ID = "ru.vitalydemidov.newsapp.extra_source_id";


    @NonNull
    private String mSourceId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articles_activity);

        setupToolbar();

        mSourceId = getIntent().getStringExtra(EXTRA_SOURCE_ID);

        // Set up Fragment (View)
        ArticlesFragment articlesFragment =
                (ArticlesFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (articlesFragment == null) {
            articlesFragment = ArticlesFragment.newInstance(mSourceId);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    articlesFragment, R.id.content_frame);
        }

        // Create Presenter
        ArticlesPresenter articlesPresenter = new ArticlesPresenter(mSourceId, articlesFragment,
                SourcesRepository.getInstance(SourcesRemoteDataSource.getInstance(), SourcesLocalDataSource.getInstance()));
    }


    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
