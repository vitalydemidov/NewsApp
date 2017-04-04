package ru.vitalydemidov.newsapp.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.reactivex.Observable;
import ru.vitalydemidov.newsapp.api.ApiClient;
import ru.vitalydemidov.newsapp.data.Article;
import ru.vitalydemidov.newsapp.data.Source;
import ru.vitalydemidov.newsapp.data.source.NewsDataSource;

/**
 * Created by vitalydemidov on 25/01/2017.
 */

public class NewsRemoteDataSource implements NewsDataSource {

    private static NewsRemoteDataSource sInstance;


    public static NewsRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new NewsRemoteDataSource();
        }
        return sInstance;
    }


    private NewsRemoteDataSource() {}


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
