package com.example.platemate;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PlaceApiService {
    @POST("/your-endpoint") // Replace "/your-endpoint" with the actual API endpoint
    Call<PlaceResponse> getNearbyPlaces(@Body LocationRequest locationRequest);
}
