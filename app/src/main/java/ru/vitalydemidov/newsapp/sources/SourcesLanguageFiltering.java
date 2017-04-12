package ru.vitalydemidov.newsapp.sources;

import android.support.annotation.Nullable;

enum SourcesLanguageFiltering {

    LANGUAGE_ALL(null),
    LANGUAGE_ENGLISH("en"),
    LANGUAGE_GERMAN("de"),
    LANGUAGE_FRENCH("fr");


    @Nullable
    private String mTitle;


    SourcesLanguageFiltering(@Nullable String title) {
        mTitle = title;
    }


    @Nullable
    public String getTitle() {
        return mTitle;
    }

}
