package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 후원요청 글 화면
*/
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SponsorActivity extends AppCompatActivity {

    private RecyclerView recyclerView;                                  // 리사이클러뷰
    private RecyclerView.Adapter adapter;                               // 리사이클러뷰 어댑터
    private RecyclerView.LayoutManager layoutManager;                   // 리사이클러뷰 레이아웃매니저
    private ArrayList<SponsorInfo> arrayList;                            // ArrayList<SponsorInfo>
    private DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("Sponsor");     // 실시간 데이터베이스

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor);

        recyclerView = findViewById(R.id.recyclerView); // 아이디 연결
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // SponsorInfo 객체를 담을 어레이 리스트 (어댑터쪽으로 데이터 전송)

        // 후원요청 firebase에서 가져오는 것
        // Sponsor -> SponsorInfo
        mDatabaseRef.child("SponsorInfo").limitToLast(100).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                    SponsorInfo sponsorInfo = snapshot.getValue(SponsorInfo.class); // 만들어뒀던 SponsorInfo 객체에 데이터를 담는다.
                    arrayList.add(0, sponsorInfo); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던 중 에러 발생 시
                Log.e("SponsorActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        adapter = new SponsorAdapter(arrayList, this);
        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결

        // button -> 후원요청 글 버튼 클릭리스너 -> SponsorWriterActivity로 이동
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 등록 화면으로 변경
                Intent intent = new Intent(SponsorActivity.this, SponsorWriterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}