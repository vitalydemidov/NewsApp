package ru.vitalydemidov.newsapp.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Article implements Parcelable {

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


    public Article(@NonNull String title) {
        mAuthor = "";
        mTitle = title;
        mDescription = "";
        mUrl = "";
        mUrlToImage = "";
        mPublishedAt = "";
    }


    public String getUrlToImage() {
        return mUrlToImage;
    }


    public String getTitle() {
        return mTitle;
    }


    public String getUrl() {
        return mUrl;
    }


    //region Parcelable
    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(@NonNull Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAuthor);
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeString(mUrl);
        dest.writeString(mUrlToImage);
        dest.writeString(mPublishedAt);
    }


    private Article(@NonNull Parcel in) {
        mAuthor = in.readString();
        mTitle = in.readString();
        mDescription = in.readString();
        mUrl = in.readString();
        mUrlToImage = in.readString();
        mPublishedAt = in.readString();
    }
    //endregion Parcelable

}
