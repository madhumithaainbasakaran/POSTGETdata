package com.example.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("/api/users")
    Call<User> getUserData(@Field("name")String name,@Field("job") String job);

    @GET("api/users?page=1")
    Call<Model> getAllData();
}