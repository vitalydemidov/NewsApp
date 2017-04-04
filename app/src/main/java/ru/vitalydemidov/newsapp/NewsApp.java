package ru.vitalydemidov.newsapp;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import ru.vitalydemidov.newsapp.api.ApiClient;

/**
 * Created by vitalydemidov on 20/03/2017.
 */

public class NewsApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApiClient.init();
        Fresco.initialize(this);
    }
}
