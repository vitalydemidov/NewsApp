package ru.vitalydemidov.newsapp.sources;

import android.support.annotation.Nullable;

import java.util.List;

import ru.vitalydemidov.newsapp.base.BasePresenter;
import ru.vitalydemidov.newsapp.base.BaseView;
import ru.vitalydemidov.newsapp.data.Source;

/**
 * Created by vitalydemidov on 23/01/2017.
 */

interface SourcesContract {

    interface View extends BaseView<Presenter> {

        void showSources(List<Source> sources);

        void showLoadingError();

        void showLoadingProgress(boolean showProgress);

    }

    interface Presenter extends BasePresenter {

        void loadSources(@Nullable String category,
                         @Nullable String language,
                         @Nullable String country);

    }

}
