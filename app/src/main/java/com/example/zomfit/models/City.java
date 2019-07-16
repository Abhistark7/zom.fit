package com.example.zomfit.models;


import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class City {

    @SerializedName("cityId")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("centerIdList")
    public ArrayList<String> centerIdList;

    @SerializedName("imageUrl")
    public String imageUrl;
}
