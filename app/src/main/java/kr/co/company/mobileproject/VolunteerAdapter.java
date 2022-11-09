package kr.co.company.mobileproject;
// 자원봉사 글 어댑터
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

    public class VolunteerAdapter extends RecyclerView.Adapter<VolunteerAdapter.CustomViewHolder> {

        private ArrayList<VolunteerInfo> arrayList;
        private Context context;

        public VolunteerAdapter(ArrayList<VolunteerInfo> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
        }

        @NonNull
        @Override
        // list_item에 대한 최초의 뷰 생성
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_volunteer, parent, false);
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
            holder.tv_context.setText(arrayList.get(position).getContext());
            holder.tv_company.setText(arrayList.get(position).getCompanyName());
            holder.tv_date.setText(arrayList.get(position).getDate());
            holder.tv_place.setText(arrayList.get(position).getPlace());
            holder.tv_volunteer_date.setText(arrayList.get(position).getVolunteerDate());

            // 리사이클러뷰 클릭이벤트(선택된 리사이클러뷰 화면으로 이동)
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int mPosition = holder.getAdapterPosition();
                    Context context = view.getContext();

                    Intent intent = new Intent(context, VolunteerPostClick.class);
                    intent.putExtra("title", arrayList.get(mPosition).getTitle());
                    intent.putExtra("companyName", arrayList.get(mPosition).getCompanyName());
                    intent.putExtra("mainName", arrayList.get(mPosition).getMainName());
                    intent.putExtra("phone", arrayList.get(mPosition).getPhone());
                    intent.putExtra("httpAddress", arrayList.get(mPosition).getHttpAddress());
                    intent.putExtra("name", arrayList.get(mPosition).getName());
                    intent.putExtra("date", arrayList.get(mPosition).getDate());
                    intent.putExtra("volunteerDate", arrayList.get(mPosition).getVolunteerDate());
                    intent.putExtra("place", arrayList.get(mPosition).getPlace());
                    intent.putExtra("context", arrayList.get(mPosition).getContext());
                    intent.putExtra("imageUrl", arrayList.get(mPosition).getImageUrl());

                    ((VolunteerActivity)context).startActivity(intent);
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
            TextView tv_title;
            TextView tv_context;
            TextView tv_company;
            TextView tv_date;
            TextView tv_place;
            TextView tv_volunteer_date;


            public CustomViewHolder(@NonNull View itemView) {
                super(itemView);
                this.iv_profile = itemView.findViewById(R.id.iv_profile);
                this.tv_title= itemView.findViewById(R.id.tv_title);
                this.tv_context = itemView.findViewById(R.id.tv_context);
                this.tv_company = itemView.findViewById(R.id.tv_company);
                this.tv_date = itemView.findViewById(R.id.tv_date);
                this.tv_place = itemView.findViewById(R.id.tv_place);
                this.tv_volunteer_date = itemView.findViewById(R.id.tv_volunteer_date);

            }
        }
    }