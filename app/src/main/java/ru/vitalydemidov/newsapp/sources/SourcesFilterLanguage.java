package ru.vitalydemidov.newsapp.sources;

import android.support.annotation.Nullable;

/**
 * Created by vitalydemidov on 28/03/2017.
 */

enum SourcesFilterLanguage {

    LANGUAGE_ALL(null),
    LANGUAGE_ENGLISH("en"),
    LANGUAGE_GERMAN("de"),
    LANGUAGE_FRENCH("fr");

    @Nullable
    private String mTitle;

    SourcesFilterLanguage(@Nullable String title) {
        mTitle = title;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

}
