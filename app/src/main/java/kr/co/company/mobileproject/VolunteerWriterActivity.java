package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 자원봉사 글 작성 화면
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class VolunteerWriterActivity extends AppCompatActivity {

    // 이미지 저장 추가
    private ImageView imageView;
    // 데이터베이스 저장
    private FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mDatabaseRef;                // 실시간 데이터베이스
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private Uri imageuri;
    Intent intent;
    private EditText EtMName, EtMTitle, ETcompany, ETmname, EtMPlace, EtMPhone, EtMDate, ETvdate, EThaddr, EtMContext;
    String UserId, strName, strTitle, strcompany, strMname, strPlace, strPhone, strDate, strVDate, strHAddr, strCon, image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_writer);

        // volunteer_picture_btn -> 이미지 선택 버튼 클릭리스너
        Button volunteer_picture_btn = findViewById(R.id.volunteer_picture_btn);
        imageView = findViewById(R.id.volunteer_imageView);

        volunteer_picture_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                startActivityResult.launch(intent);

            }
        });

        EtMName = findViewById(R.id.volunteer_name);
        EtMTitle = findViewById(R.id.volunteer_title);
        ETcompany = findViewById(R.id.volunteer_company);
        ETmname = findViewById(R.id.volunteer_mainName);
        EtMPlace = findViewById(R.id.volunteer_place);
        EtMPhone = findViewById(R.id.volunteer_phone);
        EtMDate = findViewById(R.id.volunteer_date);
        ETvdate = findViewById(R.id.volunteer_date2);
        EThaddr = findViewById(R.id.volunteer_http);
        EtMContext = findViewById(R.id.volunteer_context);

        // volunteer_writer_btn -> 등록하기 버튼 클릭리스너
        Button volunteer_writer_btn = findViewById(R.id.volunteer_writer_btn);
        volunteer_writer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strName = EtMName.getText().toString();
                strTitle = EtMTitle.getText().toString();
                strcompany = ETcompany.getText().toString();
                strMname = ETmname.getText().toString();
                strPlace= EtMPlace.getText().toString();
                strPhone = EtMPhone.getText().toString();
                strDate = EtMDate.getText().toString();
                strVDate = ETvdate.getText().toString();
                strHAddr = EThaddr.getText().toString();
                strCon = EtMContext.getText().toString();


                // 입력 필드가 공백일 경우 dialog 메시지 기능 제공
                if(strName.equals("") || strTitle.equals("") || strcompany.equals("") || strMname.equals("") || strPlace.equals("") ||
                         strPhone.equals("") || strDate.equals("") || strVDate.equals("") || strHAddr.equals("") ||strCon.equals("")){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(VolunteerWriterActivity.this);
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
                        Toast.makeText(VolunteerWriterActivity.this, "사진을 선택해주세요.", Toast.LENGTH_SHORT).show();
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

                        addVolunteer(image, strName, strTitle, strcompany, strMname, strPlace, strPhone, strDate, strVDate, strHAddr, strCon);
                        //키로 아이디 생성
                        String modelId = mDatabaseRef.push().getKey();
                        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Volunteer");
                        //데이터 넣기
                        mDatabaseRef.child("VolunImg").child(modelId).setValue(model);

                        Toast.makeText(VolunteerWriterActivity.this, "글이 등록되었습니다.", Toast.LENGTH_SHORT).show();

                        intent = new Intent(VolunteerWriterActivity.this, VolunteerPostActivity.class);
                        intent.putExtra("strName", strName);
                        intent.putExtra("strTitle", strTitle);
                        intent.putExtra("strcompany", strcompany);
                        intent.putExtra("strMname", strMname);
                        intent.putExtra("strPlace", strPlace);
                        intent.putExtra("strPhone", strPhone);
                        intent.putExtra("strDate", strDate);
                        intent.putExtra("strVDate", strVDate);
                        intent.putExtra("strHAddr", strHAddr);
                        intent.putExtra("strCon", strCon);
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
                Toast.makeText(VolunteerWriterActivity.this, "업로드 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    // 자원봉사 글작성 firebase에 저장
    // Volunteer -> VolunteerInfo
    public void addVolunteer(String imageUrl, String strName, String strTitle, String strcompany, String strMname, String strPlace, String strPhone, String strDate, String strVDate, String strHAddr, String strCon) {
        VolunteerInfo info = new VolunteerInfo(imageUrl, strName, strTitle, strcompany, strMname, strPlace, strPhone, strDate, strVDate, strHAddr, strCon);
        UserId = mFirebaseUser.getUid();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Volunteer");
        mDatabaseRef.child("VolunteerInfoWriter").child(UserId).push().setValue(info);
        mDatabaseRef.child("VolunteerInfo").push().setValue(info);
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