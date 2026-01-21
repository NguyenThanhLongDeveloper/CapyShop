package com.example.capyshop.admin.nguoidung;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capyshop.R;

import com.example.capyshop.common.nguoidung.NguoiDung;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminQuanLyNguoiDungAdapter extends RecyclerView.Adapter<AdminQuanLyNguoiDungAdapter.ViewHolder> {

    private Context context;
    private List<NguoiDung> mangAdminNguoiDung;
    private OnTrangThaiChangeListener trangThaiListener;

    public interface OnTrangThaiChangeListener {
        void onTrangThaiChange(NguoiDung user, boolean isChecked);
    }

    public AdminQuanLyNguoiDungAdapter(Context context, List<NguoiDung> mangAdminNguoiDung,
            OnTrangThaiChangeListener trangThaiListener) {
        this.context = context;
        this.mangAdminNguoiDung = mangAdminNguoiDung;
        this.trangThaiListener = trangThaiListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_quanlynguoidung_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NguoiDung user = mangAdminNguoiDung.get(position);
        holder.tvAdminItemNguoiDungHoTen.setText(user.getHoTenNguoiDung());
        holder.tvAdminItemNguoiDungEmail.setText(user.getEmail());
        holder.tvAdminItemNguoiDungSoDienThoai.setText("SĐT: " + user.getSoDienThoai());

        if (user.getHinhAnhNguoiDung() != null && !user.getHinhAnhNguoiDung().isEmpty()) {
            Glide.with(context).load(user.getHinhAnhNguoiDung()).into(holder.civAdminItemNguoiDungAnhDaiDien);
        } else {
            holder.civAdminItemNguoiDungAnhDaiDien.setImageResource(R.drawable.ic_hinh_anh);
        }

        // Status Binding
        if ("HOAT_DONG".equals(user.getTrangThai())) {
            holder.tvAdminItemNguoiDungTrangThaiNhan.setText("HOẠT ĐỘNG");
            holder.tvAdminItemNguoiDungTrangThaiNhan.setTextColor(android.graphics.Color.parseColor("#16A34A"));
        } else {
            holder.tvAdminItemNguoiDungTrangThaiNhan.setText("BỊ KHÓA");
            holder.tvAdminItemNguoiDungTrangThaiNhan.setTextColor(android.graphics.Color.parseColor("#EF4444"));
        }

        holder.swAdminItemNguoiDungTrangThai.setOnCheckedChangeListener(null);
        holder.swAdminItemNguoiDungTrangThai.setChecked("HOAT_DONG".equals(user.getTrangThai()));

        holder.swAdminItemNguoiDungTrangThai.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (trangThaiListener != null) {
                trangThaiListener.onTrangThaiChange(user, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mangAdminNguoiDung != null ? mangAdminNguoiDung.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civAdminItemNguoiDungAnhDaiDien;
        TextView tvAdminItemNguoiDungHoTen, tvAdminItemNguoiDungTrangThaiNhan, tvAdminItemNguoiDungEmail, tvAdminItemNguoiDungSoDienThoai;
        SwitchCompat swAdminItemNguoiDungTrangThai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            civAdminItemNguoiDungAnhDaiDien = itemView.findViewById(R.id.civ_admin_anh_dai_dien_nguoi_dung);
            tvAdminItemNguoiDungHoTen = itemView.findViewById(R.id.tv_admin_ho_ten_nguoi_dung);
            tvAdminItemNguoiDungTrangThaiNhan = itemView.findViewById(R.id.tv_admin_trang_thai_nhan);
            tvAdminItemNguoiDungEmail = itemView.findViewById(R.id.tv_admin_email_nguoi_dung);
            tvAdminItemNguoiDungSoDienThoai = itemView.findViewById(R.id.tv_admin_so_dien_thoai_nguoi_dung);
            swAdminItemNguoiDungTrangThai = itemView.findViewById(R.id.sw_admin_trang_thai_nguoi_dung);
        }
    }
}
