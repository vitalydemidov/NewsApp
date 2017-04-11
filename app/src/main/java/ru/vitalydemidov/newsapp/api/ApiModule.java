package ru.vitalydemidov.newsapp.api;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    private static final String API_BASE_URL = "https://newsapi.org/v1/";


    @Provides
    @Singleton
    NewsApi provideNewsApi(Retrofit retrofit) {
        return retrofit.create(NewsApi.class);
    }


    @Provides
    @Singleton
    Retrofit provideRetrofit(Converter.Factory converter, CallAdapter.Factory callAdapter) {
        return new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(converter)
                .addCallAdapterFactory(callAdapter)
                .build();
    }


    @Provides
    @Singleton
    Converter.Factory provideGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }


    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }


    @Provides
    @Singleton
    CallAdapter.Factory provideCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

}
