package ru.vitalydemidov.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.vitalydemidov.newsapp.util.schedulers.BaseSchedulerProvider;
import ru.vitalydemidov.newsapp.util.schedulers.SchedulerProvider;

@Singleton
@Module
class AppModule {

    private Context mAppContext;


    AppModule(@NonNull Context context) {
        mAppContext = context;
    }


    @Provides
    @Singleton
    Context provideContext() {
        return mAppContext;
    }


    @Provides
    @Singleton
    BaseSchedulerProvider provideSchedulerProvider() {
        return new SchedulerProvider();
    }

}
