package ru.vitalydemidov.newsapp.util.schedulers;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SchedulerProvider implements BaseSchedulerProvider {

    @Override
    @NonNull
    public Scheduler io() {
        return Schedulers.io();
    }


    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }


    @Override
    @NonNull
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

}
