package ru.vitalydemidov.newsapp.data.source;

import android.support.annotation.Nullable;

import java.util.List;

import io.reactivex.Observable;
import ru.vitalydemidov.newsapp.data.Source;

/**
 * Created by vitalydemidov on 25/01/2017.
 */

public interface SourcesDataSource {

    Observable<List<Source>> getSources(@Nullable String category,
                                        @Nullable String language,
                                        @Nullable String country);

}
