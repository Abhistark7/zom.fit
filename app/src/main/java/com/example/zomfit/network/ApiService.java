package com.example.zomfit.network;

import com.example.zomfit.models.City;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/getAllCities")
    Call<List<City>> getAllCities();
}
