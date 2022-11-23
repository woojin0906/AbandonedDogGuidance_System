package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 회원정보수정 화면
*/
import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.IDNA;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {

    private ImageView imageView;
    private Uri imageuri;

    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();;                         // 파이어베이스 인증
    private FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("UserAccount"); // 실시간 데이터베이스

    private ImageView img; // 이미지뷰
    private String UserId, email, name, phone, strName, image, images;
    private TextView tv_email, tv_phone; // 회원정보수정 텍스트 필드
    private EditText ed_name; // 회원정보수정 입력 필드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ArrayList<UserAccount> userAccount = new ArrayList<>();

        // TextView, EditView -> findViewById() 메소드
        img = findViewById(R.id.info_imageView);
        tv_email = (TextView) findViewById(R.id.info_email);
        ed_name = (EditText) findViewById(R.id.info_name);
        tv_phone = (TextView) findViewById(R.id.info_phone);

        // 현재 사용자의 idToken 값 가져오기
        UserId = mFirebaseUser.getUid();

        // UserAccount -> UserAccountInfo -> UserId -> firebase에 저장된 회원정보 가져오기
        mDatabaseRef.child("UserAccountInfo").child(UserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userAccount.clear();
                    UserAccount userAccount = dataSnapshot.getValue(UserAccount.class);

                    email = userAccount.getId();
                    name = userAccount.getName();
                    phone = userAccount.getPhone();

                    tv_email.setText(email);
                    ed_name.setText(name);
                    tv_phone.setText(phone);

                    Glide.with(img)
                            .load(userAccount.getImgUrl())
                            .into(img);
                    /// 이미지 경로 받아오기(이미지 선택하지 않은 경우를 위해서)
                    images = userAccount.getImgUrl();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("InfoActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        // info_imageBtn -> 이미지 선택 버튼 클릭리스너
        Button info_imageBtn = findViewById(R.id.info_imageBtn);
        imageView = findViewById(R.id.info_imageView);
        info_imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityResult.launch(intent);

            }
        });

        // info_passwordBtn -> 비밀번호 변경 버튼 클릭리스너
        Button info_passwordBtn = findViewById(R.id.info_passwordBtn);
        info_passwordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        if(email.equals("")) {
                            Toast.makeText(InfoActivity.this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        } else {

                            mFirebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(InfoActivity.this, "이메일을 보냈습니다.", Toast.LENGTH_LONG).show();
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), MyPageActivity.class));
                                    } else {
                                        Toast.makeText(InfoActivity.this, "메일전송이 실패되었습니다.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
            }
        });

        // info_changeBtn -> 회원정보수정 버튼 클릭 리스너
        Button info_changeBtn = findViewById(R.id.info_changeBtn);
        info_changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strName = ed_name.getText().toString();
                email = tv_email.getText().toString();
                phone = tv_phone.getText().toString();

                // 이름이 빈칸일 경우 dialog 메시지 기능 제공
                if(strName.equals("")) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(InfoActivity.this);
                    dialog.setIcon(R.mipmap.ic_launcher);
                    dialog.setTitle("알림");
                    dialog.setMessage("모두 입력하시오.");
                    dialog.setNegativeButton("확인", null);
                    dialog.show();
                } else {
                            // 이미지를 선택한 경우 선택된 이미지로 업데이트
                            if(imageuri != null) {
                                uploadToFirebase(imageuri);
                                Toast.makeText(getApplicationContext(), "수정 완료", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(InfoActivity.this, MyPageActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // 이미지를 선택하지 않은 경우 기존의 이미지 불러오기
                                uploadTo(images);
                                Toast.makeText(getApplicationContext(), "수정 완료", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(InfoActivity.this, MyPageActivity.class);
                                startActivity(intent);
                                finish();
                            }

                }

            }
        });
    }

    // 이미지를 선택한 경우 이미지 경로 받아오기
    private void uploadToFirebase(Uri uri) {
        image = uri.toString();
        addinfo(email, UserId, strName, phone, image);
    }

    // 이미지를 선택하지 않은 경우 -> 원래의 이미지
    private void uploadTo(String images) {
        addinfo(email, UserId, strName, phone, images);
    }

    // 회원정보 업데이트
    // UserAccount -> UserAccountInfo -> UserId
    public void addinfo(String email, String IdToken, String strName, String phone, String image) {
        UserAccount info = new UserAccount(email, IdToken,strName, phone, image);

        mDatabaseRef.child("UserAccountInfo").child(UserId).setValue(info);
    }

    // 이미지 결과 처리
    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // result의 코드가 RESULT_OK이고 data가 널이 아닌 경우
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imageuri = result.getData().getData();

                        try {
                            // Bitmap으로 만들어서 imageView에 집어 넣음
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageuri);
                            imageView.setImageBitmap(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
}