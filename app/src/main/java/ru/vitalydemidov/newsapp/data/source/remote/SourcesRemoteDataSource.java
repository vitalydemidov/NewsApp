package ru.vitalydemidov.newsapp.data.source.remote;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.vitalydemidov.newsapp.api.ApiClient;
import ru.vitalydemidov.newsapp.data.Source;
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

    private SourcesRemoteDataSource() {}


    @Override
    public Observable<List<Source>> getSources() {
        return ApiClient.provideApiClient().sources()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(
                        result -> Observable.just(result.sources)
                );
    }

}
