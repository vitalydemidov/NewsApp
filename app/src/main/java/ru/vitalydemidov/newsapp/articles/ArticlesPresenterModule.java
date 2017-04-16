package ru.vitalydemidov.newsapp.articles;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger 2 module. It used to pass in {@link ArticlesContract.View} and
 * {@link String sourceId} dependencies to {@link ArticlesPresenter}.
 */

@Module
class ArticlesPresenterModule {

    @NonNull
    private final ArticlesContract.View mArticlesView;


    @NonNull
    private final String mSourceId;


    ArticlesPresenterModule(@NonNull ArticlesContract.View articlesView,
                            @NonNull String sourceId) {
        mArticlesView = articlesView;
        mSourceId = sourceId;
    }


    @Provides
    ArticlesContract.View provideArticlesContractView() {
        return mArticlesView;
    }


    @Provides
    String provideSourceId() {
        return mSourceId;
    }

}
