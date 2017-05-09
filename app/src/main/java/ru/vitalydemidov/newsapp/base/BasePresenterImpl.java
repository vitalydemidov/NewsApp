package ru.vitalydemidov.newsapp.base;

import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import ru.vitalydemidov.newsapp.data.source.NewsDataSource;
import ru.vitalydemidov.newsapp.util.schedulers.BaseSchedulerProvider;

public abstract class BasePresenterImpl<V> implements BasePresenter<V> {

    @NonNull
    protected final NewsDataSource mNewsRepository;


    @NonNull
    protected final BaseSchedulerProvider mSchedulerProvider;


    @NonNull
    protected final CompositeDisposable mCompositeDisposable;


    protected BasePresenterImpl(@NonNull NewsDataSource newsRepository,
                                @NonNull BaseSchedulerProvider schedulerProvider) {
        mNewsRepository = newsRepository;
        mSchedulerProvider = schedulerProvider;
        mCompositeDisposable = new CompositeDisposable();
    }

}
