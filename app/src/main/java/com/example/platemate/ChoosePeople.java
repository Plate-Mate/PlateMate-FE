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

        Button buttonTogether = findViewById(R.id.buttonTogether);
        buttonTogether.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // '둘이 먹어요' 버튼을 클릭했을 때의 동작 설정
                Intent intent = new Intent(ChoosePeople.this, eatTogether.class);
                startActivity(intent);
            }
        });
    }
}
