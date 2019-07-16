package com.example.zomfit.models.book;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class BaseResponse {

    @SerializedName("status")
    public boolean status;

    @SerializedName("message")
    public String message;
}
