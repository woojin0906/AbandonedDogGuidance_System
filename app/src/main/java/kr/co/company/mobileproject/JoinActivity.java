package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 회원가입 화면
*/
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class JoinActivity extends AppCompatActivity {
    // 이미지 저장 추가
    private ImageView imageView;
    private Uri imageuri;

    private FirebaseAuth mFirebaseAuth;                         // 파이어베이스 인증
    private DatabaseReference mDatabaseRef;                     // 실시간 데이터베이스
    private EditText mEtId, mEtPwd,mEtPwdCf, mEtName, mEtPh;    // 회원가입 입력필드
    private Button mBtnJoin;                                    // 회원가입 버튼
    private String image, strId, strPw, strPwCf, strName, strPh, UserId;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("UserAccount");

        mEtId = findViewById(R.id.join_id);
        mEtPwd = findViewById(R.id.join_password);
        mEtPwdCf = findViewById(R.id.join_passwordConfirm);
        mEtName = findViewById(R.id.join_name);
        mEtPh = findViewById(R.id.join_phone);
        mBtnJoin = findViewById(R.id.join_joinButton);

        // join_imageBtn -> 이미지 선택 버튼 클릭 리스너
        Button join_imageBtn = findViewById(R.id.join_imageBtn);
        imageView = findViewById(R.id.join_imageView);

        join_imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityResult.launch(intent);

            }
        });

        // mBtnJoin -> 회원가입 버튼 클릭 리스너
        mBtnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 처리 시작
                // 회원가입 버튼을 눌렀을 때 실행됨
                strId = mEtId.getText().toString();
                strPw = mEtPwd.getText().toString();
                strPwCf = mEtPwdCf.getText().toString();
                strName = mEtName.getText().toString();
                strPh = mEtPh.getText().toString();

                // 입력 필드가 공백일 경우 dialog 메시지 기능 제공
                if(strId.equals("") || strPw.equals("") ||
                        strPwCf.equals("") || strName.equals("") || strPh.equals("")) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(JoinActivity.this);
                    dialog.setIcon(R.mipmap.ic_launcher);
                    dialog.setTitle("알림");
                    dialog.setMessage("모두 입력하시오.");
                    dialog.setNegativeButton("확인", null);
                    dialog.show();
                } else {
                    // 비밀번호와 비밀번호 확인이 동일해야하며, 6자리 이상으로 작성해야하는 조건
                    // 핸드폰 번호는 10자리 혹은 11자리 조건
                    // 이미지는 널이면 안됨(무조건 한 번은 선택해야하는 조건)
                    if(strPw.equals(strPwCf)) {
                        if(strPw.length() >= 6){
                            if(strPh.length() >= 10 && strPh.length() <= 11) {
                                if(imageuri != null) {
                                    uploadToFirebase(imageuri);

                                } else {
                                    Toast.makeText(JoinActivity.this, "사진을 선택해주세요.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "전화번호는 10자리 혹은 11자리입니다.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "비밀번호는 6자리 이상으로 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "비빌번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // 이미지 StorageReference에 업로드
    private void uploadToFirebase(Uri uri) {
        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));

        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // 성공시
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        //이미지 모델에 담기
                        Model model = new Model(uri.toString());
                        image = uri.toString();

                        // Firebase Auth 진행
                        mFirebaseAuth.createUserWithEmailAndPassword(strId, strPw).addOnCompleteListener(JoinActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // 객체에 회원가입 된 유저를 가져오는 것
                                    // 파이어베이스에서 연동된 메소드는 아이디와 비밀번호를 입력 안하면 회원가입이 안됨
                                    FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

                                    UserId = firebaseUser.getUid();
                                    strId = firebaseUser.getEmail();

                                    addinfo(strId, UserId, strPw, strPwCf, strName, strPh, image);

                                    Toast.makeText(JoinActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                                   // Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                                   // startActivity(intent);
                                    finish();
                                } else {

                                    Toast.makeText(JoinActivity.this, "이메일 형식이 아니거나, 이미 가입된 아이디입니다.", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                        //키로 아이디 생성
                        String modelId = mDatabaseRef.push().getKey();
                        mDatabaseRef = FirebaseDatabase.getInstance().getReference("UserAccount");
                        //데이터 넣기
                        mDatabaseRef.child("UserAccountImg").child(modelId).setValue(model);

                        //Toast.makeText(JoinActivity.this, "업로드 성공", Toast.LENGTH_SHORT).show();


                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                // 프로그래스바
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // 실패시
                Toast.makeText(JoinActivity.this, "업로드 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    // 회원가입 firebase에 저장
    // UserAccount -> UserAccountInfo -> UserId
    public void addinfo(String strId, String UserId, String strPw, String strPwCf, String strName, String strPh, String image) {
        UserAccount info = new UserAccount(strId, UserId, strPw, strPwCf, strName, strPh, image);
        // setValue : database에 insert (삽입) 행위
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