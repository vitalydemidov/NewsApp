package ru.vitalydemidov.newsapp;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import ru.vitalydemidov.newsapp.api.ApiModule;
import ru.vitalydemidov.newsapp.data.source.NewsRepositoryModule;

public class NewsApp extends Application {

    private static AppComponent sAppComponent;


    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        sAppComponent = buildAppComponent();
    }


    protected AppComponent buildAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule())
                .newsRepositoryModule(new NewsRepositoryModule())
                .build();
    }

}
