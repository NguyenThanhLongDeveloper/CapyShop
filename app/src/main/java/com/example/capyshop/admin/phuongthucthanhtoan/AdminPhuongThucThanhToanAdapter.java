package com.example.capyshop.admin.phuongthucthanhtoan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capyshop.R;

import com.example.capyshop.common.phuongthucthanhtoan.PhuongThucThanhToan;
import java.util.List;

public class AdminPhuongThucThanhToanAdapter
        extends RecyclerView.Adapter<AdminPhuongThucThanhToanAdapter.MyViewHolder> {

    Context context;
    List<PhuongThucThanhToan> mangPhuongThucThanhToan;
    OnTrangThaiChangeListener listenerTrangThai;

    public interface OnTrangThaiChangeListener {
        void onTrangThaiChange(PhuongThucThanhToan item, boolean isChecked);
    }

    public AdminPhuongThucThanhToanAdapter(Context context, List<PhuongThucThanhToan> mangPhuongThucThanhToan,
            OnTrangThaiChangeListener listener) {
        this.context = context;
        this.mangPhuongThucThanhToan = mangPhuongThucThanhToan;
        this.listenerTrangThai = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_quanlyphuongthucthanhtoan_item, parent,
                false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PhuongThucThanhToan item = mangPhuongThucThanhToan.get(position);
        holder.tvAdminItemPhuongThucThanhToanTen.setText(item.getTenPhuongThucThanhToan());
        holder.tvAdminItemPhuongThucThanhToanPhuDe.setText(laySubtitle(item));

        // Avoid infinite loop listener
        holder.swAdminItemPhuongThucThanhToanTrangThai.setOnCheckedChangeListener(null);
        holder.swAdminItemPhuongThucThanhToanTrangThai.setChecked("HOAT_DONG".equals(item.getTrangThai()));

        holder.swAdminItemPhuongThucThanhToanTrangThai.setOnCheckedChangeListener((buttonView, isChecked) -> {
            listenerTrangThai.onTrangThaiChange(item, isChecked);
        });
    }

    private String laySubtitle(PhuongThucThanhToan item) {
        String ten = item.getTenPhuongThucThanhToan().toLowerCase();
        if (ten.contains("nhận hàng") || ten.contains("cod")) {
            return "COD - Tiền mặt";
        } else if (ten.contains("ngân hàng") || ten.contains("chuyển khoản")) {
            return "TP Bank •••• 2369";
        } else if (ten.contains("momo")) {
            return "HOAT_DONG".equals(item.getTrangThai()) ? "Ví đã liên kết" : "Chưa kích hoạt";
        }
        return "Phương thức thanh toán trực tuyến";
    }

    @Override
    public int getItemCount() {
        return mangPhuongThucThanhToan != null ? mangPhuongThucThanhToan.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvAdminItemPhuongThucThanhToanTen, tvAdminItemPhuongThucThanhToanPhuDe;
        SwitchCompat swAdminItemPhuongThucThanhToanTrangThai;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAdminItemPhuongThucThanhToanTen = itemView.findViewById(R.id.tv_admin_item_phuong_thuc_thanh_toan_ten);
            tvAdminItemPhuongThucThanhToanPhuDe = itemView.findViewById(R.id.tv_admin_item_phuong_thuc_thanh_toan_phu_de);
            swAdminItemPhuongThucThanhToanTrangThai = itemView.findViewById(R.id.sw_admin_item_phuong_thuc_thanh_toan_trang_thai);
        }
    }
}
