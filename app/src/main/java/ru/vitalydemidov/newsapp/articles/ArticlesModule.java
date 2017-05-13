package ru.vitalydemidov.newsapp.articles;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.vitalydemidov.newsapp.data.source.NewsDataSource;
import ru.vitalydemidov.newsapp.data.source.Repository;
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
    ArticlesViewModel provideArticlesViewModel(@NonNull String sourceId,
                                               @NonNull @Repository NewsDataSource newsRepository,
                                               @NonNull BaseSchedulerProvider schedulerProvider) {
        return new ArticlesViewModel(sourceId, newsRepository, schedulerProvider);
    }


    @Provides
    @NonNull
    @PerActivity
    ArticlesAdapter provideArticlesAdapter() {
        return new ArticlesAdapter();
    }

}
