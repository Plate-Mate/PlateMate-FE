package com.example.platemate;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("your_endpoint")
    Call<String> requestPlateCode(@Query("userName") String userName, @Query("gpsInfo") String gpsInfo);
}
