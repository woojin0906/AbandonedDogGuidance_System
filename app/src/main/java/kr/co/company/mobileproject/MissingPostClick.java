package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 실종동물 게시글 화면 (리사이클러뷰에서 게시글 클릭시)
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

public class MissingPostClick extends AppCompatActivity {

    private TextView tv_date, tv_title, tv_place, tv_money, tv_phone, tv_pet, tv_context;
    private ImageView tv_img;
    private String date, title, place , money, phone, pet, context, img;

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
        tv_img = findViewById(R.id.missing_post_image);

        Intent intent = getIntent();
        context = intent.getExtras().getString("context");
        date = intent.getExtras().getString("date");
        money = intent.getExtras().getString("money");
        pet = intent.getExtras().getString("pet");
        phone = intent.getExtras().getString("phone");
        place = intent.getExtras().getString("place");
        title = intent.getExtras().getString("title");
        img = intent.getExtras().getString("imageUrl");

        tv_context.setText(context);
        tv_date.setText(date);
        tv_money.setText(money);
        tv_pet.setText(pet);
        tv_phone.setText(phone);
        tv_title.setText(title);
        tv_place.setText(place);
        Glide.with(tv_img)
                .load(img)
                .into(tv_img);

        // btnComment -> 댓글보기 버튼 클릭리스너 -> MissingCommentActivity로 이동
        Button btnComment = findViewById(R.id.btnComment);
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 실종동물찾기 댓글 화면으로 이동
                Intent intent = new Intent(MissingPostClick.this, MissingCommentActivity.class);
                intent.putExtra("title", title);
                startActivity(intent);
                finish();
            }
        });

    }
}