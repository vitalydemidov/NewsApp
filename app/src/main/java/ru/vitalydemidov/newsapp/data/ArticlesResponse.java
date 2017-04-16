package ru.vitalydemidov.newsapp.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticlesResponse {

    @SerializedName("source")
    public String source;


    @SerializedName("sortBy")
    public String sortBy;


    @SerializedName("articles")
    public List<Article> articles;

}
