package com.example.zomfit.models.save;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class SaveCenterRequest {

    @SerializedName("centerId")
    public String centerId;

    @SerializedName("userId")
    public String userId;
}
