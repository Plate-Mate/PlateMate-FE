package com.example.platemate;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/upload")
    Call<Void> uploadUser(@Body User user);
}