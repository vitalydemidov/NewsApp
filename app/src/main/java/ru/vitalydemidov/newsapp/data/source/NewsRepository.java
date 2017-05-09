package ru.vitalydemidov.newsapp.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.reactivex.Observable;
import ru.vitalydemidov.newsapp.data.Article;
import ru.vitalydemidov.newsapp.data.Source;

class NewsRepository implements NewsDataSource {

    @NonNull
    private final NewsDataSource mNewsRemoteDataSource;

    @NonNull
    private final NewsDataSource mNewsLocalDataSource;



    NewsRepository(@NonNull NewsDataSource newsRemoteDataSource,
                   @NonNull NewsDataSource newsLocalDataSource) {
        mNewsRemoteDataSource = newsRemoteDataSource;
        mNewsLocalDataSource = newsLocalDataSource;
    }


    @Override
    public Observable<List<Source>> getSources(@Nullable String category,
                                               @Nullable String language,
                                               @Nullable String country) {
        return mNewsRemoteDataSource.getSources(category, language, country);
    }


    @Override
    public Observable<List<Article>> getArticles(@NonNull String sourceId) {
        return mNewsRemoteDataSource.getArticles(sourceId);
    }

}
