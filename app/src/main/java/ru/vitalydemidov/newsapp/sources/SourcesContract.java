package ru.vitalydemidov.newsapp.sources;

import android.support.annotation.NonNull;

import java.util.List;

import ru.vitalydemidov.newsapp.base.BasePresenter;
import ru.vitalydemidov.newsapp.base.BaseView;
import ru.vitalydemidov.newsapp.data.Source;

interface SourcesContract {

    interface View extends BaseView {

        void showSources(@NonNull List<Source> sources);

        void showArticlesForSource(@NonNull Source selectedSource);

    }


    interface Presenter extends BasePresenter<View> {

        void loadSources();

        void openArticlesForSource(@NonNull Source selectedSource);

        void setFiltering(@NonNull FilteringContainer filtering);

        @NonNull
        FilteringContainer getFiltering();

    }

}
