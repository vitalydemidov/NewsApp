package ru.vitalydemidov.newsapp.sources;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.vitalydemidov.newsapp.data.source.NewsDataSource;
import ru.vitalydemidov.newsapp.data.source.Repository;
import ru.vitalydemidov.newsapp.util.PerActivity;
import ru.vitalydemidov.newsapp.util.schedulers.BaseSchedulerProvider;

/**
 * This is a Dagger 2 module. It used to pass in all necessary dependencies to
 * {@link SourcesActivity}.
 */

@Module
class SourcesModule {

    @Provides
    @NonNull
    @PerActivity
    SourcesViewModel provideSourcesViewModel(@NonNull @Repository NewsDataSource newsRepository,
                                             @NonNull BaseSchedulerProvider schedulerProvider) {
        return new SourcesViewModel(newsRepository, schedulerProvider);
    }


    @Provides
    @NonNull
    @PerActivity
    SourcesAdapter provideSourcesAdapter() {
        return new SourcesAdapter();
    }

}
