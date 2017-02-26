package ru.vitalydemidov.newsapp.data.source;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import ru.vitalydemidov.newsapp.data.Source;

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


    public static SourcesRepository getInstance(@NonNull SourcesDataSource sourcesRemoteDataSource,
                                                @NonNull SourcesDataSource sourcesLocalDataSource) {
        if (sInstance == null) {
            sInstance = new SourcesRepository(sourcesRemoteDataSource, sourcesLocalDataSource);
        }
        return sInstance;
    }


    @Override
    public Observable<List<Source>> getSources() {
        return null;
    }
}
