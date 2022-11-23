package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 실종동물 글 작성 화면
*/
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MissingAnimalWriterActivity extends AppCompatActivity {

    // 이미지 저장 추가
    private ImageView imageView;
    // 데이터베이스 저장
    private FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mDatabaseRef;                 // 실시간 데이터베이스
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private Uri imageuri;
    Intent intent;
    private EditText EtMName, EtMTitle, EtMPlace, EtMMoney, EtMPhone, EtMDate, EtMContext, EtMPet;
    String UserId, strName, strTitle, strMoney, strPlace, strPhone, strDate, strCon, image, strPet;
    // 품종 목록(스피너) 데이터를 배열에 넣어서 준비
//    String[] items = {"말티즈", "푸들", "시바견", "골든리트리버", "기타"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_animal_writer);

        // missing_picture_btn -> 이미지 선택 버튼 클릭리스너
        Button missing_picture_btn = findViewById(R.id.missing_picture_btn);
        imageView = findViewById(R.id.missing_imageView);

        missing_picture_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityResult.launch(intent);
            }
        });

        EtMName = findViewById(R.id.missing_name);
        EtMTitle = findViewById(R.id.missing_title);
        EtMPlace = findViewById(R.id.missing_place);
        EtMMoney = findViewById(R.id.missing_money);
        EtMPhone = findViewById(R.id.missing_phone);
        EtMDate = findViewById(R.id.missing_date);
        EtMContext = findViewById(R.id.missing_context);
        EtMPet = findViewById(R.id.missing_pet);

        // missing_writer_btn -> 등록하기 버튼 클릭리스너
        Button missing_writer_btn = findViewById(R.id.missing_writer_btn);
        missing_writer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strName = EtMName.getText().toString();
                strTitle = EtMTitle.getText().toString();
                strMoney = EtMMoney.getText().toString();
                strPlace= EtMPlace.getText().toString();
                strPhone = EtMPhone.getText().toString();
                strDate = EtMDate.getText().toString();
                strCon = EtMContext.getText().toString();
                strPet = EtMPet.getText().toString();

                // 입력 필드가 공백일 경우 dialog 메시지 기능 제공
                if(strName.equals("") || strTitle.equals("") || strMoney.equals("") ||
                        strPlace.equals("") || strPhone.equals("") || strDate.equals("") || strCon.equals("") || strPet.equals("")){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MissingAnimalWriterActivity.this);
                    dialog.setIcon(R.mipmap.ic_launcher);
                    dialog.setTitle("알림");
                    dialog.setMessage("모두 입력하시오.");
                    dialog.setNegativeButton("확인", null);
                    dialog.show();

                } else  {
                    // 사진을 무조건 선택해야하는 조건
                    if(imageuri != null) {
                    uploadToFirebase(imageuri);

                } else {
                Toast.makeText(MissingAnimalWriterActivity.this, "사진을 선택해주세요.", Toast.LENGTH_SHORT).show();
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

                        addAnimal(strName, strTitle, strPlace, strMoney, strPhone, strDate, strCon, image, strPet);
                        //키로 아이디 생성
                        String modelId = mDatabaseRef.push().getKey();
                        mDatabaseRef = FirebaseDatabase.getInstance().getReference("MissingAnimal");
                        //데이터 넣기
                        mDatabaseRef.child("MissingAnimalImg").child(modelId).setValue(model);

                        Toast.makeText(MissingAnimalWriterActivity.this, "글이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                        intent = new Intent(MissingAnimalWriterActivity.this, MissingPostActivity.class);
                        intent.putExtra("strTitle", strTitle);
                        intent.putExtra("strMoney", strMoney);
                        intent.putExtra("strPlace", strPlace);
                        intent.putExtra("strPhone", strPhone);
                        intent.putExtra("strDate", strDate);
                        intent.putExtra("strCon", strCon);
                        intent.putExtra("strPet", strPet);
                        intent.putExtra("image", image);
                        startActivity(intent);
                        finish();
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
            Toast.makeText(MissingAnimalWriterActivity.this, "업로드 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    // 실종동물 글작성 firebase에 저장
    // MissingAnimal -> MissingAnimalInfo
    public void addAnimal(String strName, String strTitle, String strPlace, String strMoney, String strPhone, String strDate, String strCon, String image, String strPet) {
        MissingAnimalInfo info = new MissingAnimalInfo(strName, strTitle, strPlace, strMoney, strPhone, strDate, strCon, image, strPet);
        UserId = mFirebaseUser.getUid();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("MissingAnimal");
        mDatabaseRef.child("MissingAnimalInfoWriter").child(UserId).push().setValue(info);
        mDatabaseRef.child("MissingAnimalInfo").push().setValue(info);

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