package ru.vitalydemidov.newsapp.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.reactivex.Observable;
import ru.vitalydemidov.newsapp.data.Article;
import ru.vitalydemidov.newsapp.data.Source;

/**
 * Created by vitalydemidov on 25/01/2017.
 */

public interface NewsDataSource {

    Observable<List<Source>> getSources(@Nullable String category,
                                        @Nullable String language,
                                        @Nullable String country);


    Observable<List<Article>> getArticles(@NonNull String sourceId);

}