package com.example.capyshop.user.chitietsanpham;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.capyshop.R;

import com.example.capyshop.common.chitietsanpham.ChiTietSanPhamThuocTinh;
import java.util.List;

public class UserChiTietSanPhamThuocTinhAdapter extends RecyclerView.Adapter<UserChiTietSanPhamThuocTinhAdapter.MyViewHolder> {
    Context context;
    List<ChiTietSanPhamThuocTinh> array;
    Runnable giaTriThuocTinhDaChon; // Nhận từ Activity

    public UserChiTietSanPhamThuocTinhAdapter(Context context, List<ChiTietSanPhamThuocTinh> array, Runnable callback) {
        this.context = context;
        this.array = array;
        this.giaTriThuocTinhDaChon = callback;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_chitietsanpham_thuoctinh_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChiTietSanPhamThuocTinh userChiTietSanPhamThuocTinh = array.get(position);
        holder.tvItemTenThuocTinhChiTietSanPham.setText(userChiTietSanPhamThuocTinh.getTenThuocTinh());

        // Truyền Runnable xuống Adapter con
        UserChiTietSanPhamGiaTriThuocTinhAdapter giaTriAdapter = new UserChiTietSanPhamGiaTriThuocTinhAdapter(
                context,
                userChiTietSanPhamThuocTinh.getGiaTri(),
                giaTriThuocTinhDaChon);

        holder.rvItemGiaTriThuocTinhChiTietSanPham.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rvItemGiaTriThuocTinhChiTietSanPham.setAdapter(giaTriAdapter);
    }

    @Override
    public int getItemCount() {
        return array != null ? array.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemTenThuocTinhChiTietSanPham;
        RecyclerView rvItemGiaTriThuocTinhChiTietSanPham;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemTenThuocTinhChiTietSanPham = itemView.findViewById(R.id.tv_item_ten_thuoc_tinh_chi_tiet_san_pham);
            rvItemGiaTriThuocTinhChiTietSanPham = itemView.findViewById(R.id.rv_item_gia_tri_thuoc_tinh_chi_tiet_san_pham);
        }
    }
}