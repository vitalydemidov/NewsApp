package ru.vitalydemidov.newsapp.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.vitalydemidov.newsapp.base.BaseResponse;

/**
 * Created by vitalydemidov on 23/03/2017.
 */

public class SourcesResponse extends BaseResponse {

    @SerializedName("sources")
    public List<Source> sources;

}
