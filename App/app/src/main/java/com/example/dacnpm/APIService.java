package com.example.dacnpm;

import android.database.Observable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    @POST("api/auth")
    @FormUrlEncoded
    Call<loginStatus> loginUser(@Field("email") String email,
                                 @Field("password") String password);

    @POST("api/users")
    @FormUrlEncoded
    Call<registerStatus> regisUser(@Field("email") String email,
                                 @Field("password") String password,
                                   @Field("is_parent") int is_parent);
}
