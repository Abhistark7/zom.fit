package com.example.zomfit.models.getsavedcenter;

import com.example.zomfit.models.Center;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class GetSavedCenterResponse {

    @SerializedName("savedCenterList")
    public List<Center> centerList;
}
