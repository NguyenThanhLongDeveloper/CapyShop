package com.example.capyshop.admin.donhang;

import android.content.Context;
import android.content.Intent;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capyshop.R;
import com.example.capyshop.admin.chitietdonhang.AdminChiTietDonHangActivity;
import com.example.capyshop.admin.chitietdonhang.AdminChiTietDonHangAdapter;
import com.example.capyshop.common.Interface.ItemClickListener;

import com.example.capyshop.common.chitietdonhang.ChiTietDonHang;
import com.example.capyshop.common.donhang.DonHang;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminQuanLyDonHangAdapter extends RecyclerView.Adapter<AdminQuanLyDonHangAdapter.ViewHolder> {

    private Context context;
    private List<DonHang> mangDonHang;
    private OnDonHangInteractionListener listener;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private final Set<Integer> expandedOrders = new HashSet<>();

    public interface OnDonHangInteractionListener {
        void onXacNhanClick(DonHang donHang);
    }

    public AdminQuanLyDonHangAdapter(Context context, List<DonHang> mangDonHang,
            OnDonHangInteractionListener listener) {
        this.context = context;
        this.mangDonHang = mangDonHang;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_quanlydonhang_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonHang adminDonHang = mangDonHang.get(position);

        // Bind data
        holder.tvItemDonHangMaDonHang.setText("Mã đơn hàng : " + adminDonHang.getMaDonHang());
        holder.tvItemDonHangNgayDat.setText("• " + adminDonHang.getThoiGianTao());
        holder.tvItemDonHangTenNguoiDung.setText(adminDonHang.getTenNguoiDung());
        Glide.with(context)
                .load(adminDonHang.getHinhAnhNguoiDung())
                .placeholder(R.drawable.ic_hinh_anh)
                .into(holder.civItemDonHangAnhDaiDien);

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvItemDonHangTongTien.setText(decimalFormat.format(adminDonHang.getTongTien()) + " đ");

        // Status Button logic
        String trangThai = adminDonHang.getTrangThai();
        configureStatus(holder, trangThai);

        // Nested RecyclerView (Products) logic
        List<ChiTietDonHang> chiTietList = adminDonHang.getChitietdonhang();

        // Safety check
        if (chiTietList == null) {
            holder.rvItemDonHangChiTiet.setAdapter(null);
            holder.tvItemDonHangXemThemChiTiet.setVisibility(View.GONE);
            return;
        }

        int orderId = adminDonHang.getMaDonHang();
        boolean isExpanded = expandedOrders.contains(orderId);
        List<ChiTietDonHang> displayList;

        if (isExpanded || chiTietList.size() <= 1) {
            displayList = chiTietList;
            holder.tvItemDonHangXemThemChiTiet.setText("Thu gọn");
            holder.tvItemDonHangXemThemChiTiet.setVisibility(chiTietList.size() > 1 ? View.VISIBLE : View.GONE);
        } else {
            displayList = chiTietList.subList(0, 1);
            holder.tvItemDonHangXemThemChiTiet.setText("Xem thêm (" + chiTietList.size() + " sản phẩm)");
            holder.tvItemDonHangXemThemChiTiet.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.rvItemDonHangChiTiet.getContext(),
                LinearLayoutManager.VERTICAL, false);
        layoutManager.setInitialPrefetchItemCount(displayList.size());

        // Use NEW Admin Adapter
        AdminChiTietDonHangAdapter childAdapter = new AdminChiTietDonHangAdapter(context, displayList);
        holder.rvItemDonHangChiTiet.setLayoutManager(layoutManager);
        holder.rvItemDonHangChiTiet.setAdapter(childAdapter);
        holder.rvItemDonHangChiTiet.setRecycledViewPool(viewPool);

        // Confirm Button Click
        holder.btItemDonHangXacNhan.setOnClickListener(v -> {
            if (listener != null)
                listener.onXacNhanClick(adminDonHang);
        });

        // Expand/Collapse logic
        holder.tvItemDonHangXemThemChiTiet.setOnClickListener(v -> {
            ViewGroup parent = (ViewGroup) holder.itemView.getParent();
            if (parent != null) {
                TransitionManager.beginDelayedTransition(parent, new AutoTransition());
            }

            if (isExpanded) {
                expandedOrders.remove(orderId);
            } else {
                expandedOrders.add(orderId);
            }
            notifyItemChanged(position);
        });

        // Open Details Screen
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClicḳ(View view, int position, boolean isLongClick) {
                if (!isLongClick) {
                    Intent intent = new Intent(context, AdminChiTietDonHangActivity.class);
                    intent.putExtra("donhang", adminDonHang);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

    }

    private void configureStatus(ViewHolder holder, String trangThai) {
        holder.btItemDonHangXacNhan.setVisibility(View.GONE); // Default hidden

        switch (trangThai) {
            case "CHO_XAC_NHAN":
                holder.tvItemDonHangTrangThai.setText("Chờ xác nhận");
                holder.btItemDonHangXacNhan.setVisibility(View.VISIBLE);
                holder.btItemDonHangXacNhan.setText("Xác nhận đơn");
                break;

            case "CHO_LAY_HANG":
                holder.tvItemDonHangTrangThai.setText("Chờ lấy hàng");
                holder.btItemDonHangXacNhan.setVisibility(View.VISIBLE);
                holder.btItemDonHangXacNhan.setText("Giao vận chuyển");
                break;

            case "DANG_GIAO_HANG":
                holder.tvItemDonHangTrangThai.setText("Đang giao hàng");
                holder.btItemDonHangXacNhan.setVisibility(View.VISIBLE);
                holder.btItemDonHangXacNhan.setText("Đã giao xong");
                break;

            case "DA_GIAO_HANG":
                holder.tvItemDonHangTrangThai.setText("Đã giao hàng");
                break;

            case "DA_HUY":
                holder.tvItemDonHangTrangThai.setText("ĐÃ HỦY");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mangDonHang != null ? mangDonHang.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        TextView tvItemDonHangMaDonHang, tvItemDonHangNgayDat, tvItemDonHangTenNguoiDung, tvItemDonHangTongTien, tvItemDonHangTrangThai, tvItemDonHangXemThemChiTiet;
        CircleImageView civItemDonHangAnhDaiDien;
        RecyclerView rvItemDonHangChiTiet;
        AppCompatButton btItemDonHangXacNhan;
        private ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemDonHangMaDonHang = itemView.findViewById(R.id.tv_admin_item_don_hang_ma_don_hang);
            tvItemDonHangNgayDat = itemView.findViewById(R.id.tv_admin_item_don_hang_ngay_dat);
            tvItemDonHangTenNguoiDung = itemView.findViewById(R.id.tv_admin_item_don_hang_ten_nguoi_dung);
            tvItemDonHangTongTien = itemView.findViewById(R.id.tv_admin_item_don_hang_tong_tien);
            tvItemDonHangTrangThai = itemView.findViewById(R.id.tv_admin_item_don_hang_trang_thai);
            tvItemDonHangXemThemChiTiet = itemView.findViewById(R.id.tv_admin_item_don_hang_xem_them_chi_tiet);
            civItemDonHangAnhDaiDien = itemView.findViewById(R.id.civ_admin_item_don_hang_anh_dai_dien_nguoi_dung);
            rvItemDonHangChiTiet = itemView.findViewById(R.id.rv_admin_item_don_hang_chi_tiet);
            btItemDonHangXacNhan = itemView.findViewById(R.id.bt_admin_item_don_hang_xac_nhan);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onClicḳ(v, getAdapterPosition(), false);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onClicḳ(v, getAdapterPosition(), true);
            }
            return true;
        }
    }
}
