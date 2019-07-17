package com.example.zomfit.models.getlikedactivity;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class GetLikedActivityRequest {

    @SerializedName("userId")
    public String userId;

}
