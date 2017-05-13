package ru.vitalydemidov.newsapp.data.source.local;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import ru.vitalydemidov.newsapp.data.Article;
import ru.vitalydemidov.newsapp.data.Source;
import ru.vitalydemidov.newsapp.data.source.NewsDataSource;

public class NewsLocalDataSource implements NewsDataSource {

    private static final List<Source> SOURCES = Arrays.asList(
            new Source("local source mock1"),
            new Source("local source mock2"),
            new Source("local source mock3")
    );


    private static final List<Article> ARTICLES = Arrays.asList(
            new Article("local article mock1"),
            new Article("local article mock2"),
            new Article("local article mock3")
    );


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
