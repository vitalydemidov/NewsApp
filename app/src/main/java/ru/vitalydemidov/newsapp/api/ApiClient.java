package ru.vitalydemidov.newsapp.api;

import android.support.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.vitalydemidov.newsapp.util.CommonUtils;

/**
 * Created by vitalydemidov on 19/03/2017.
 */

public class ApiClient {

    private static final String API_BASE_URL = "https://newsapi.org/v1/";

    private static volatile ApiClient sApiClient;

    @NonNull
    private final ApiInterface mApiInterface;


    private ApiClient() {
        mApiInterface = createApiClient(ApiInterface.class);
    }


    public static void init() {
        if (sApiClient == null) {
            synchronized (ApiClient.class) {
                if (sApiClient == null) {
                    sApiClient = new ApiClient();
                }
            }
        }
    }


    public static ApiClient getApiClient() {
        return CommonUtils.checkNotNull(sApiClient, "Init ApiClient before use it!");
    }


    public static ApiInterface provideApiClient() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        return builder.build().create(ApiInterface.class);
    }


    private <T> T createApiClient(@NonNull Class<T> apiClientClass) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        return builder.build().create(apiClientClass);
    }

}
