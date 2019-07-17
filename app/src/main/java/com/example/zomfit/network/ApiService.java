package com.example.zomfit.network;

import com.example.zomfit.models.book.BaseResponse;
import com.example.zomfit.models.Center;
import com.example.zomfit.models.City;
import com.example.zomfit.models.book.BookActivityRequest;
import com.example.zomfit.models.booking.MyBookingRequest;
import com.example.zomfit.models.booking.MyBookingResponse;
import com.example.zomfit.models.getactivities.GetActivitiesRequest;
import com.example.zomfit.models.getactivities.GetActivitiesResponse;
import com.example.zomfit.models.getcenters.GetCentersRequest;
import com.example.zomfit.models.getcenters.GetCentersResponse;
import com.example.zomfit.models.getlikedactivity.GetLikedActivityRequest;
import com.example.zomfit.models.getlikedactivity.GetLikedActivityResponse;
import com.example.zomfit.models.getsavedcenter.GetSavedCenterRequest;
import com.example.zomfit.models.getsavedcenter.GetSavedCenterResponse;
import com.example.zomfit.models.like.LikeActivityRequest;
import com.example.zomfit.models.login.LoginRequest;
import com.example.zomfit.models.login.LoginResponse;
import com.example.zomfit.models.save.SaveCenterRequest;

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

    @POST("/getUpcomingBooking")
    Call<MyBookingResponse> getUpcomingBooking(@Body MyBookingRequest request);

    @POST("/getCompletedBooking")
    Call<MyBookingResponse> getCompletedBooking(@Body MyBookingRequest request);

    @POST("/likeActivity")
    Call<BaseResponse> likeActivity(@Body LikeActivityRequest request);

    @POST("/dislikeActivity")
    Call<BaseResponse> unlikeActivity(@Body LikeActivityRequest request);

    @POST("/saveCenter")
    Call<BaseResponse> saveCenter(@Body SaveCenterRequest request);

    @POST("/unsaveCenter")
    Call<BaseResponse> unSaveCenter(@Body SaveCenterRequest request);

    @POST("/getSavedCenter")
    Call<GetSavedCenterResponse> getSavedCenter(@Body GetSavedCenterRequest request);

    @POST("/getLikedActivities")
    Call<GetLikedActivityResponse> getLikedActivity(@Body GetLikedActivityRequest request);
}
