package com.example.platemate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ChoosePeople extends AppCompatActivity {

    private String userName; // 사용자명
    private String gpsInfo; // GPS 정보

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_people);

        // MainActivity에서 전달받은 사용자명과 GPS 정보를 가져옴
        userName = getIntent().getStringExtra("userName");
        gpsInfo = getIntent().getStringExtra("gpsInfo");

        // 로그를 통해 사용자명과 GPS 정보를 출력
        Log.e("ChoosePeople", "UserName: " + userName);
        Log.e("ChoosePeople", "GPS Info: " + gpsInfo);

        Button buttonTogether = findViewById(R.id.buttonTogether);
        buttonTogether.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // '둘이 먹어요' 버튼을 클릭했을 때의 동작 설정
                // MainActivity에서 전달받은 사용자명과 GPS 정보를 함께 intent에 저장하여 eatTogether 액티비티로 전달
                Intent intent = new Intent(ChoosePeople.this, eatTogether.class);
                intent.putExtra("userName", userName); // 사용자명 추가
                intent.putExtra("gpsInfo", gpsInfo); // GPS 정보 추가
                startActivity(intent);
            }
        });
    }
}
