package com.example.zomfit.models.getlikedactivity;

import com.example.zomfit.models.activity.Activity;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class GetLikedActivityResponse {

    @SerializedName("likedActivityList")
    public List<Activity> activityList;
}
