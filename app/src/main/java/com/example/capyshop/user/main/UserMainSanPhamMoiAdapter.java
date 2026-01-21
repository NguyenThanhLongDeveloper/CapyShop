package com.example.capyshop.user.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capyshop.common.Interface.ItemClickListener;
import com.example.capyshop.R;
import com.example.capyshop.user.chitietsanpham.UserChiTietSanPhamActivity;
import com.example.capyshop.common.sanpham.SanPham;
import java.util.List;
import java.text.NumberFormat;
import java.util.Locale;


// Adapter cho RecyclerView hiển thị danh sách sản phẩm mới
public class UserMainSanPhamMoiAdapter extends RecyclerView.Adapter<UserMainSanPhamMoiAdapter.MyViewHolder> {

    Context context;
    List<SanPham> array;

    public UserMainSanPhamMoiAdapter(Context context, List<SanPham> array) {
        this.context = context;
        this.array = array;
    }

    // Tạo ViewHolder và gắn layout cho mỗi item
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_main_sanphammoi_item, null);
        return new MyViewHolder(item);
    }

    // Gán dữ liệu vào các View của từng item
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SanPham userSanPhamMoi = array.get(position);

        // Hiển thị tên sản phẩm
        holder.textViewTenSanPhamMoi.setText(userSanPhamMoi.getTenSanPham());

        // Định dạng và hiển thị giá
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        String giaFormatted = formatter.format(userSanPhamMoi.getGiaSanPham()) + " đ";
        holder.textViewGiaSanPhamMoi.setText(giaFormatted);

        // Tải và hiển thị hình ảnh bằng Glide
        Glide.with(context).load(userSanPhamMoi.getHinhAnhSanPham()).into(holder.imageViewHinhAnhSanPhamMoi);

        // Xử lý sự kiện click vào item
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClicḳ(View view, int position, boolean isLongClick) {
                if (!isLongClick) {
                    Intent intent = new Intent(context, UserChiTietSanPhamActivity.class);
                    // CHỈ TRUYỀN ID
                    intent.putExtra("maSanPham", array.get(position).getMaSanPham());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

    }

    // Trả về tổng số item trong danh sách
    @Override
    public int getItemCount() {
        return array.size();
    }

    // Lớp ViewHolder để lưu trữ các View của mỗi item
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewTenSanPhamMoi, textViewGiaSanPhamMoi;
        ImageView imageViewHinhAnhSanPhamMoi;
        private ItemClickListener itemClickListener;

        public MyViewHolder(View itemView) {
            super(itemView);
            // Ánh xạ các View từ layout
            textViewTenSanPhamMoi = itemView.findViewById(R.id.tv_item_ten_san_pham_moi);
            textViewGiaSanPhamMoi = itemView.findViewById(R.id.tv_item_gia_san_pham_moi);
            imageViewHinhAnhSanPhamMoi = itemView.findViewById(R.id.iv_item_hinh_anh_san_pham_moi);
            // Đặt listener cho sự kiện click
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