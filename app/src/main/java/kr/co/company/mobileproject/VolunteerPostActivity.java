package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 자원봉사 게시글 화면 (글 작성 후 보이는 게시글)
*/
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VolunteerPostActivity extends AppCompatActivity {

    private FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("Volunteer");          // 실시간 데이터베이스
    private TextView tv_title, tv_company, tv_mName, tv_phone, tv_hAddr, tv_name, tv_date, tv_vDate, tv_place, tv_context;
    private ImageView tv_img;
    private String title, company, mainName, phone, httpAddr, name, date, vDate, place, con, image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_post);

        ArrayList<VolunteerInfo> volunteerList = new ArrayList<>();

        tv_title = (TextView) findViewById(R.id.volunteer_post_title);
        tv_company = (TextView) findViewById(R.id.volunteer_post_company);
        tv_mName = (TextView) findViewById(R.id.volunteer_post_mainName);
        tv_phone = (TextView) findViewById(R.id.volunteer_post_phone);
        tv_hAddr = (TextView) findViewById(R.id.volunteer_post_httpAddr);
        tv_name = (TextView) findViewById(R.id.volunteer_post_name);
        tv_date = (TextView) findViewById(R.id.volunteer_post_date);
        tv_vDate = (TextView) findViewById(R.id.volunteer_post_vdate);
        tv_place = (TextView) findViewById(R.id.volunteer_post_place);
        tv_context = (TextView) findViewById(R.id.volunteer_post_context);
        tv_img = findViewById(R.id.volunteer_post_image);

        Intent receiveIntent = getIntent();
        name = receiveIntent.getStringExtra("strName");
        title = receiveIntent.getStringExtra("strTitle");
        company = receiveIntent.getStringExtra("strcompany");
        mainName = receiveIntent.getStringExtra("strMname");
        place = receiveIntent.getStringExtra("strPlace");
        phone = receiveIntent.getStringExtra("strPhone");
        date = receiveIntent.getStringExtra("strDate");
        vDate = receiveIntent.getStringExtra("strVDate");
        httpAddr = receiveIntent.getStringExtra("strHAddr");
        con = receiveIntent.getStringExtra("strCon");
        image = receiveIntent.getStringExtra("image");

        tv_name.setText(name);
        tv_title.setText(title);
        tv_company.setText(company);
        tv_mName.setText(mainName);
        tv_place.setText(place);
        tv_phone.setText(phone);
        tv_date.setText(date);
        tv_vDate.setText(vDate);
        tv_hAddr.setText(httpAddr);
        tv_context.setText(con);
        Glide.with(tv_img).load(image).into(tv_img);

        // login_joinButton -> 회원가입 버튼 클릭리스너 -> AgreeActivity로 이동
        Button btnComment = findViewById(R.id.btnComment);
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 화면으로 이동
                Intent intent = new Intent(VolunteerPostActivity.this, VolunteerCommentActivity.class);
                intent.putExtra("title", title);
                startActivity(intent);
                finish();
            }
        });
    }
}