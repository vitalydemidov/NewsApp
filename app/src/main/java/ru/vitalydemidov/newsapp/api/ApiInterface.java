package ru.vitalydemidov.newsapp.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.vitalydemidov.newsapp.data.SourceResponse;

/**
 * Created by vitalydemidov on 19/03/2017.
 */

public interface ApiInterface {

    @GET("sources")
    Observable<SourceResponse> sources(@Query("category") String category,
                                       @Query("language") String language,
                                       @Query("country") String country);

}
