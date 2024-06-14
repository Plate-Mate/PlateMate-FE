package com.example.platemate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ChoosePeople extends AppCompatActivity {

    private String userName;
    private String gpsInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_people);

        Button buttonAlone = findViewById(R.id.buttonAlone);
        buttonAlone.setText("혼자\n먹어요!");
        buttonAlone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoosePeople.this, PlaceList.class);
                startActivity(intent);
            }
        });

        Button togetherbutton = findViewById(R.id.buttonTogether);
        togetherbutton.setText("둘이서\n먹어요!");

        userName = getIntent().getStringExtra("userName");
        gpsInfo = getIntent().getStringExtra("gpsInfo");

        Log.d("ChoosePeople", "UserName: " + userName);
        Log.d("ChoosePeople", "GPS Info: " + gpsInfo);

        Button buttonTogether = findViewById(R.id.buttonTogether);
        buttonTogether.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoosePeople.this, eatTogether.class);
                intent.putExtra("userName", userName);
                intent.putExtra("gpsInfo", gpsInfo);
                startActivity(intent);
            }
        });
    }
}
