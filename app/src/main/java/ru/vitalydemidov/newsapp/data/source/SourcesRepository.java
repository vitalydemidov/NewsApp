package ru.vitalydemidov.newsapp.data.source;

import android.support.annotation.NonNull;

import static ru.vitalydemidov.newsapp.util.CommonUtils.checkNotNull;

/**
 * Created by vitalydemidov on 25/01/2017.
 */

public class SourcesRepository implements SourcesDataSource {

    private static SourcesRepository sInstance;


    private final SourcesDataSource mSourcesRemoteDataSource;


    private final SourcesDataSource mSourcesLocalDataSource;


    private SourcesRepository(@NonNull SourcesDataSource sourcesRemoteDataSource,
                              @NonNull SourcesDataSource sourcesLocalDataSource) {
        mSourcesRemoteDataSource = checkNotNull(sourcesRemoteDataSource);
        mSourcesLocalDataSource = checkNotNull(sourcesLocalDataSource);
    }


    private SourcesRepository getInstance(@NonNull SourcesDataSource sourcesRemoteDataSource,
                                          @NonNull SourcesDataSource sourcesLocalDataSource) {
        if (sInstance == null) {
            sInstance = new SourcesRepository(sourcesRemoteDataSource, sourcesLocalDataSource);
        }
        return sInstance;
    }


    @Override
    public void getSources() {

    }
}
