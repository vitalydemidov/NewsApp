package ru.vitalydemidov.newsapp.api;

import android.support.annotation.Nullable;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.vitalydemidov.newsapp.util.CommonUtils;

/**
 * Created by vitalydemidov on 19/03/2017.
 */

public class ApiClient {

    private static final String API_BASE_URL = "https://newsapi.org/v1/";


    @Nullable
    private static ApiInterface mApiInterface;


    private ApiClient() {}


    public static void init() {
        if (mApiInterface == null) {
            synchronized (ApiClient.class) {
                if (mApiInterface == null) {
                    mApiInterface = initApiClient();
                }
            }
        }
    }


    public static ApiInterface provideApiClient() {
        return CommonUtils.checkNotNull(mApiInterface, "Init ApiClient before use it!");
    }


    private static ApiInterface initApiClient() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        return builder.build().create(ApiInterface.class);
    }

}
