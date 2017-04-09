package ru.vitalydemidov.newsapp.sources;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger 2 module. It used to pass in {@link SourcesContract.View} dependency
 * to {@link SourcesPresenter}.
 */

@Module
class SourcesPresenterModule {

    @NonNull
    private final SourcesContract.View mSourcesView;


    SourcesPresenterModule(@NonNull SourcesContract.View sourcesView) {
        mSourcesView = sourcesView;
    }


    @Provides
    SourcesContract.View provideSourcesContractView() {
        return mSourcesView;
    }

}
