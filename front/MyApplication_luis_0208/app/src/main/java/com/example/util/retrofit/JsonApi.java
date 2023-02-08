package com.example.util.retrofit;

import com.example.dto.Favorite;
import com.example.dto.LocationCoordinate;
import com.example.dto.TimeRoute;
import com.example.dto.request.FavoriteLocationCoordinateRequest;
import com.example.dto.request.JoinRequest;
import com.example.dto.request.LoginRequest;
import com.example.dto.response.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import java.util.List;

public interface JsonApi {

    @POST("/api/route")
    Call<ServerResponse<List<TimeRoute>>> getTimeRoute(@Body LocationCoordinate lc);

    @GET("/favorites")
    Call<ServerResponse<List<Favorite>>> getFavorites(@Header("Authorization") String token);

    @POST("/favorites")
    Call<ServerResponse<String>> addFavorite(@Header("Authorization") String token, @Body FavoriteLocationCoordinateRequest request);

    @POST("/login")
    Call<ServerResponse<String>> login(@Body LoginRequest request);

    @POST("/join")
    Call<ServerResponse<String>> join(@Body JoinRequest request);
}
