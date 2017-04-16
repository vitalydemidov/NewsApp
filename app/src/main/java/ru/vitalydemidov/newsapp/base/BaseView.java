package ru.vitalydemidov.newsapp.base;

import android.support.annotation.NonNull;

public interface BaseView<T> {

    void setPresenter(@NonNull T presenter);

    void showLoadingError();

    void showLoadingProgress(boolean showProgress);

}
