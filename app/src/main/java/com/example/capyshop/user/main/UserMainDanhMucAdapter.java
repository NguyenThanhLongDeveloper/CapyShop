package com.example.capyshop.user.main;

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
import com.example.capyshop.common.Interface.ItemClickListener;
import com.example.capyshop.R;
import com.example.capyshop.user.sanpham.UserSanPhamActivity;

import com.example.capyshop.common.danhmuc.DanhMuc;
import java.util.List;

// Adapter tùy chỉnh cho ListView để hiển thị danh sách Danh mục
public class UserMainDanhMucAdapter extends RecyclerView.Adapter<UserMainDanhMucAdapter.MyViewHolder> {
    Context context;
    List<DanhMuc> array;

    public UserMainDanhMucAdapter(Context context, List<DanhMuc> mangUserMainDanhMuc) {
        this.context = context;
        this.array = mangUserMainDanhMuc;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_main_danhmuc_item, null);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DanhMuc userMainDanhMuc = array.get(position);

        // Hiển thị tên sản phẩm
        holder.textViewItemTenDanhMuc.setText(userMainDanhMuc.getTenDanhMuc());

        // Tải và hiển thị hình ảnh bằng Glide
        Glide.with(context).load(userMainDanhMuc.getHinhAnhDanhMuc()).into(holder.imageViewItemHinhAnhDanhMuc);

        // Xử lý sự kiện click vào item
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClicḳ(View view, int position, boolean isLongClick) {
                if (!isLongClick) {
                    // Mở màn hình danh sách sản phẩm theo danh mục
                    Intent intent = new Intent(context, UserSanPhamActivity.class);
                    intent.putExtra("madanhmuc", userMainDanhMuc.getMaDanhMuc());
                    intent.putExtra("tendanhmuc", userMainDanhMuc.getTenDanhMuc());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageViewItemHinhAnhDanhMuc;
        TextView textViewItemTenDanhMuc;
        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewItemHinhAnhDanhMuc = itemView.findViewById(R.id.iv_item_hinh_anh_danh_muc);
            textViewItemTenDanhMuc = itemView.findViewById(R.id.tv_item_ten_danh_muc);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClicḳ(v, getAdapterPosition(), false);
        }
    }
}