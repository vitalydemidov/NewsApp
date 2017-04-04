package ru.vitalydemidov.newsapp.data.source.local;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.reactivex.Observable;
import ru.vitalydemidov.newsapp.data.Article;
import ru.vitalydemidov.newsapp.data.Source;
import ru.vitalydemidov.newsapp.data.source.NewsDataSource;

/**
 * Created by vitalydemidov on 26/02/2017.
 */

public class NewsLocalDataSource implements NewsDataSource {

    private static NewsLocalDataSource sInstance;


    public static NewsLocalDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new NewsLocalDataSource();
        }
        return sInstance;
    }


    private NewsLocalDataSource() {}


    @Override
    public Observable<List<Source>> getSources(@Nullable String category,
                                               @Nullable String language,
                                               @Nullable String country) {
        return null;
    }


    @Override
    public Observable<List<Article>> getArticles(@NonNull String sourceId) {
        return null;
    }

}
