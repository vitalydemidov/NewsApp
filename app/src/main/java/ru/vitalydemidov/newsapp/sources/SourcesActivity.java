package ru.vitalydemidov.newsapp.sources;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ru.vitalydemidov.newsapp.R;
import ru.vitalydemidov.newsapp.util.ActivityUtils;

/**
 * Created by vitalydemidov on 23/01/2017.
 */

public class SourcesActivity extends AppCompatActivity {

    private SourcesPresenter mSourcesPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sources_activity);

        // Set up Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up Fragment (View)
        SourcesFragment sourcesFragment =
                (SourcesFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (sourcesFragment == null) {
            sourcesFragment = SourcesFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), sourcesFragment, R.id.content_frame);
        }

        // Create Presenter
        SourcesPresenter sourcesPresenter = new SourcesPresenter();
    }
}
