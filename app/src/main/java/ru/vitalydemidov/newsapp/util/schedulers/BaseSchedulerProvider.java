package ru.vitalydemidov.newsapp.util.schedulers;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

public interface BaseSchedulerProvider {

    @NonNull
    Scheduler io();


    @NonNull
    Scheduler computation();


    @NonNull
    Scheduler ui();

}
