package com.example.capyshop.admin.chitietdonhang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capyshop.R;

import com.example.capyshop.common.chitietdonhang.ChiTietDonHang;
import com.example.capyshop.common.chitietdonhang.DonHangThuocTinh;
import java.text.DecimalFormat;
import java.util.List;

public class AdminChiTietDonHangAdapter extends RecyclerView.Adapter<AdminChiTietDonHangAdapter.MyViewHolder> {
    Context context;
    List<ChiTietDonHang> chiTietList;

    public AdminChiTietDonHangAdapter(Context context, List<ChiTietDonHang> chiTietList) {
        this.context = context;
        this.chiTietList = chiTietList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use new Admin specific layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_chitietdonhang_item, parent,
                false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChiTietDonHang chiTiet = chiTietList.get(position);
        holder.tvItemChiTietDonHangTenSanPham.setText(chiTiet.getTenSanPham());
        holder.tvItemChiTietDonHangSoLuong.setText("x" + chiTiet.getSoLuong());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvItemChiTietDonHangGia.setText(decimalFormat.format(chiTiet.getGiaSanPham()) + " đ");

        // Attributes (Phân loại)
        String thuocTinh = hienThiThuocTinh(chiTiet);
        if (!thuocTinh.isEmpty()) {
            // Remove trailing " | " if present for cleaner look
            if (thuocTinh.endsWith(" | ")) {
                thuocTinh = thuocTinh.substring(0, thuocTinh.length() - 3);
            }
            holder.tvItemChiTietDonHangThuocTinh.setText("Phân loại: " + thuocTinh);
            holder.tvItemChiTietDonHangThuocTinh.setVisibility(View.VISIBLE);
        } else {
            holder.tvItemChiTietDonHangThuocTinh.setVisibility(View.GONE);
        }

        Glide.with(context)
                .load(chiTiet.getHinhAnhSanPham())
                .placeholder(R.drawable.ic_hinh_anh)
                .into(holder.ivItemChiTietDonHangHinhAnh);
    }

    private String hienThiThuocTinh(ChiTietDonHang chiTiet) {
        if (chiTiet.getThuocTinh() == null)
            return "";
        StringBuilder stringBuilder = new StringBuilder();
        for (DonHangThuocTinh thuocTinh : chiTiet.getThuocTinh()) {
            stringBuilder.append(thuocTinh.getTenThuocTinh()).append(": ");
            if (thuocTinh.getGiaTri() != null) {
                for (int i = 0; i < thuocTinh.getGiaTri().size(); i++) {
                    stringBuilder.append(thuocTinh.getGiaTri().get(i).getValue());
                    if (i < thuocTinh.getGiaTri().size() - 1)
                        stringBuilder.append(" ");
                }
            }
            stringBuilder.append(" | ");
        }
        return stringBuilder.toString();
    }

    @Override
    public int getItemCount() {
        return chiTietList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItemChiTietDonHangHinhAnh;
        TextView tvItemChiTietDonHangTenSanPham, tvItemChiTietDonHangSoLuong, tvItemChiTietDonHangGia, tvItemChiTietDonHangThuocTinh;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Using IDs from admin_chitietdonhang_item.xml
            ivItemChiTietDonHangHinhAnh = itemView.findViewById(R.id.iv_admin_item_chi_tiet_don_hang_hinh_anh);
            tvItemChiTietDonHangTenSanPham = itemView.findViewById(R.id.tv_admin_item_chi_tiet_don_hang_ten_san_pham);
            tvItemChiTietDonHangThuocTinh = itemView.findViewById(R.id.tv_admin_item_chi_tiet_don_hang_thuoc_tinh);
            tvItemChiTietDonHangSoLuong = itemView.findViewById(R.id.tv_admin_item_chi_tiet_don_hang_so_luong);
            tvItemChiTietDonHangGia = itemView.findViewById(R.id.tv_admin_item_chi_tiet_don_hang_gia);
        }
    }
}
