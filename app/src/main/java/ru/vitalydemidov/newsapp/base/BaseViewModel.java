package ru.vitalydemidov.newsapp.base;

import android.support.annotation.NonNull;

import ru.vitalydemidov.newsapp.data.source.NewsDataSource;
import ru.vitalydemidov.newsapp.util.schedulers.BaseSchedulerProvider;

public class BaseViewModel {

    @NonNull
    protected final NewsDataSource mNewsRepository;


    @NonNull
    protected final BaseSchedulerProvider mSchedulerProvider;


    protected BaseViewModel(@NonNull final NewsDataSource newsRepository,
                            @NonNull final BaseSchedulerProvider schedulerProvider) {
        mNewsRepository = newsRepository;
        mSchedulerProvider = schedulerProvider;
    }

}
