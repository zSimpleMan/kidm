package com.example.myservice;

import android.database.Observable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {
    @POST("/api/users/update-location/{code}")
    @FormUrlEncoded
    Call<LocationStatus> UpdateLocation(@Path("code") String code, @Field("Latitute") double latitute,
                                        @Field("Longitute") double longitute);
    @POST("api/auth")
    @FormUrlEncoded
    Call<LoginStatus> Login(@Field("code") String code);

}