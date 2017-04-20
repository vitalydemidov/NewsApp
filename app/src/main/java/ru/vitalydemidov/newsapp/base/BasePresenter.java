package ru.vitalydemidov.newsapp.base;

import android.support.annotation.NonNull;

public interface BasePresenter<V> {

    void attachView(@NonNull V view);

    void detachView();

}
