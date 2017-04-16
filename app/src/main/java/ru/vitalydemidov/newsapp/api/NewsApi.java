package ru.vitalydemidov.newsapp.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import ru.vitalydemidov.newsapp.data.ArticlesResponse;
import ru.vitalydemidov.newsapp.data.SourcesResponse;

public interface NewsApi {

    @GET("sources")
    Observable<SourcesResponse> sources(@Query("category") String category,
                                        @Query("language") String language,
                                        @Query("country") String country);


    @Headers("X-Api-Key: ef3b4169553e4d4cbe2e43ca418f93bf")
    @GET("articles")
    Observable<ArticlesResponse> articles(@Query("source") String sourceId);

}
