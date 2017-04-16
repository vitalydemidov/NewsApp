package ru.vitalydemidov.newsapp.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import ru.vitalydemidov.newsapp.data.Article;
import ru.vitalydemidov.newsapp.data.Source;

@Singleton
public class NewsRepository implements NewsDataSource {

    private final NewsDataSource mNewsRemoteDataSource;


    private final NewsDataSource mNewsLocalDataSource;


    @Inject
    NewsRepository(@Remote NewsDataSource newsRemoteDataSource,
                   @Local NewsDataSource newsLocalDataSource) {
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
