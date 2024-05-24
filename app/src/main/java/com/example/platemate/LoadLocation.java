package com.example.platemate;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.Manifest;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadLocation extends AppCompatActivity {
    private boolean locationRetrieved = false;
    private LocationListener locationListener;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loadlocation);
        Button locationButton = findViewById(R.id.buttonLoadLocation);
        String nickName = getIntent().getStringExtra("nickName");


        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startLocationService();
                long longlatitude = Math.round(latitude);
                long longLongitude = Math.round(longitude);
                User user = new User();
                user.setNickname(nickName);
                user.setLatitude(longlatitude);
                user.setLongitude(longLongitude);
                ApiService apiService = RetrofitClient.getApiService();
                apiService.uploadUser(user).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(LoadLocation.this, "File uploaded successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoadLocation.this, "Failed to upload file", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(LoadLocation.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                Intent intent = new Intent(LoadLocation.this, ChoosePeople.class);
                startActivity(intent);

            }
        });




    }

    private void startLocationService() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null && !LoadLocation.this.locationRetrieved) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();


                    LoadLocation.this.locationRetrieved = true;

                    locationManager.removeUpdates(locationListener);
                }
            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Toast.makeText(this, "Location service started!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Location permission required!", Toast.LENGTH_SHORT).show();
        }
    }
}