package com.example.zomfit.models.activity;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Activity {

    @SerializedName("activityId")
    public String activityId;

    @SerializedName("name")
    public String name;

    @SerializedName("cost")
    public String cost;

    @SerializedName("iconUrl")
    public String iconUrl;

    @SerializedName("timinglist")
    public List<Timing> timingList;

    @SerializedName("rating")
    public String rating;
}
