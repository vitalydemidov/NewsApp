package ru.vitalydemidov.newsapp.sources;

import android.support.annotation.NonNull;

import java.util.List;

import ru.vitalydemidov.newsapp.base.BasePresenter;
import ru.vitalydemidov.newsapp.base.BaseView;
import ru.vitalydemidov.newsapp.data.Source;

interface SourcesContract {

    interface View extends BaseView<Presenter> {

        void showSources(List<Source> sources);

    }


    interface Presenter extends BasePresenter {

        void loadSources();

        void setCategoryFiltering(SourcesCategoryFiltering category);

        void setLanguageFiltering(SourcesLanguageFiltering language);

        void setCountryFiltering(SourcesCountryFiltering country);

        @NonNull
        SourcesCategoryFiltering getCategoryFiltering();

        @NonNull
        SourcesLanguageFiltering getLanguageFiltering();

        @NonNull
        SourcesCountryFiltering getCountryFiltering();

    }

}
