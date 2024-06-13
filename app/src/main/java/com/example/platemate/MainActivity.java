package com.example.platemate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUserName; // 사용자명을 입력받을 EditText

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 사용자명을 입력받는 EditText 초기화
        editTextUserName = findViewById(R.id.nicknameText);

        Button locationButton = findViewById(R.id.buttonFindf);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용자가 입력한 사용자명을 가져옴
                String userName = editTextUserName.getText().toString().trim();

                // intent에 사용자명을 저장하여 LoadLocation 액티비티로 전달
                Intent intent = new Intent(MainActivity.this, LoadLocation.class);
                intent.putExtra("userName", userName); // 사용자명을 intent에 추가
                startActivity(intent);
            }
        });
    }
}