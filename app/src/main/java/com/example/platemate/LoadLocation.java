package com.example.platemate;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;

public class LoadLocation extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loadlocation);
        Button locationButton = findViewById(R.id.buttonLoadLocation);

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLocationPermission();
            }
        });
    }

    // 위치 권한을 요청하는 메서드
    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // 권한이 없는 경우 권한 요청 대화상자 표시
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // 이미 권한이 있는 경우 위치 정보 가져오기 메서드 호출
            getGpsInfo();
        }
    }

    // 사용자의 권한 요청 결과를 처리하는 메서드
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 사용자가 권한을 승인한 경우 위치 정보 가져오기 메서드 호출
                getGpsInfo();
            } else {
                // 사용자가 권한을 거부한 경우 알림이나 다른 작업 수행
            }
        }
    }

    // GPS 정보를 가져오는 메서드
    @SuppressLint("MissingPermission")
    private void getGpsInfo() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                // 위도와 경도를 문자열 형태로 반환
                String gpsInfo = "Latitude: " + latitude + ", Longitude: " + longitude;

                // GPS 정보를 가져왔으므로 ChoosePeople 액티비티로 이동하여 사용자명과 GPS 정보를 전달
                moveToChoosePeople(gpsInfo);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        // 위치 업데이트 요청
        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
    }

    // ChoosePeople 액티비티로 이동하고 GPS 정보를 전달하는 메서드
    private void moveToChoosePeople(String gpsInfo) {
        String userName = getIntent().getStringExtra("userName"); // MainActivity에서 전달받은 사용자명
        Intent intent = new Intent(LoadLocation.this, ChoosePeople.class);
        intent.putExtra("userName", userName); // 사용자명 추가
        intent.putExtra("gpsInfo", gpsInfo); // GPS 정보 추가
        startActivity(intent);
    }
}
