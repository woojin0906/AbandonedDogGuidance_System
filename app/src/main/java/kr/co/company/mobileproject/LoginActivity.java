package kr.co.company.mobileproject;
// 로그인 화면
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;                         // 파이어베이스 인증
    private EditText mEtId, mEtPwd;                             // 로그인 입력필드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mEtId = findViewById(R.id.login_id);
        mEtPwd = findViewById(R.id.login_password);

        // login_loginButton -> 로그인 버튼 클릭리스너
        Button login_loginButton = findViewById(R.id.login_loginButton);
        login_loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그인 요청
                String strId = mEtId.getText().toString();
                String strPw = mEtPwd.getText().toString();
                if(strId.equals("") || strPw.equals("")) {
                    // 로그인 실패
                    Toast.makeText(LoginActivity.this, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    mFirebaseAuth.signInWithEmailAndPassword(strId, strPw).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // 로그인 성공
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                Toast.makeText(LoginActivity.this,"로그인 성공", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            } else {
                                // 로그인 실패
                                Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        // login_joinButton -> 회원가입 버튼 클릭리스너 -> AgreeActivity로 이동
        Button login_joinButton = findViewById(R.id.login_joinButton);
        login_joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 화면으로 이동
                Intent intent = new Intent(LoginActivity.this, AgreeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // login_password_change_btn -> 비밀번호 찾기 버튼 클릭리스너 -> Toast 메시지 기능 제공
        Button login_password_change_btn = findViewById(R.id.login_password_change_btn);
        login_password_change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "비밀번호를 잃어버리신 사용자는 고객센터로 전화주세요. \n (02-321-569)", Toast.LENGTH_SHORT).show();
            }
        });
    }
}