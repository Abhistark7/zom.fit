package com.example.zomfit.models.booking;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class MyBookingRequest {

    @SerializedName("userId")
    public String userId;
}
