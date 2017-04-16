package ru.vitalydemidov.newsapp.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import ru.vitalydemidov.newsapp.api.NewsApi;
import ru.vitalydemidov.newsapp.data.Article;
import ru.vitalydemidov.newsapp.data.Source;
import ru.vitalydemidov.newsapp.data.source.NewsDataSource;

public class NewsRemoteDataSource implements NewsDataSource {

    @NonNull
    private NewsApi mNewsApi;


    @Inject
    public NewsRemoteDataSource(@NonNull NewsApi newsApi) {
        mNewsApi = newsApi;
    }


    @Override
    public Observable<List<Source>> getSources(@Nullable String category,
                                               @Nullable String language,
                                               @Nullable String country) {
        return mNewsApi.sources(category, language, country)
                .map(sourceResponse -> sourceResponse.sources);
    }


    @Override
    public Observable<List<Article>> getArticles(@NonNull String sourceId) {
        return mNewsApi.articles(sourceId)
                .map(articlesResponse -> articlesResponse.articles);
    }

}
