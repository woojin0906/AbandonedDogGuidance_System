package kr.co.company.mobileproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<CommentInfo> arrayList;
    private DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("Chat");
    private FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    private EditText Etcomment;
    private String UserId, comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Etcomment = findViewById(R.id.comment_et);
        UserId = mFirebaseUser.getEmail();

        // comment_btn -> 등록 버튼 클릭리스너
        Button comment_btn = findViewById(R.id.comment_btn);
        comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comment = Etcomment.getText().toString();
                if(comment.equals("")) {
                    Toast.makeText(ChatActivity.this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {

                    addinfo(comment, UserId);
                    mDatabaseRef = FirebaseDatabase.getInstance().getReference("Chat");
                    Etcomment.setText("");

                }
            }
        });

        recyclerView = findViewById(R.id.recyclerView); // 아이디 연결
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // CommentInfo 객체를 담을 어레이 리스트 (어댑터쪽으로 데이터 전송)

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CommentInfo commentInfo = snapshot.getValue(CommentInfo.class);
                    arrayList.add(commentInfo);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ChatActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        adapter = new CommentAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);
    }

    public void addinfo(String comment, String id) {
        CommentInfo info = new CommentInfo(comment, id);
        mDatabaseRef.push().setValue(info);
    }
}