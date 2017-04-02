package ru.vitalydemidov.newsapp.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vitalydemidov on 03/04/2017.
 */

public class ArticlesResponse {

    @SerializedName("source")
    public String source;


    @SerializedName("sortBy")
    public String sortBy;


    @SerializedName("articles")
    public List<Article> articles;

}
