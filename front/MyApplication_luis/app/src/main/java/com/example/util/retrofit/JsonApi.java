package com.example.util.retrofit;

import com.example.dto.Favorite;
import com.example.dto.LocationCoordinate;
import com.example.dto.TimeRoute;
import com.example.dto.request.FavoriteLocationCoordinateRequest;
import com.example.dto.request.JoinRequest;
import com.example.dto.request.LoginRequest;
import com.example.dto.request.TokenRefreshRequest;
import com.example.dto.response.LoginSuccessTokenResponse;
import com.example.dto.response.MyPageResponse;
import com.example.dto.response.ServerResponse;
import com.example.dto.response.TokenRefreshResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface JsonApi {

    @POST("/api/route")
    Call<ServerResponse<List<TimeRoute>>> getTimeRoute(@Body LocationCoordinate lc);
    @GET("/favorites")
    Call<ServerResponse<List<Favorite>>> getFavorites(@Header("Authorization") String accessToken);
    @POST("/favorites")
    Call<ServerResponse<String>> addFavorite(@Header("Authorization") String accessToken, @Body FavoriteLocationCoordinateRequest request);

    @DELETE("/favorites/{favoriteId}")
    Call<ServerResponse<String>> deleteFavorite(@Header("Authorization") String accessToken, @Path("favoriteId") String favoriteId);

    @POST("/refresh")
    Call<ServerResponse<TokenRefreshResponse>> refresh(@Body TokenRefreshRequest request);

    @GET("/my-page")
    Call<ServerResponse<MyPageResponse>> getMyPage(@Header("Authorization") String accessToken);

    @POST("/login")
    Call<ServerResponse<LoginSuccessTokenResponse>> login(@Body LoginRequest request);
    @GET("/auto-login")
    Call<ServerResponse<String>> autoLogin(@Header("Authorization") String accessToken);
    @POST("/logout")
    Call<ServerResponse<String>> logout(@Body TokenRefreshRequest request);

    @POST("/join")
    Call<ServerResponse<String>> join(@Body JoinRequest request);

}
