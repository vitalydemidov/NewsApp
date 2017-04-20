package ru.vitalydemidov.newsapp.sources;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.vitalydemidov.newsapp.data.source.NewsRepository;
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
    SourcesContract.Presenter provideSourcesPresenter(@NonNull NewsRepository newsRepository,
                                                      @NonNull BaseSchedulerProvider schedulerProvider) {
        return new SourcesPresenter(newsRepository, schedulerProvider);
    }


    @Provides
    @NonNull
    @PerActivity
    SourcesAdapter provideSourcesAdapter() {
        return new SourcesAdapter();
    }

}
