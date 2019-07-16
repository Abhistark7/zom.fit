package com.example.zomfit.models.getactivities;

import com.example.zomfit.models.activity.Activity;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class GetActivitiesResponse {

    @SerializedName("activityList")
    public List<Activity> activityList;
}
