package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 시작 로딩 화면
*/
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000); // 애플리케이션 실행 시 2초 동안 delay
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}