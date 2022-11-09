package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 실종동물 게시글 화면
*/
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class MissingPostActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("MissingAnimal");      // 실시간 데이터베이스
    private TextView tv_date, tv_title, tv_place, tv_money, tv_phone, tv_pet, tv_context;
    private ImageView tv_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_post);

        tv_date = (TextView) findViewById(R.id.missing_post_date);
        tv_img = findViewById(R.id.missing_post_image);
        tv_title = (TextView) findViewById(R.id.missing_post_title);
        tv_place = (TextView) findViewById(R.id.missing_post_place);
        tv_money = (TextView) findViewById(R.id.missing_post_money);
        tv_phone = (TextView) findViewById(R.id.missing_post_phone);
        tv_pet = (TextView) findViewById(R.id.missing_post_pet);
        tv_context = (TextView) findViewById(R.id.missing_post_context);

        Intent receiveIntent = getIntent();
        final String title = receiveIntent.getStringExtra("strTitle");
        final String money = receiveIntent.getStringExtra("strMoney");
        final String place = receiveIntent.getStringExtra("strPlace");
        final String phone = receiveIntent.getStringExtra("strPhone");
        final String date = receiveIntent.getStringExtra("strDate");
        final String con = receiveIntent.getStringExtra("strCon");
        final String pet = receiveIntent.getStringExtra("strPet");
        final String image = receiveIntent.getStringExtra("image");

        tv_title.setText(title);
        tv_money.setText(money);
        tv_place.setText(place);
        tv_phone.setText(phone);
        tv_date.setText(date);
        tv_context.setText(con);
        tv_pet.setText(pet);
        Glide.with(tv_img).load(image).into(tv_img);

        // login_joinButton -> 회원가입 버튼 클릭리스너 -> AgreeActivity로 이동
        Button btnComment = findViewById(R.id.btnComment);
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 화면으로 이동
                Intent intent = new Intent(MissingPostActivity.this, MissingCommentActivity.class);
                intent.putExtra("title", title);
                startActivity(intent);
                finish();
            }
        });
    }
}