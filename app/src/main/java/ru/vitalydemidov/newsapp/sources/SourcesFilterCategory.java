package ru.vitalydemidov.newsapp.sources;

import android.support.annotation.Nullable;

/**
 * Created by vitalydemidov on 28/03/2017.
 */

enum SourcesFilterCategory {

    CATEGORY_ALL(null),
    CATEGORY_BUSINESS("business"),
    CATEGORY_ENTERTAINMENT("entertainment"),
    CATEGORY_GAMING("gaming"),
    CATEGORY_GENERAL("general"),
    CATEGORY_MUSIC("music"),
    CATEGORY_POLITICS("politics"),
    CATEGORY_SCIENCE_AND_NATURE("science-and-nature"),
    CATEGORY_SPORT("sport"),
    CATEGORY_TECHNOLOGY("technology");

    @Nullable
    private String mTitle;

    SourcesFilterCategory(@Nullable String title) {
        mTitle = title;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }
}
