package com.example.capyshop.user.chitietdonhang;

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
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class UserChiTietDonHangAdapter extends RecyclerView.Adapter<UserChiTietDonHangAdapter.MyViewHolder> {
    Context context;
    List<ChiTietDonHang> array;

    public UserChiTietDonHangAdapter(Context context, List<ChiTietDonHang> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_chitietdonhang_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChiTietDonHang userChiTietDonHang = array.get(position);
        Glide.with(context).load(userChiTietDonHang.getHinhAnhSanPham()).into(holder.ivItemHinhAnhSanPhamChiTietDonHang);
        holder.tvItemTenSanPhamChiTietDonHang.setText(userChiTietDonHang.getTenSanPham());
        holder.tvItemThuocTinhSanPhamDonHang.setText(hienThiThuocTinh(userChiTietDonHang));

        holder.tvSoLuongSanPhamChiTietDonHang.setText("x " + userChiTietDonHang.getSoLuong());
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String giaNumberFormat = numberFormat.format(userChiTietDonHang.getGiaSanPham()) + "đ";
        holder.tvItemGiaSanPhamChiTietDonHang.setText(giaNumberFormat);

    }

    private String hienThiThuocTinh(ChiTietDonHang userChiTietDonHang) {
        if (userChiTietDonHang.getThuocTinh() == null) return "";
        StringBuilder stringBuilder = new StringBuilder();
        for (DonHangThuocTinh userDonHangThuocTinh : userChiTietDonHang.getThuocTinh()) {
            stringBuilder.append(userDonHangThuocTinh.getTenThuocTinh()).append(": ");
            for (int i = 0; i < userDonHangThuocTinh.getGiaTri().size(); i++) {
                stringBuilder.append(userDonHangThuocTinh.getGiaTri().get(i).getValue());
                if (i < userDonHangThuocTinh.getGiaTri().size() - 1) stringBuilder.append(" ");
            }
            stringBuilder.append(" | ");
        }
        return stringBuilder.toString();
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItemHinhAnhSanPhamChiTietDonHang;
        TextView tvItemTenSanPhamChiTietDonHang, tvSoLuongSanPhamChiTietDonHang,
                tvItemGiaSanPhamChiTietDonHang, tvItemThuocTinhSanPhamDonHang;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemHinhAnhSanPhamChiTietDonHang = itemView.findViewById(R.id.iv_item_hinh_anh_san_pham_chi_tiet_don_hang);
            tvItemTenSanPhamChiTietDonHang = itemView.findViewById(R.id.tv_item_ten_san_pham_chi_tiet_don_hang);
            tvSoLuongSanPhamChiTietDonHang = itemView.findViewById(R.id.tv_item_so_luong_san_pham_chi_tiet_don_hang);
            tvItemGiaSanPhamChiTietDonHang = itemView.findViewById(R.id.tv_item_gia_san_pham_chi_tiet_don_hang);
            tvItemThuocTinhSanPhamDonHang = itemView.findViewById(R.id.tv_item_thuoc_tinh_san_pham_don_hang);
        }
    }

}
