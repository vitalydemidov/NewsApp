package ru.vitalydemidov.newsapp.sources;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

        void saveState(@Nullable Bundle outState);

        void restoreState(@Nullable Bundle savedState);

        void setCategoryFiltering(@NonNull SourcesCategoryFiltering category);

        void setLanguageFiltering(@NonNull SourcesLanguageFiltering language);

        void setCountryFiltering(@NonNull SourcesCountryFiltering country);

    }

}
