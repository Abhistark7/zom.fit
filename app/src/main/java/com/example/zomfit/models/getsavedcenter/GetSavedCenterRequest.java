package com.example.zomfit.models.getsavedcenter;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class GetSavedCenterRequest {

    @SerializedName("userId")
    public String userId;
}
