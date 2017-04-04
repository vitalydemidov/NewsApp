package ru.vitalydemidov.newsapp.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.reactivex.Observable;
import ru.vitalydemidov.newsapp.data.Article;
import ru.vitalydemidov.newsapp.data.Source;

import static ru.vitalydemidov.newsapp.util.CommonUtils.checkNotNull;

/**
 * Created by vitalydemidov on 25/01/2017.
 */

public class NewsRepository implements NewsDataSource {

    private static NewsRepository sInstance;


    @NonNull
    private final NewsDataSource mSourcesRemoteDataSource;


    @NonNull
    private final NewsDataSource mSourcesLocalDataSource;


    private NewsRepository(@NonNull NewsDataSource sourcesRemoteDataSource,
                           @NonNull NewsDataSource sourcesLocalDataSource) {
        mSourcesRemoteDataSource = checkNotNull(sourcesRemoteDataSource);
        mSourcesLocalDataSource = checkNotNull(sourcesLocalDataSource);
    }


    public static NewsRepository getInstance(@NonNull NewsDataSource sourcesRemoteDataSource,
                                             @NonNull NewsDataSource sourcesLocalDataSource) {
        if (sInstance == null) {
            sInstance = new NewsRepository(sourcesRemoteDataSource, sourcesLocalDataSource);
        }
        return sInstance;
    }


    @Override
    public Observable<List<Source>> getSources(@Nullable String category,
                                               @Nullable String language,
                                               @Nullable String country) {
        return mSourcesRemoteDataSource.getSources(category, language, country);
    }


    @Override
    public Observable<List<Article>> getArticles(@NonNull String sourceId) {
        return mSourcesRemoteDataSource.getArticles(sourceId);
    }

}
