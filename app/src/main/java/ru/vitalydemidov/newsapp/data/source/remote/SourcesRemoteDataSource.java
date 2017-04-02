package ru.vitalydemidov.newsapp.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.reactivex.Observable;
import ru.vitalydemidov.newsapp.api.ApiClient;
import ru.vitalydemidov.newsapp.data.Article;
import ru.vitalydemidov.newsapp.data.Source;
import ru.vitalydemidov.newsapp.data.source.SourcesDataSource;

/**
 * Created by vitalydemidov on 25/01/2017.
 */

public class SourcesRemoteDataSource implements SourcesDataSource {

    private static SourcesRemoteDataSource sInstance;


    public static SourcesRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new SourcesRemoteDataSource();
        }
        return sInstance;
    }


    private SourcesRemoteDataSource() {}


    @Override
    public Observable<List<Source>> getSources(@Nullable String category,
                                               @Nullable String language,
                                               @Nullable String country) {
        return ApiClient.provideApiClient().sources(category, language, country)
                .map(sourceResponse -> sourceResponse.sources);
    }


    @Override
    public Observable<List<Article>> getArticles(@NonNull String sourceId) {
        return ApiClient.provideApiClient().articles(sourceId)
                .map(articlesResponse -> articlesResponse.articles);
    }

}
