package com.example.zomfit.network;

import com.example.zomfit.models.book.BaseResponse;
import com.example.zomfit.models.Center;
import com.example.zomfit.models.City;
import com.example.zomfit.models.book.BookActivityRequest;
import com.example.zomfit.models.getactivities.GetActivitiesRequest;
import com.example.zomfit.models.getactivities.GetActivitiesResponse;
import com.example.zomfit.models.getcenters.GetCentersRequest;
import com.example.zomfit.models.getcenters.GetCentersResponse;
import com.example.zomfit.models.login.LoginRequest;
import com.example.zomfit.models.login.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("/getAllCities")
    Call<List<City>> getAllCities();

    @GET("/getAllCenters")
    Call<List<Center>> getAllCenters();

    @POST("/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("/getCentersById")
    Call<GetCentersResponse> getCenterByCity(@Body GetCentersRequest getCentersRequest);

    @POST("/getActivitiesById")
    Call<GetActivitiesResponse> getActivityById(@Body GetActivitiesRequest getActivitiesRequest);

    @POST("/bookActivity")
    Call<BaseResponse> bookActivity(@Body BookActivityRequest request);
}
