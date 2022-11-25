package com.example.myapplication.domain;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JsonApi {
    @POST("/route")
    Call<List<TimeRoute>> getTimeRoute(@Body LocationCoordinate locationCoordinate);
}
