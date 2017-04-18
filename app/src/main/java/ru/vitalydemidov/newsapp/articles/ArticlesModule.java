package ru.vitalydemidov.newsapp.articles;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.vitalydemidov.newsapp.data.source.NewsRepository;
import ru.vitalydemidov.newsapp.util.PerActivity;
import ru.vitalydemidov.newsapp.util.schedulers.BaseSchedulerProvider;

/**
 * This is a Dagger 2 module. It used to pass in all necessary dependencies to
 * {@link ArticlesActivity}.
 */

@Module
class ArticlesModule {

    @Provides
    @NonNull
    @PerActivity
    ArticlesContract.Presenter provideArticlesPresenter(@NonNull String sourceId,
                                                        @NonNull NewsRepository newsRepository,
                                                        @NonNull BaseSchedulerProvider schedulerProvider) {
        return new ArticlesPresenter(sourceId,newsRepository, schedulerProvider);
    }


    @Provides
    @NonNull
    @PerActivity
    ArticlesAdapter provideArticlesAdapter() {
        return new ArticlesAdapter();
    }

}
