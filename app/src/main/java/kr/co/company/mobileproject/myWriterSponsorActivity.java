package kr.co.company.mobileproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class myWriterSponsorActivity extends AppCompatActivity {

    private FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private RecyclerView recyclerView;                                  // 리사이클러뷰
    private RecyclerView.Adapter adapter;                               // 리사이클러뷰 어댑터
    private RecyclerView.LayoutManager layoutManager;                   // 리사이클러뷰 레이아웃매니저
    private ArrayList<SponsorInfo> arrayList;                     // ArrayList<MissingAnimalInfo>
    private DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("Sponsor");  // 실시간 데이터베이스
    private String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_writer_sponsor);

        recyclerView = findViewById(R.id.recyclerView);     // 아이디 연결
        recyclerView.setHasFixedSize(true);                 // 리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();                      // MissingAnimalInfo 객체를 담을 어레이 리스트 (어댑터쪽으로 데이터 전송)

        UserId = mFirebaseUser.getUid();

        // 실종동물찾기 firebase에서 가져오는 것
        // MissingAnimal -> MissingAnimalInfo
        mDatabaseRef.child("SponsorInfoWriter").child(UserId).limitToLast(100).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                    SponsorInfo sponsorInfo = snapshot.getValue(SponsorInfo.class); // 만들어뒀던 MissingAnimalInfo 객체에 데이터를 담는다.
                    arrayList.add(0, sponsorInfo);  // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던 중 에러 발생 시 에러문 출력
                Log.e("myWriterSponsorActivity", String.valueOf(databaseError.toException()));
            }
        });

        adapter = new MyWriterSponsorAdapter(arrayList, this);
        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결
    }
}