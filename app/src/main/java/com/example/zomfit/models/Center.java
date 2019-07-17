package com.example.zomfit.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Center {

    @SerializedName("centerId")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("cityName")
    public String cityName;

    @SerializedName("activityIdList")
    public List<String> activityIdList;

    @SerializedName("imageUrl")
    public String imageUrl;

    @SerializedName("rating")
    public String rating;

    @SerializedName("likedUserIds")
    public List<String> likedUserids;

}
