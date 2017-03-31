package ru.vitalydemidov.newsapp.sources;

import android.support.annotation.Nullable;

/**
 * Created by vitalydemidov on 28/03/2017.
 */

enum SourcesCountryFiltering {

    COUNTRY_ALL(null),
    COUNTRY_AUSTRALIA("au"),
    COUNTRY_GERMANY("de"),
    COUNTRY_GREAT_BRITAIN("gb"),
    COUNTRY_INDIA("in"),
    COUNTRY_ITALY("it"),
    COUNTRY_UNITED_STATES("us");


    @Nullable
    private String mTitle;


    SourcesCountryFiltering(@Nullable String title) {
        mTitle = title;
    }


    @Nullable
    public String getTitle() {
        return mTitle;
    }

}
