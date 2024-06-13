package com.example.platemate;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    private EditText inputNick; // 사용자명을 입력받을 EditText
    private final int REQUEST_LOCATION_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button locationButton = findViewById(R.id.buttonFindf);
        checkLocationPermission();
        inputNick = findViewById(R.id.inputNick);
        // Add TextWatcher to inputNick
        inputNick.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This part is called before the text changes
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This part is called when the text is changing
                if (charSequence.toString().isEmpty()) {
                    // If the text is empty, show the hint
                    inputNick.setHint("닉네임을 입력해주세요");
                } else {
                    // If there is text, hide the hint
                    inputNick.setHint("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // This part is called after the text has changed
            }
        });
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = inputNick.getText().toString();

                // Print the nickname to the log
                Log.d("MainActivity", "Nickname entered: " + nickname);

                Intent intent = new Intent(MainActivity.this, LoadLocation.class);
                intent.putExtra("userName", String.valueOf(nickname));
                startActivity(intent);

            }
        });


    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            return false;
        }
        return true;
    }
}