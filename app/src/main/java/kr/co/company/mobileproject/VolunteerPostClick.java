package kr.co.company.mobileproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class VolunteerPostClick extends AppCompatActivity {

    private TextView tv_title, tv_company, tv_mName, tv_phone, tv_hAddr, tv_name, tv_date, tv_vDate, tv_place, tv_context;
    private ImageView tv_img;
    private String img, title, company, mName, phone, hAddr, name, date, vDate, place, context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_post);

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

        Intent intent = getIntent();
        title = intent.getExtras().getString("title");
        company = intent.getExtras().getString("companyName");
        mName = intent.getExtras().getString("mainName");
        phone = intent.getExtras().getString("phone");
        hAddr = intent.getExtras().getString("httpAddress");
        name = intent.getExtras().getString("name");
        date = intent.getExtras().getString("date");
        vDate = intent.getExtras().getString("volunteerDate");;
        place = intent.getExtras().getString("place");
        context = intent.getExtras().getString("context");
        img = intent.getExtras().getString("imageUrl");

        tv_title.setText(title);
        tv_company.setText(company);
        tv_mName.setText(mName);
        tv_phone.setText(phone);
        tv_hAddr.setText(hAddr);
        tv_name.setText(name);
        tv_date.setText(date);
        tv_vDate.setText(vDate);
        tv_place.setText(place);
        tv_context.setText(context);
        Glide.with(tv_img)
                .load(img)
                .into(tv_img);

        // login_joinButton -> 회원가입 버튼 클릭리스너 -> AgreeActivity로 이동
        Button btnComment = findViewById(R.id.btnComment);
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 화면으로 이동
                Intent intent = new Intent(VolunteerPostClick.this, VolunteerCommentActivity.class);
                intent.putExtra("title", title);
                startActivity(intent);
                finish();
            }
        });
    }
}