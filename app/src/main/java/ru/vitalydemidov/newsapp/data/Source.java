package ru.vitalydemidov.newsapp.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by vitalydemidov on 24/01/2017.
 */

public class Source {

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("url")
    private String mUrl;

    @SerializedName("category")
    private String mCategory;

    @SerializedName("urlsToLogos")
    private Map<String, String> mUrlsToLogos;

    @SerializedName("sortBysAvailable")
    private List<String> mSortBysAvailable;

}
