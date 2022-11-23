package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 나의 실종동물 글 어댑터
*/
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyWriterMissingAdapter extends RecyclerView.Adapter<MyWriterMissingAdapter.CustomViewHolder> {

    private ArrayList<MissingAnimalInfo> arrayList;
    private Context context;

    public MyWriterMissingAdapter(ArrayList<MissingAnimalInfo> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    // list_item에 대한 최초의 뷰 생성
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_animal, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    // 실제 아이템 매칭 역할
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getImageUrl())
                .into(holder.iv_profile);
        holder.tv_title.setText(arrayList.get(position).getTitle());
        holder.tv_pet.setText(arrayList.get(position).getPet());
        holder.tv_date.setText(arrayList.get(position).getDate());
        holder.tv_money.setText(arrayList.get(position).getMoney());
        holder.tv_place.setText(arrayList.get(position).getPlace());

        // 리사이클러뷰 클릭이벤트(선택된 리사이클러뷰 화면으로 이동)
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mPosition = holder.getAdapterPosition();
                Context context = view.getContext();

                Intent intent = new Intent(context, MissingPostClick.class);
                intent.putExtra("context", arrayList.get(mPosition).getContext());
                intent.putExtra("date", arrayList.get(mPosition).getDate());
                intent.putExtra("money", arrayList.get(mPosition).getMoney());
                intent.putExtra("pet", arrayList.get(mPosition).getPet());
                intent.putExtra("phone", arrayList.get(mPosition).getPhone());
                intent.putExtra("title", arrayList.get(mPosition).getTitle());
                intent.putExtra("place", arrayList.get(mPosition).getPlace());
                intent.putExtra("imageUrl", arrayList.get(mPosition).getImageUrl());


                ((myWriterMissingActivity)context).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //삼합 연산자 (arrayList가 널이 아니면 참인 경우 arrayList.size(), 거짓인 경우 0)
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        TextView tv_title, tv_pet, tv_date, tv_money, tv_place;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_profile);
            this.tv_title= itemView.findViewById(R.id.tv_title);
            this.tv_pet = itemView.findViewById(R.id.tv_pet);
            this.tv_date = itemView.findViewById(R.id.tv_date);
            this.tv_money = itemView.findViewById(R.id.tv_money);
            this.tv_place = itemView.findViewById(R.id.tv_place);

        }

    }
}