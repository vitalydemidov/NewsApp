package ru.vitalydemidov.newsapp.base;

import android.support.annotation.NonNull;

/**
 * Created by vitalydemidov on 23/01/2017.
 */

public interface BaseView<T> {

    void setPresenter(@NonNull T presenter);

    void showLoadingError();

    void showLoadingProgress(boolean showProgress);

}
