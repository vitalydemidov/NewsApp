package ru.vitalydemidov.newsapp.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.reactivex.Observable;
import ru.vitalydemidov.newsapp.data.Source;

import static ru.vitalydemidov.newsapp.util.CommonUtils.checkNotNull;

/**
 * Created by vitalydemidov on 25/01/2017.
 */

public class SourcesRepository implements SourcesDataSource {

    private static SourcesRepository sInstance;

    @NonNull
    private final SourcesDataSource mSourcesRemoteDataSource;

    @NonNull
    private final SourcesDataSource mSourcesLocalDataSource;


    private SourcesRepository(@NonNull SourcesDataSource sourcesRemoteDataSource,
                              @NonNull SourcesDataSource sourcesLocalDataSource) {
        mSourcesRemoteDataSource = checkNotNull(sourcesRemoteDataSource);
        mSourcesLocalDataSource = checkNotNull(sourcesLocalDataSource);
    }


    public static SourcesRepository getInstance(@NonNull SourcesDataSource sourcesRemoteDataSource,
                                                @NonNull SourcesDataSource sourcesLocalDataSource) {
        if (sInstance == null) {
            sInstance = new SourcesRepository(sourcesRemoteDataSource, sourcesLocalDataSource);
        }
        return sInstance;
    }


    @Override
    public Observable<List<Source>> getSources(@Nullable String category,
                                               @Nullable String language,
                                               @Nullable String country) {
        return mSourcesRemoteDataSource.getSources(category, language, country);
    }

}
