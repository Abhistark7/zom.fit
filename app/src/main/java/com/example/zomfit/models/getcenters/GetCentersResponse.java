package com.example.zomfit.models.getcenters;

import com.example.zomfit.models.Center;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class GetCentersResponse {

    @SerializedName("centerList")
    public List<Center> centerList;
}
