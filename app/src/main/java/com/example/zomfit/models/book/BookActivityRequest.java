package com.example.zomfit.models.book;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class BookActivityRequest {

    @SerializedName("userId")
    public String userId;

    @SerializedName("time")
    public String time;

    @SerializedName("centerName")
    public String centerName;

    @SerializedName("centerImageUrl")
    public String centerImageUrl;

    @SerializedName("cityName")
    public String cityName;

    @SerializedName("date")
    public String date;

    @SerializedName("activityName")
    public String activityName;

    @SerializedName("activityIconUrl")
    public String activityIconUrl;
}
