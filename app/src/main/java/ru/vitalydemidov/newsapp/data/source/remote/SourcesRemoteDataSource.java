package ru.vitalydemidov.newsapp.data.source.remote;

import ru.vitalydemidov.newsapp.data.source.SourcesDataSource;

/**
 * Created by vitalydemidov on 25/01/2017.
 */

public class SourcesRemoteDataSource implements SourcesDataSource {


    private static SourcesRemoteDataSource sInstance;


    public static SourcesRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new SourcesRemoteDataSource();
        }
        return sInstance;
    }

    private SourcesRemoteDataSource() {

    }


    @Override
    public void getSources() {

    }
}
