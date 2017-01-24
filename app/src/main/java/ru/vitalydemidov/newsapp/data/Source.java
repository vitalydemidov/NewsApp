package ru.vitalydemidov.newsapp.data;

import java.util.Arrays;

/**
 * Created by vitalydemidov on 24/01/2017.
 */

public class Source {

    private String mId;

    private String mName;

    private String mDescription;

    private String mUrl;

    private String mCategory;

    private String[] mUrlsToLogos;

    private String[] mSortBysAvailable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Source source = (Source) o;

        if (!mId.equals(source.mId)) return false;
        if (!mName.equals(source.mName)) return false;
        if (!mDescription.equals(source.mDescription)) return false;
        if (!mUrl.equals(source.mUrl)) return false;
        if (!mCategory.equals(source.mCategory)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(mUrlsToLogos, source.mUrlsToLogos)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(mSortBysAvailable, source.mSortBysAvailable);

    }

    @Override
    public int hashCode() {
        int result = mId.hashCode();
        result = 31 * result + mName.hashCode();
        result = 31 * result + mDescription.hashCode();
        result = 31 * result + mUrl.hashCode();
        result = 31 * result + mCategory.hashCode();
        result = 31 * result + Arrays.hashCode(mUrlsToLogos);
        result = 31 * result + Arrays.hashCode(mSortBysAvailable);
        return result;
    }
}
