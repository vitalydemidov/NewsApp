package ru.vitalydemidov.newsapp.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class CommonUtils {

    public static <T> T checkNotNull(@Nullable T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    public static <T> T checkNotNull(@Nullable T reference, @NonNull String exceptionMessage) {
        if (reference == null) {
            throw new NullPointerException(exceptionMessage);
        }
        return reference;
    }

}
