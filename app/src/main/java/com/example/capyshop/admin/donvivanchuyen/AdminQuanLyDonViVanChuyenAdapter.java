package com.example.capyshop.admin.donvivanchuyen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capyshop.R;
import com.example.capyshop.common.Interface.ImageClickListener;

import com.example.capyshop.common.donvivanchuyen.DonViVanChuyen;
import java.text.DecimalFormat;
import java.util.List;

public class AdminQuanLyDonViVanChuyenAdapter
        extends RecyclerView.Adapter<AdminQuanLyDonViVanChuyenAdapter.ViewHolder> {

    private Context context;
    private List<DonViVanChuyen> mangDonVi;
    private ImageClickListener listener;
    private OnTrangThaiChangeListener trangThaiListener;

    public interface OnTrangThaiChangeListener {
        void onTrangThaiChange(DonViVanChuyen donVi, boolean isChecked);
    }

    public AdminQuanLyDonViVanChuyenAdapter(Context context, List<DonViVanChuyen> mangDonVi,
            ImageClickListener listener, OnTrangThaiChangeListener trangThaiListener) {
        this.context = context;
        this.mangDonVi = mangDonVi;
        this.listener = listener;
        this.trangThaiListener = trangThaiListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_quanlydonvivanchuyen_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonViVanChuyen donVi = mangDonVi.get(position);
        holder.tvAdminItemDonViVanChuyenTen.setText(donVi.getTenDonViVanChuyen());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvAdminItemDonViVanChuyenGia.setText("Giá cước: " + decimalFormat.format(donVi.getGiaDonViVanChuyen()) + "đ");

        if ("HOAT_DONG".equals(donVi.getTrangThai())) {
            holder.tvAdminItemDonViVanChuyenTrangThaiNhan.setText("HOẠT ĐỘNG");
            holder.tvAdminItemDonViVanChuyenTrangThaiNhan.setTextColor(context.getResources().getColor(R.color.Nude_pink)); // Adjusted to match design
        } else {
            holder.tvAdminItemDonViVanChuyenTrangThaiNhan.setText("TẠM DỪNG");
            holder.tvAdminItemDonViVanChuyenTrangThaiNhan.setTextColor(context.getResources().getColor(R.color.black));
        }

        holder.tvAdminItemDonViVanChuyenThoiGianCapNhat.setText("Cập nhật: " + donVi.getThoiGianTao());

        holder.ivAdminItemDonViVanChuyenSua.setOnClickListener(v -> {
            if (listener != null) {
                listener.onImageClick(v, position, 0); // Reuse listener for edit
            }
        });

        holder.ivAdminItemDonViVanChuyenXoa.setOnClickListener(v -> {
            if (listener != null) {
                listener.onImageClick(v, position, 1); // Assuming 1 is for delete
            }
        });
    }

    @Override
    public int getItemCount() {
        return mangDonVi != null ? mangDonVi.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAdminItemDonViVanChuyenTen, tvAdminItemDonViVanChuyenGia, tvAdminItemDonViVanChuyenTrangThaiNhan, tvAdminItemDonViVanChuyenThoiGianCapNhat;
        ImageView ivAdminItemDonViVanChuyenSua, ivAdminItemDonViVanChuyenXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAdminItemDonViVanChuyenTen = itemView.findViewById(R.id.tv_admin_item_don_vi_van_chuyen_ten);
            tvAdminItemDonViVanChuyenGia = itemView.findViewById(R.id.tv_admin_item_don_vi_van_chuyen_gia);
            tvAdminItemDonViVanChuyenTrangThaiNhan = itemView.findViewById(R.id.tv_admin_item_don_vi_van_chuyen_trang_thai_nhan);
            tvAdminItemDonViVanChuyenThoiGianCapNhat = itemView.findViewById(R.id.tv_admin_item_don_vi_van_chuyen_thoi_gian_cap_nhat);
            ivAdminItemDonViVanChuyenSua = itemView.findViewById(R.id.iv_admin_item_don_vi_van_chuyen_sua);
            ivAdminItemDonViVanChuyenXoa = itemView.findViewById(R.id.iv_admin_item_don_vi_van_chuyen_xoa);
        }
    }
}
