package ru.vitalydemidov.newsapp.articles;

import android.support.annotation.NonNull;

enum Sort {

    TOP("top"),
    LATEST("latest"),
    POPULAR("popular");


    @NonNull
    private String mTitle;


    Sort(@NonNull String title) {
        mTitle = title;
    }


    @NonNull
    String getTitle() {
        return mTitle;
    }


    @NonNull
    static Sort fromValue(@NonNull String title) {
        for (Sort sort : values()) {
            if (sort.getTitle().equals(title)) {
                return sort;
            }
        }
        return Sort.TOP;
    }

}
