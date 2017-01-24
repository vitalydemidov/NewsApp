package ru.vitalydemidov.newsapp.sources;

import android.support.annotation.NonNull;

import static ru.vitalydemidov.newsapp.util.CommonUtils.checkNotNull;

/**
 * Created by vitalydemidov on 23/01/2017.
 */

public class SourcesPresenter implements SourcesContract.Presenter {

    private final SourcesContract.View mSourcesView;

    public SourcesPresenter(@NonNull SourcesContract.View sourcesView) {
        mSourcesView = checkNotNull(sourcesView);
        mSourcesView.setPresenter(this);
    }

    @Override
    public void start() {
        loadSources();
    }

    @Override
    public void loadSources() {

    }
}
