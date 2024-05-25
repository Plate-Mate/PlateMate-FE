package com.example.platemate;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class eatTogether extends AppCompatActivity {

    private static final String TAG = "eatTogether";

    private TextView textViewPlateCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat_together);

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
        // 서버에 요청을 보내는 AsyncTask 실행
        new ApiRequestTask().execute("YOUR_API_ENDPOINT_URL");
    }

    private class ApiRequestTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String jsonResponse = null;

            try {
                // 서버에 요청을 보낼 URL을 생성
                URL url = new URL(params[0]);
                // HTTP 연결 설정
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // 서버에서 응답 데이터를 읽어옴
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                jsonResponse = buffer.toString();
            } catch (IOException e) {
                Log.e(TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }
                }
            }
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // 서버 응답을 이용하여 UI 업데이트 또는 기타 작업 수행
            if (result != null) {
                // 서버 응답을 처리하여 Plate Code를 표시하거나 다른 작업을 수행
                textViewPlateCode.setText(result);
            }
        }
    }
}
