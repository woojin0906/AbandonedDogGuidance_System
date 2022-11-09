package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 후원요청 게시글 화면
*/
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SponsorPostActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("Sponsor");         // 실시간 데이터베이스
    private TextView tv_title, tv_company, tv_mName, tv_phone, tv_place, tv_hAddr, tv_name, tv_context;
    private ImageView tv_img;
    private String title, company, mName, phone, place , hAddr, name, context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_post);

       // ArrayList<SponsorInfo> sponsorInfo = new ArrayList<>();

        tv_name = (TextView) findViewById(R.id.sponsor_post_name);
        tv_title = (TextView) findViewById(R.id.sponsor_post_title);
        tv_company = (TextView) findViewById(R.id.sponsor_post_company);
        tv_mName = (TextView) findViewById(R.id.sponsor_post_mName);
        tv_phone = (TextView) findViewById(R.id.sponsor_post_phone);
        tv_hAddr = (TextView) findViewById(R.id.sponsor_post_hAddr);
        tv_context = (TextView) findViewById(R.id.sponsor_post_context);
        tv_place = (TextView) findViewById(R.id.sponsor_post_place);
        tv_img = findViewById(R.id.sponsor_post_image);

        Intent receiveIntent = getIntent();
        final String name = receiveIntent.getStringExtra("strName");
        final String title = receiveIntent.getStringExtra("strTitle");
        final String company = receiveIntent.getStringExtra("strcompany");
        final String mainName = receiveIntent.getStringExtra("strMname");
        final String phone = receiveIntent.getStringExtra("strPhone");
        final String httpAddr = receiveIntent.getStringExtra("strHAddr");
        final String con = receiveIntent.getStringExtra("strCon");
        final String place = receiveIntent.getStringExtra("strPlace");
        final String image = receiveIntent.getStringExtra("image");

        tv_name.setText(name);
        tv_title.setText(title);
        tv_company.setText(company);
        tv_mName.setText(mainName);
        tv_phone.setText(phone);
        tv_hAddr.setText(httpAddr);
        tv_context.setText(con);
        tv_place.setText(place);
        Glide.with(tv_img).load(image).into(tv_img);

        // login_joinButton -> 회원가입 버튼 클릭리스너 -> AgreeActivity로 이동
        Button btnComment = findViewById(R.id.btnComment);
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 화면으로 이동
                Intent intent = new Intent(SponsorPostActivity.this, SponsorCommentActivity.class);
                intent.putExtra("title", title);
                startActivity(intent);
                finish();
            }
        });
    }
}