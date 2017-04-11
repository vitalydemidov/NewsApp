package ru.vitalydemidov.newsapp.data.source;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.vitalydemidov.newsapp.api.NewsApi;
import ru.vitalydemidov.newsapp.data.source.local.NewsLocalDataSource;
import ru.vitalydemidov.newsapp.data.source.remote.NewsRemoteDataSource;

/**
 * This is a Dagger 2 module. It used by Dagger 2 to inject the required arguments into
 * the {@link NewsRepository}.
 */

@Module
public class NewsRepositoryModule {

    @Singleton
    @Provides
    @Local
    NewsDataSource provideNewsLocalDataSource() {
        return new NewsLocalDataSource();
    }


    @Singleton
    @Provides
    @Remote
    NewsDataSource provideNewsRemoteDataSource(NewsApi newsApi) {
        return new NewsRemoteDataSource(newsApi);
    }

}
