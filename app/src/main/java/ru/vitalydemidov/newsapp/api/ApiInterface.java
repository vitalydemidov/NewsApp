package ru.vitalydemidov.newsapp.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.vitalydemidov.newsapp.data.Source;

/**
 * Created by vitalydemidov on 19/03/2017.
 */

public interface ApiInterface {

    @GET("sources")
    Call<List<Source>> sources();

}
