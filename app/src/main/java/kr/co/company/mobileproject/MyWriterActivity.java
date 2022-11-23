package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 나의 글보기 화면
*/
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyWriterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_writer);

        // myWriter_btn_missing -> 나의 실종동물 글 보기 클릭리스너 -> myWriterMissingActivity로 이동
        TextView myWriter_btn_missing = findViewById(R.id.myWriter_btn_missing);
        myWriter_btn_missing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 내 정보 관리 화면으로 이동
                Intent intent = new Intent(MyWriterActivity.this, myWriterMissingActivity.class);
                startActivity(intent);
            }
        });

        // myWriter_btn_volunteer -> 나의 자원봉사 글 보기 클릭리스너 -> myWriterVolunActivity로 이동
        TextView myWriter_btn_volunteer = findViewById(R.id.myWriter_btn_volunteer);
        myWriter_btn_volunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 내 정보 관리 화면으로 이동
                Intent intent = new Intent(MyWriterActivity.this, myWriterVolunActivity.class);
                startActivity(intent);
            }
        });

        // myWriter_btn_pet -> 나의 후원요청 글 보기 클릭리스너 -> myWriterSponsorActivity로 이동
        TextView myWriter_btn_pet = findViewById(R.id.myWriter_btn_pet);
        myWriter_btn_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 내 정보 관리 화면으로 이동
                Intent intent = new Intent(MyWriterActivity.this, myWriterSponsorActivity.class);
                startActivity(intent);
            }
        });
    }
}