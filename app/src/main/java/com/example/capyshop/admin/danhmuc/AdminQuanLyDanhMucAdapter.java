package com.example.capyshop.admin.danhmuc;

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

import com.example.capyshop.common.danhmuc.DanhMuc;
import java.util.List;

import com.example.capyshop.common.Interface.ImageClickListener;

public class AdminQuanLyDanhMucAdapter extends RecyclerView.Adapter<AdminQuanLyDanhMucAdapter.ViewHolder> {

    private Context context;
    private List<DanhMuc> mangDanhMuc;
    private ImageClickListener listenerClickDanhMuc;

    public AdminQuanLyDanhMucAdapter(Context context, List<DanhMuc> mangDanhMuc,
            ImageClickListener listenerClickDanhMuc) {
        this.context = context;
        this.mangDanhMuc = mangDanhMuc;
        this.listenerClickDanhMuc = listenerClickDanhMuc;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_quanlydanhmuc_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DanhMuc danhMuc = mangDanhMuc.get(position);
        holder.tvAdminItemDanhMucTen.setText(danhMuc.getTenDanhMuc());
        holder.tvAdminItemDanhMucSoLuongSanPham.setText("Mã: " + danhMuc.getMaDanhMuc());

        Glide.with(context)
                .load(danhMuc.getHinhAnhDanhMuc())
                .placeholder(R.drawable.ic_hinh_anh)
                .into(holder.ivAdminItemDanhMucHinhAnh);

        holder.ivAdminItemDanhMucSua.setOnClickListener(v -> {
            if (listenerClickDanhMuc != null)
                listenerClickDanhMuc.onImageClick(v, position, 0);
        });

        holder.ivAdminItemDanhMucXoa.setOnClickListener(v -> {
            if (listenerClickDanhMuc != null)
                listenerClickDanhMuc.onImageClick(v, position, 1);
        });
    }

    @Override
    public int getItemCount() {
        return mangDanhMuc != null ? mangDanhMuc.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAdminItemDanhMucHinhAnh, ivAdminItemDanhMucSua, ivAdminItemDanhMucXoa;
        TextView tvAdminItemDanhMucTen, tvAdminItemDanhMucSoLuongSanPham;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAdminItemDanhMucHinhAnh = itemView.findViewById(R.id.iv_admin_item_danh_muc_hinh_anh);
            ivAdminItemDanhMucSua = itemView.findViewById(R.id.iv_admin_item_danh_muc_sua);
            ivAdminItemDanhMucXoa = itemView.findViewById(R.id.iv_admin_item_danh_muc_xoa);
            tvAdminItemDanhMucTen = itemView.findViewById(R.id.tv_admin_item_danh_muc_ten);
            tvAdminItemDanhMucSoLuongSanPham = itemView.findViewById(R.id.tv_admin_item_danh_muc_so_luong_san_pham);
        }
    }
}
