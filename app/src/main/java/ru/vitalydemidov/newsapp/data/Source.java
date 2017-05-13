package ru.vitalydemidov.newsapp.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Source implements Parcelable {

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


    @Expose(serialize = false, deserialize = false)
    @SerializedName("urlsToLogos")
    private Map<String, String> mUrlsToLogos;


    @SerializedName("sortBysAvailable")
    private List<String> mSortBysAvailable;


    public Source(@NonNull String name) {
        mId = "";
        mName = name;
        mDescription = "";
        mUrl = "";
        mCategory = "";
        mSortBysAvailable = new ArrayList<>();
    }


    public String getId() {
        return mId;
    }


    public String getName() {
        return mName;
    }


    public List<String> getSortBysAvailable() {
        return mSortBysAvailable;
    }


    //region Parcelable
    public static final Creator<Source> CREATOR = new Creator<Source>() {
        @Override
        public Source createFromParcel(@NonNull Parcel in) {
            return new Source(in);
        }

        @Override
        public Source[] newArray(int size) {
            return new Source[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeString(mUrl);
        dest.writeString(mCategory);
        dest.writeStringList(mSortBysAvailable);
    }


    private Source(@NonNull Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mDescription = in.readString();
        mUrl = in.readString();
        mCategory = in.readString();
        mSortBysAvailable = in.createStringArrayList();
    }
    //endregion Parcelable

}
