package ru.vitalydemidov.newsapp.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.vitalydemidov.newsapp.base.BaseResponse;

public class SourcesResponse extends BaseResponse {

    @SerializedName("sources")
    public List<Source> sources;

}
