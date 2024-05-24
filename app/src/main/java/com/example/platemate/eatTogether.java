package com.example.platemate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class eatTogether extends AppCompatActivity {

    private TextView textViewPlateCode;
    private Set<String> generatedCodes = new HashSet<>();
    private String serverGeneratedCode = ""; // 서버에서 생성된 인증번호 저장

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat_together);

        textViewPlateCode = findViewById(R.id.textViewPlateCode);
        Button buttonRequestVerificationCode = findViewById(R.id.buttonRequestVerificationCode);
        buttonRequestVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateUniquePlateCode();
                // 서버에서 인증번호 생성 (임시로 랜덤 코드 사용)
                serverGeneratedCode = generateRandomCode("abcdefghijklmnopqrstuvwxyz0123456789");
                // 텍스트뷰에 생성된 코드 표시
                textViewPlateCode.setText(serverGeneratedCode);
            }
        });

        Button buttonVerify = findViewById(R.id.buttonVerify);
        final EditText editTextFriendVerificationCode = findViewById(R.id.editTextFriendVerificationCode);
        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredCode = editTextFriendVerificationCode.getText().toString().trim();
                verifyCode(enteredCode);
            }
        });
    }

    private void verifyCode(String enteredCode) {
        if (enteredCode.equals(serverGeneratedCode)) {
            // 매핑 완료 후 맛집 리스트뷰로 이동
            navigateToRestaurantListView();
        } else {
            // 인증번호를 다시 확인해주세요 메시지 표시
            showToast("인증번호를 다시 확인해주세요");
        }
    }

    private void navigateToRestaurantListView() {
        Intent intent = new Intent(eatTogether.this, RestaurantListViewActivity.class);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void generateUniquePlateCode() {
        // 랜덤한 코드를 생성하기 위한 문자열 pool
        String pool = "abcdefghijklmnopqrstuvwxyz0123456789";

        // 랜덤한 코드를 저장할 변수
        String plateCode;

        do {
            plateCode = generateRandomCode(pool);
        } while (generatedCodes.contains(plateCode)); // 생성된 코드가 이미 존재한다면 다시 생성

        // 생성된 코드를 저장
        generatedCodes.add(plateCode);
    }

    private String generateRandomCode(String pool) {
        StringBuilder plateCode = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            char randomChar = pool.charAt(random.nextInt(pool.length()));
            plateCode.append(randomChar);
        }
        return plateCode.toString();
    }
}
