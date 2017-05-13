package ru.vitalydemidov.newsapp.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import ru.vitalydemidov.newsapp.api.NewsApi;
import ru.vitalydemidov.newsapp.data.Article;
import ru.vitalydemidov.newsapp.data.Source;
import ru.vitalydemidov.newsapp.data.source.NewsDataSource;


public class NewsRemoteDataSource implements NewsDataSource {

    public static final List<Source> SOURCES = Arrays.asList(
            new Source("remote source mock1"),
            new Source("remote source mock2"),
            new Source("remote source mock3")
    );


    public static final List<Article> ARTICLES = Arrays.asList(
            new Article("remote article mock1"),
            new Article("remote article mock2"),
            new Article("remote article mock3")
    );


    public NewsRemoteDataSource(@NonNull NewsApi newsApi) {
        // ignore NewsApi
    }


    @Override
    public Observable<List<Source>> getSources(@Nullable String category,
                                               @Nullable String language,
                                               @Nullable String country) {
        return Observable.just(SOURCES);
    }


    @Override
    public Observable<List<Article>> getArticles(@NonNull String sourceId,
                                                 @NonNull String sortBy) {
        return Observable.just(ARTICLES);
    }

}
