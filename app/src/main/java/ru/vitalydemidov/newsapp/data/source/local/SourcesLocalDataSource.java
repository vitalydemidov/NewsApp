package ru.vitalydemidov.newsapp.data.source.local;

import java.util.List;

import io.reactivex.Observable;
import ru.vitalydemidov.newsapp.data.Source;
import ru.vitalydemidov.newsapp.data.source.SourcesDataSource;

/**
 * Created by vitalydemidov on 26/02/2017.
 */

public class SourcesLocalDataSource implements SourcesDataSource {

    private static SourcesLocalDataSource sInstance;


    public static SourcesLocalDataSource getInstance() {
        return sInstance != null ? sInstance : new SourcesLocalDataSource();
    }


    private SourcesLocalDataSource() {}


    @Override
    public Observable<List<Source>> getSources() {
        return null;
    }
}
