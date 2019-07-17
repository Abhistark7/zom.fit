package com.example.zomfit.models.like;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class LikeActivityRequest {

    @SerializedName("activityId")
    public String activityId;

    @SerializedName("userId")
    public String userId;
}
