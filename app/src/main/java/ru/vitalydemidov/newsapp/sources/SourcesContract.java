package ru.vitalydemidov.newsapp.sources;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import ru.vitalydemidov.newsapp.base.BasePresenter;
import ru.vitalydemidov.newsapp.base.BaseView;
import ru.vitalydemidov.newsapp.data.Source;

interface SourcesContract {

    interface View extends BaseView {

        void showSources(List<Source> sources);

        void showArticlesForSourceUi(@NonNull Source selectedSource);

    }


    interface Presenter extends BasePresenter<View> {

        void loadSources();

        void openArticlesForSource(@NonNull Source selectedSource);

        void setCategoryFiltering(@Nullable SourcesCategoryFiltering category);

        void setLanguageFiltering(@Nullable SourcesLanguageFiltering language);

        void setCountryFiltering(@Nullable SourcesCountryFiltering country);

        @NonNull
        SourcesCategoryFiltering getCategoryFiltering();

        @NonNull
        SourcesLanguageFiltering getLanguageFiltering();

        @NonNull
        SourcesCountryFiltering getCountryFiltering();

    }

}
