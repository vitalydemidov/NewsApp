package ru.vitalydemidov.newsapp.sources;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

class FilteringContainer {

    @NonNull
    private SourcesCategoryFiltering mCurrentCategoryFiltering = SourcesCategoryFiltering.CATEGORY_ALL;


    @NonNull
    private SourcesLanguageFiltering mCurrentLanguageFiltering = SourcesLanguageFiltering.LANGUAGE_ALL;


    @NonNull
    private SourcesCountryFiltering mCurrentCountryFiltering = SourcesCountryFiltering.COUNTRY_ALL;


    void setCategoryFiltering(@Nullable SourcesCategoryFiltering category) {
        mCurrentCategoryFiltering = category != null ? category : SourcesCategoryFiltering.CATEGORY_ALL;
    }


    void setLanguageFiltering(@Nullable SourcesLanguageFiltering language) {
        mCurrentLanguageFiltering = language != null ? language : SourcesLanguageFiltering.LANGUAGE_ALL;
    }


    void setCountryFiltering(@Nullable SourcesCountryFiltering country) {
        mCurrentCountryFiltering = country != null ? country : SourcesCountryFiltering.COUNTRY_ALL;
    }


    @NonNull
    SourcesCategoryFiltering getCategoryFiltering() {
        return mCurrentCategoryFiltering;
    }


    @NonNull
    SourcesLanguageFiltering getLanguageFiltering() {
        return mCurrentLanguageFiltering;
    }


    @NonNull
    SourcesCountryFiltering getCountryFiltering() {
        return mCurrentCountryFiltering;
    }

}
