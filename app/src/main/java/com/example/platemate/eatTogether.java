package com.example.platemate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.platemate.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class eatTogether extends AppCompatActivity {

    private static final String BASE_URL = "YOUR_BASE_URL";
    private static final String TAG = "eatTogether";
    private TextView textViewPlateCode;
    private String userName; // 사용자명
    private String gpsInfo; // GPS 정보

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat_together);

        // 사용자명과 GPS 정보를 intent에서 가져옴
        userName = getIntent().getStringExtra("userName");
        gpsInfo = getIntent().getStringExtra("gpsInfo");

        textViewPlateCode = findViewById(R.id.textViewPlateCode);

        Button buttonRequestVerificationCode = findViewById(R.id.buttonRequestVerificationCode);
        buttonRequestVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Plate Code 받기 버튼을 클릭했을 때 서버에 요청을 보냄
                requestPlateCode();
            }
        });
    }

    private void requestPlateCode() {
        ApiService apiService = RetrofitClient.getClient(BASE_URL).create(ApiService.class);
        Call<String> call = apiService.requestPlateCode(userName, gpsInfo);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String plateCode = response.body();
                    textViewPlateCode.setText(plateCode);
                } else {
                    Log.e(TAG, "Response unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "Request failed", t);
            }
        });
    }
}
