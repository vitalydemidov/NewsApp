package ru.vitalydemidov.newsapp.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vitalydemidov on 02/04/2017.
 */

public class Article {

    @SerializedName("author")
    private String mAuthor;


    @SerializedName("title")
    private String mTitle;


    @SerializedName("description")
    private String mDescription;


    @SerializedName("url")
    private String mUrl;


    @SerializedName("urlToImage")
    private String mUrlToImage;


    @SerializedName("publishedAt")
    private String mPublishedAt;


    public String getTitle() {
        return mTitle;
    }

}
