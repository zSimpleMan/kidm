package com.example.myapplication;

import android.database.Observable;

import com.example.myapplication.JsonData.LocationStatus;
import com.example.myapplication.JsonData.LoginStatus;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {
    @POST("api/auth")
    @FormUrlEncoded
    Call<LoginStatus> Login(@Field("code") String code);

    @GET("api/users/getlocation/{code}")
    Call<LocationStatus> GetLocation(@Path("code") String code);
}