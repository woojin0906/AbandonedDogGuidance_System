package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 비밀번호 재설정 화면
*/
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        editText = (EditText) findViewById(R.id.editTextUserEmail);


        // btn_sendEmail -> 이메일 보내기 클릭리스너
        Button btn_sendEmail = findViewById(R.id.btn_sendEmail);
        btn_sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        String strEmail = editText.getText().toString();

                    if(strEmail.equals("")) {
                        Toast.makeText(PasswordActivity.this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        //비밀번호 재설정 이메일 보내기
                       String emailAddress = editText.getText().toString().trim();

                        mFirebaseAuth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(PasswordActivity.this, LoginActivity.class);
                                    Toast.makeText(PasswordActivity.this, "이메일을 보냈습니다.", Toast.LENGTH_LONG).show();
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(PasswordActivity.this, "이메일을 확인해주세요.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                }
            }
        });

    }
}