package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 마이페이지 화면
*/
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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

public class MyPageActivity extends AppCompatActivity {

    private FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("UserAccount");     // 실시간 데이터베이스
    private FirebaseAuth mFirebaseAuth;             // 파이어베이스 인증
    private ImageView img;
    private String UserId, name;
    private TextView tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        mFirebaseAuth = FirebaseAuth.getInstance();

        ArrayList<UserAccount> userAccount = new ArrayList<>();

        img = findViewById(R.id.mypage_imageView);
        tv_name = (TextView) findViewById(R.id.mypage_name);

        UserId = mFirebaseUser.getUid();

        database = FirebaseDatabase.getInstance();
        
        // UserAccount -> UserAccountInfo -> UserId -> 회원정보 중 이름과 사진 가져오기
        mDatabaseRef.child("UserAccountInfo").child(UserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userAccount.clear();

                    UserAccount userAccount = dataSnapshot.getValue(UserAccount.class);

                    name = userAccount.getName();

                    tv_name.setText(name);

                    Glide.with(img)
                            .load(userAccount.getImgUrl())
                            .into(img);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MypageActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        // mypage_writer -> 나의 글 보기 클릭리스너 -> InfoActivity로 이동
        TextView mypage_writer = findViewById(R.id.mypage_writer);
        mypage_writer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 내 정보 관리 화면으로 이동
                Intent intent = new Intent(MyPageActivity.this, MyWriterActivity.class);
                startActivity(intent);
            }
        });

        // mypage_logout -> 로그아웃 클릭리스너 -> LoginActivity로 이동
        TextView mypage_logout = findViewById(R.id.mypage_logout);
        mypage_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그아웃 하기
                mFirebaseAuth.signOut();
                //로그아웃 후 로그인 화면으로 변경
                finishAffinity();
                Intent intent = new Intent(MyPageActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // mypage_exit -> 탈퇴하기 클릭리스너 -> LoginActivity로 이동 혹은 Toast 메시지 기능 제공
        TextView mypage_exit = findViewById(R.id.mypage_exit);
        mypage_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 탈퇴 확인을 위한 AlertDialog 메시지 기능 제공
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MyPageActivity.this);
                alertDialogBuilder.setMessage("정말 탈퇴하시겠습니까?");
                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 리얼타임데이터베이스 지우기
                        mDatabaseRef = database.getReference("UserAccount").child("UserAccountInfo").child(UserId);
                        mDatabaseRef.removeValue();

                        // firebase 계정 지우기
                        mFirebaseAuth.getCurrentUser().delete();

                        finishAffinity();

                    }
                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MyPageActivity.this, "탈퇴되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        // mypage_info -> 내 정보 관리 클릭리스너 -> InfoActivity로 이동
        TextView mypage_info = findViewById(R.id.mypage_info);
        mypage_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 내 정보 관리 화면으로 이동
                Intent intent = new Intent(MyPageActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });

        // mypage_company -> 고객센터 클릭리스너 -> Toast 메시지 기능 제공
        TextView mypage_company = findViewById(R.id.mypage_company);
        mypage_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MyPageActivity.this);
                alertDialogBuilder.setMessage("고객센터 전화번호는 (02-321-569)입니다. 고객센터 운영 시간은 (9:00~18:00)입니다.");

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                // Toast.makeText(MyPageActivity.this, "고객센터 전화번호는 (02-321-569)입니다. 고객센터 운영 시간은 (9:00~18:00)입니다.", Toast.LENGTH_LONG).show();
            }
        });

    }
}