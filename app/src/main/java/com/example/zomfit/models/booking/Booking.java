package com.example.zomfit.models.booking;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Booking {

    @SerializedName("userId")
    public String userId;

    @SerializedName("time")
    public String time;

    @SerializedName("status")
    public String status;

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
