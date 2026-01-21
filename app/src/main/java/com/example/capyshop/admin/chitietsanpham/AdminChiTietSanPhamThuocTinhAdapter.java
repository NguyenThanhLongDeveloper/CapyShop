package com.example.capyshop.admin.chitietsanpham;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capyshop.R;

import com.example.capyshop.common.chitietsanpham.ChiTietSanPhamThuocTinh;
import com.example.capyshop.common.chitietsanpham.ChiTietSanPhamGiaTriThuocTinh;

import java.util.List;

public class AdminChiTietSanPhamThuocTinhAdapter
        extends RecyclerView.Adapter<AdminChiTietSanPhamThuocTinhAdapter.ViewHolder> {

    private Context context;
    private List<ChiTietSanPhamThuocTinh> listThuocTinh;

    public AdminChiTietSanPhamThuocTinhAdapter(Context context, List<ChiTietSanPhamThuocTinh> listThuocTinh) {
        this.context = context;
        this.listThuocTinh = listThuocTinh;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_chitietsanpham_thuoctinh_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChiTietSanPhamThuocTinh thuocTinh = listThuocTinh.get(position);
        holder.tvAdminItemTenThuocTinh.setText(thuocTinh.getTenThuocTinh());

        // Join values with comma
        StringBuilder sb = new StringBuilder();
        if (thuocTinh.getGiaTri() != null) {
            for (ChiTietSanPhamGiaTriThuocTinh val : thuocTinh.getGiaTri()) {
                if (sb.length() > 0)
                    sb.append(", ");
                sb.append(val.getGiaTri());
            }
        }
        holder.tvAdminItemGiaTriThuocTinh.setText(sb.toString());
    }

    @Override
    public int getItemCount() {
        return listThuocTinh != null ? listThuocTinh.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAdminItemTenThuocTinh, tvAdminItemGiaTriThuocTinh;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAdminItemTenThuocTinh = itemView.findViewById(R.id.tv_admin_item_ten_thuoc_tinh);
            tvAdminItemGiaTriThuocTinh = itemView.findViewById(R.id.tv_admin_item_gia_tri_thuoc_tinh);
        }
    }
}
