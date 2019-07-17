package com.example.zomfit.models.booking;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class MyBookingResponse {

    @SerializedName("bookingList")
    public List<Booking> bookingList;
}
