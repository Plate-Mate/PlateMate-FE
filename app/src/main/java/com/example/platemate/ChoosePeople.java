package com.example.platemate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ChoosePeople extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_people);

        // Button click listener for "혼자 먹어요!" button
        Button buttonAlone = findViewById(R.id.buttonAlone);
        buttonAlone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoosePeople.this, PlaceList.class);
                startActivity(intent);
            }
        });
    }
}
