package com.example.capyshop.user.donhang;

import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capyshop.R;

import com.example.capyshop.common.chitietdonhang.ChiTietDonHang;
import com.example.capyshop.common.donhang.DonHang;
import com.example.capyshop.user.chitietdonhang.UserChiTietDonHangAdapter;

import java.text.NumberFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class UserDonHangAdapter extends RecyclerView.Adapter<UserDonHangAdapter.MyViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    // Khai báo Set để lưu trữ ID (String) của các đơn hàng đang ở trạng thái mở rộng
    private final Set<String> expandedOrders = new HashSet<>();
    Context context;
    List<DonHang> array;

    public UserDonHangAdapter(Context context, List<DonHang> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_donhang_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DonHang userDonHang = array.get(position);
        holder.tvItemMaDonHang.setText(" " + userDonHang.getMaDonHang());
        String trangThai = userDonHang.getTrangThai();
        if (trangThai.equals("CHO_XAC_NHAN")) {
            holder.tvItemTrangThaiDonHang.setText("Chờ xác nhận");
        }else if (trangThai.equals("CHO_LAY_HANG")) {
            holder.tvItemTrangThaiDonHang.setText("Chờ lấy hàng");
        }else if (trangThai.equals("DANG_GIAO_HANG")) {
            holder.tvItemTrangThaiDonHang.setText("Đang giao hàng");
        }else if (trangThai.equals("DA_GIAO_HANG")) {
            holder.tvItemTrangThaiDonHang.setText("Đã giao hàng");
        }else if (trangThai.equals("DA_HUY")) {
            holder.tvItemTrangThaiDonHang.setText("Đã huỷ");
        }

        holder.tvItemTongSoLuongSanPhamDonHang.setText("(" + userDonHang.getTongSoLuong() + " sản phẩm) :");

        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String tongTienNumberFormat = numberFormat.format(userDonHang.getTongTien()) + " đ";
        holder.tvItemTongTienSanPhamDonHang.setText(tongTienNumberFormat);

        // Ép kiểu chiTietList về kiểu dữ liệu chính xác (ChiTietDonHang)
        @SuppressWarnings("unchecked")
        List<ChiTietDonHang> chiTietList = (List<ChiTietDonHang>) userDonHang.getChitietdonhang();

        final String orderId = String.valueOf(userDonHang.getMaDonHang());
        final boolean isExpanded = expandedOrders.contains(orderId);

        List<ChiTietDonHang> displayList;

        // Logic hiển thị:
        if (isExpanded || chiTietList.size() <= 1) {
            // Trạng thái mở rộng HOẶC chỉ có 0/1 sản phẩm: hiển thị tất cả
            displayList = chiTietList;
            holder.tvItemMoRongDonHang.setText("Thu gọn");
            // Ẩn nút nếu chỉ có 0 hoặc 1 sản phẩm
            holder.tvItemMoRongDonHang.setVisibility(chiTietList.size() > 1 ? View.VISIBLE : View.GONE);
        } else {

            @SuppressWarnings("unchecked")
            List<ChiTietDonHang> subList = (List<ChiTietDonHang>) chiTietList.subList(0, 1);
            displayList = subList;
            holder.tvItemMoRongDonHang.setText("Xem thêm (" + chiTietList.size() + ")");
            // Hiển thị nút nếu có nhiều hơn 1 sản phẩm
            holder.tvItemMoRongDonHang.setVisibility(View.VISIBLE);
        }

        // --- Thiết lập RecyclerView bên trong ---
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                holder.rvItemDanhSachSanPhamDonHang.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );

        linearLayoutManager.setInitialPrefetchItemCount(displayList.size());

        // Truyền displayList (đã có kiểu ChiTietDonHang chính xác) vào Adapter chi tiết
        UserChiTietDonHangAdapter userChiTietDonHangAdapter = new UserChiTietDonHangAdapter(context, displayList);

        holder.rvItemDanhSachSanPhamDonHang.setLayoutManager(linearLayoutManager);
        holder.rvItemDanhSachSanPhamDonHang.setAdapter(userChiTietDonHangAdapter);
        holder.rvItemDanhSachSanPhamDonHang.setRecycledViewPool(viewPool);

        // --- Xử lý sự kiện nút Xem thêm/Thu gọn (Đã thêm hiệu ứng trượt) ---
        holder.tvItemMoRongDonHang.setOnClickListener(v -> {
            // 1. Bắt đầu TransitionManager trên RecyclerView cha (hoặc container)
            ViewGroup parent = (ViewGroup) holder.itemView.getParent();
            if (parent != null) {
                // Dùng AutoTransition để tự động tính toán và tạo hiệu ứng trượt (slide) cho sự thay đổi kích thước
                TransitionManager.beginDelayedTransition(parent, new AutoTransition());
            }

            // 2. Thay đổi trạng thái
            if (isExpanded) {
                expandedOrders.remove(orderId); // Thu gọn: Xóa ID khỏi Set
            } else {
                expandedOrders.add(orderId); // Mở rộng: Thêm ID vào Set
            }
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    // Đổi thành public static class MyViewHolder để tránh Memory Leak
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemMaDonHang, tvItemTrangThaiDonHang, tvItemMoRongDonHang,
                tvItemTongSoLuongSanPhamDonHang, tvItemTongTienSanPhamDonHang;
        RecyclerView rvItemDanhSachSanPhamDonHang;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemMaDonHang = itemView.findViewById(R.id.tv_item_ma_don_hang);
            tvItemTrangThaiDonHang = itemView.findViewById(R.id.tv_item_trang_thai_don_hang);
            rvItemDanhSachSanPhamDonHang = itemView.findViewById(R.id.rv_item_danh_sach_san_pham_don_hang);
            tvItemMoRongDonHang = itemView.findViewById(R.id.tv_item_mo_rong_don_hang);
            tvItemTongSoLuongSanPhamDonHang = itemView.findViewById(R.id.tv_item_tong_so_luong_san_pham_don_hang);
            tvItemTongTienSanPhamDonHang = itemView.findViewById(R.id.tv_item_tong_tien_san_pham_don_hang);

        }
    }
}