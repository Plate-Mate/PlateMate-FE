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
        Button alonebutton = findViewById(R.id.buttonAlone);
        alonebutton.setText("혼자\n먹어요!");

        Button togetherbutton = findViewById(R.id.buttonTogether);
        togetherbutton.setText("둘이서\n먹어요!");




    }
}