package ru.vitalydemidov.newsapp.base;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import ru.vitalydemidov.newsapp.data.source.NewsRepository;
import ru.vitalydemidov.newsapp.util.schedulers.BaseSchedulerProvider;

public abstract class BasePresenterImpl implements BasePresenter {

    @NonNull
    protected final NewsRepository mNewsRepository;


    @NonNull
    protected final BaseSchedulerProvider mSchedulerProvider;


    @NonNull
    protected final CompositeDisposable mCompositeDisposable;


    protected BasePresenterImpl(@NonNull NewsRepository newsRepository,
                                @NonNull BaseSchedulerProvider schedulerProvider) {
        mNewsRepository = newsRepository;
        mSchedulerProvider = schedulerProvider;
        mCompositeDisposable = new CompositeDisposable();
    }


    @CallSuper
    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

}
