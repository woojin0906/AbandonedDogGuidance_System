package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 메인 화면
*/
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;             // 파이어베이스 인증
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();

        // main_mypage -> 마이페이지 버튼 클릭리스너 -> MyPageActivity로 이동
        Button main_mypage = findViewById(R.id.main_mypage);
        main_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //마이페이지 화면으로 변경
              //  finish();
                Intent intent = new Intent(MainActivity.this, MyPageActivity.class);
                startActivity(intent);
            }
        });

        // main_logout -> 로그아웃 버튼 클릭리스너 -> LoginActivity로 이동
        Button main_logout = findViewById(R.id.main_logout);
        main_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //로그아웃 하기
                mFirebaseAuth.signOut();
                 //로그아웃 후 로그인 화면으로 변경
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // main_btn_missing -> 실종동물찾기 버튼 클릭리스너 -> MissingAnimalActivity로 이동
        Button main_btn_missing = findViewById(R.id.main_btn_missing);
        main_btn_missing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 실종동물찾기 화면으로 변경
                Intent intent = new Intent(MainActivity.this, MissingAnimalActivity.class);
                startActivity(intent);
            }
        });

        // main_btn_pet -> 후원요청 버튼 클릭리스너 -> SponsorActivity 이동
        Button main_btn_pet = findViewById(R.id.main_btn_pet);
        main_btn_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 후원요청 화면으로 변경
                Intent intent = new Intent(MainActivity.this, SponsorActivity.class);
                startActivity(intent);
            }
        });

        // main_btn_search -> 유기견보호센터 버튼 클릭리스너 -> SearchActivity 이동
        Button main_btn_search = findViewById(R.id.main_btn_search);
        main_btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 유기견보호센터 화면으로 변경
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);

            }
        });

        // main_btn_volunteer -> 자원봉사 버튼 클릭리스너 -> VolunteerActivity 이동
        Button main_btn_volunteer = findViewById(R.id.main_btn_volunteer);
        main_btn_volunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 자원봉사 화면으로 변경
                Intent intent = new Intent(MainActivity.this, VolunteerActivity.class);
                startActivity(intent);
            }
        });


        // main_comment -> 커뮤니티 클릭리스너 -> ChatActivity 이동
        TextView main_comment = findViewById(R.id.main_comment);
        main_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 채팅 화면으로 변경
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
    }
}