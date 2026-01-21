package com.example.capyshop.user.chitietsanpham;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.capyshop.R;

import com.example.capyshop.common.chitietsanpham.ChiTietSanPhamGiaTriThuocTinh;
import java.util.List;

public class UserChiTietSanPhamGiaTriThuocTinhAdapter extends RecyclerView.Adapter<UserChiTietSanPhamGiaTriThuocTinhAdapter.MyViewHolder> {
    Context context;
    List<ChiTietSanPhamGiaTriThuocTinh> array;
    private int selectedPosition = -1;
    private Runnable giaTriThuocTinhDaChon; // Sử dụng Runnable để back dữ liệu

    public UserChiTietSanPhamGiaTriThuocTinhAdapter(Context context, List<ChiTietSanPhamGiaTriThuocTinh> array, Runnable callback) {
        this.context = context;
        this.array = array;
        this.giaTriThuocTinhDaChon = callback;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_chitietsanpham_giatrithuoctinh_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChiTietSanPhamGiaTriThuocTinh userChiTietSanPhamGiaTriThuocTinh = array.get(position);
        holder.tvItemGiaTriThuocTinhChiTietSanPham.setText(userChiTietSanPhamGiaTriThuocTinh.getGiaTri());

        if (selectedPosition == position) {
            holder.tvItemGiaTriThuocTinhChiTietSanPham.setBackgroundResource(R.drawable.da_chon_backgroud);
            holder.tvItemGiaTriThuocTinhChiTietSanPham.setTextColor(Color.BLACK);
            userChiTietSanPhamGiaTriThuocTinh.setSelected(true);
        } else {
            holder.tvItemGiaTriThuocTinhChiTietSanPham.setBackgroundResource(R.drawable.chua_chon_backgroud);
            holder.tvItemGiaTriThuocTinhChiTietSanPham.setTextColor(Color.BLACK);
            userChiTietSanPhamGiaTriThuocTinh.setSelected(false);
        }

        // Trong onClick của holder.itemView
        holder.itemView.setOnClickListener(v -> {
            int currentPos = holder.getAdapterPosition();
            if (currentPos != RecyclerView.NO_POSITION) {
                // Reset trạng thái các item khác trong danh sách này
                for (int i = 0; i < array.size(); i++) {
                    array.get(i).setSelected(false);
                }

                selectedPosition = currentPos;
                array.get(currentPos).setSelected(true); // Đánh dấu item này đã chọn

                notifyDataSetChanged();

                if (giaTriThuocTinhDaChon != null) {
                    giaTriThuocTinhDaChon.run();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return array != null ? array.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemGiaTriThuocTinhChiTietSanPham;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemGiaTriThuocTinhChiTietSanPham = itemView.findViewById(R.id.tv_item_gia_tri_thuoc_tinh_chi_tiet_san_pham);
        }
    }
}