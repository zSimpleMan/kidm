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
    @POST("/api/users/kidlocation/{id}")
    @FormUrlEncoded
    Call<LocationStatus> UpdateLocation(@Path("id") int id, @Field("latitude") double latitude,
                                        @Field("longitude") double longitude);

}