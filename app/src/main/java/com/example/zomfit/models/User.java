package com.example.zomfit.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class User {

    @SerializedName("userId")
    public String userId;

    @SerializedName("name")
    public String name;

    @SerializedName("address")
    public List<String> addressList;

    @SerializedName("email")
    public String email;

    @SerializedName("password")
    public String password;

    @SerializedName("savedAddressList")
    public List<String> savedAddressList;

    @SerializedName("savedCenterIdList")
    public List<String> savedCenterIdList;

    @SerializedName("likedActivitiesList")
    public List<String> likedActivitiesList;
}
