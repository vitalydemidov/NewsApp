package ru.vitalydemidov.newsapp;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import ru.vitalydemidov.newsapp.data.source.DaggerNewsRepositoryComponent;
import ru.vitalydemidov.newsapp.data.source.NewsRepositoryComponent;

public class NewsApp extends Application {

    private NewsRepositoryComponent mNewsRepositoryComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);

        mNewsRepositoryComponent = DaggerNewsRepositoryComponent.create();
    }


    public NewsRepositoryComponent getNewsRepositoryComponent() {
        return mNewsRepositoryComponent;
    }

}
