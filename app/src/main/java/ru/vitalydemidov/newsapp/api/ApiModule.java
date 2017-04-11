package ru.vitalydemidov.newsapp.api;

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
    NewsApi provideNewsApi(Converter.Factory converter,
                           CallAdapter.Factory callAdapter) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(converter)
                .addCallAdapterFactory(callAdapter);

        return builder.build().create(NewsApi.class);
    }


    @Provides
    @Singleton
    Converter.Factory provideConverterFactory() {
        return GsonConverterFactory.create();
    }


    @Provides
    @Singleton
    CallAdapter.Factory provideCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

}
