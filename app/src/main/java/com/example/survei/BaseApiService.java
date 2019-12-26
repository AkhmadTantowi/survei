package com.example.survei;

import com.example.survei.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

interface BaseApiService {
    @FormUrlEncoded
    @POST("api/login")
    Call<BaseResponse<User>> loginRequest(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/register")
    Call<BaseResponse<User>> register(@Field("name") String name, @Field("email") String email, @Field("password") String password);

}

