package ru.vitalydemidov.newsapp.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import ru.vitalydemidov.newsapp.data.Result;

/**
 * Created by vitalydemidov on 19/03/2017.
 */

public interface ApiInterface {

    @GET("sources")
    Observable<Result> sources();

}
