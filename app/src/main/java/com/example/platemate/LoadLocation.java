package com.example.platemate;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LoadLocation extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private static final int LOCATION_TIMEOUT = 10000; // 10초
    private Handler handler = new Handler();
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Runnable locationTimeoutRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loadlocation);
        Button locationButton = findViewById(R.id.buttonLoadLocation);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLocationPermission();
            }
        });
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getGpsInfo();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getGpsInfo();
            } else {
                Log.e("LoadLocation", "Location permission denied");
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getGpsInfo() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    String gpsInfo = "Latitude: " + latitude + ", Longitude: " + longitude;
                    Log.d("LoadLocation", "GPS Info: " + gpsInfo);
                    moveToChoosePeople(gpsInfo);
                    handler.removeCallbacks(locationTimeoutRunnable); // 타임아웃 콜백 제거
                    locationManager.removeUpdates(locationListener); // 위치 업데이트 중지
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {}

                @Override
                public void onProviderEnabled(String provider) {}

                @Override
                public void onProviderDisabled(String provider) {}
            };

            Log.d("LoadLocation", "Requesting location update...");
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);

            // 타임아웃 설정
            locationTimeoutRunnable = new Runnable() {
                @Override
                public void run() {
                    Log.e("LoadLocation", "Location request timed out");
                    Toast.makeText(LoadLocation.this, "Location request timed out", Toast.LENGTH_SHORT).show();
                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (lastKnownLocation != null) {
                        double latitude = lastKnownLocation.getLatitude();
                        double longitude = lastKnownLocation.getLongitude();
                        String gpsInfo = "Latitude: " + latitude + ", Longitude: " + longitude;
                        moveToChoosePeople(gpsInfo);
                    } else {
                        Toast.makeText(LoadLocation.this, "Failed to get location", Toast.LENGTH_SHORT).show();
                    }
                    locationManager.removeUpdates(locationListener); // 위치 업데이트 중지
                }
            };
            handler.postDelayed(locationTimeoutRunnable, LOCATION_TIMEOUT);
        } else {
            Log.e("LoadLocation", "GPS Provider is disabled");
            Toast.makeText(this, "Please enable GPS", Toast.LENGTH_SHORT).show();
        }
    }

    private void moveToChoosePeople(String gpsInfo) {
        String userName = getIntent().getStringExtra("userName");
        Intent intent = new Intent(LoadLocation.this, ChoosePeople.class);
        intent.putExtra("userName", userName);
        intent.putExtra("gpsInfo", gpsInfo);
        Log.d("LoadLocation", "Starting ChoosePeople activity with userName: " + userName + " and gpsInfo: " + gpsInfo);
        startActivity(intent);
    }
}
