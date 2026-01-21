package com.example.capyshop.user.dathang;

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
import com.example.capyshop.user.giohang.UserGioHang;
import com.example.capyshop.user.giohang.UserGioHangThuocTinh;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Adapter này sử dụng trực tiếp Utils.mangMuaHang để hiển thị danh sách sản phẩm chờ đặt hàng.
 */
public class UserDatHangDanhSachSanPhamAdapter extends RecyclerView.Adapter<UserDatHangDanhSachSanPhamAdapter.MyViewHolder> {
    private Context context;
    private List<UserGioHang> array;

    public UserDatHangDanhSachSanPhamAdapter(Context context, List<UserGioHang> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_dathang_danhsachsanpham_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // LẤY DỮ LIỆU TỪ MẢNG MUA HÀNG THEO VỊ TRÍ (POSITION)
        // Lưu ý: array ở đây chính là Utils.mangMuaHang được truyền từ Activity qua
        UserGioHang sanPham = array.get(position);

        if (sanPham != null) {
            // Đổ tên sản phẩm
            holder.tvTenSanPhamDatHang.setText(sanPham.getTenSanPham());
            holder.tvThuocTinhSanPhamDatHang.setText(hienThiThuocTinh(sanPham));

            // Định dạng giá tiền (Ví dụ: 100.000 đ)
            NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
            String giaFormatted = formatter.format(sanPham.getGiaSanPham()) + " đ";
            holder.tvGiaSanPhamDatHang.setText(giaFormatted);

            // Đổ số lượng
            holder.tvSoLuongSanPhamDatHang.setText("Số lượng: " + sanPham.getSoLuong());

            // Load hình ảnh bằng Glide
            Glide.with(context)
                    .load(sanPham.getHinhAnhSanPham())
                    .placeholder(R.drawable.ic_launcher_background) // Ảnh mặc định khi đang tải
                    .error(R.drawable.ic_launcher_background)       // Ảnh khi lỗi
                    .into(holder.ivHinhAnhSanPhamDatHang);
        }
    }

    private String hienThiThuocTinh(UserGioHang userGioHang) {
        if (userGioHang.getThuocTinh() == null) return "";
        StringBuilder stringBuilder = new StringBuilder();
        for (UserGioHangThuocTinh userGioHangThuocTinh : userGioHang.getThuocTinh()) {
            stringBuilder.append(userGioHangThuocTinh.getTenThuocTinh()).append(": ");
            for (int i = 0; i < userGioHangThuocTinh.getGiaTri().size(); i++) {
                stringBuilder.append(userGioHangThuocTinh.getGiaTri().get(i).getValue());
                if (i < userGioHangThuocTinh.getGiaTri().size() - 1) stringBuilder.append(" ");
            }
            stringBuilder.append(" | ");
        }
        return stringBuilder.toString();
    }

    @Override
    public int getItemCount() {
        // Trả về số lượng phần tử của danh sách mua hàng
        if (array != null) {
            return array.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHinhAnhSanPhamDatHang;
        TextView tvTenSanPhamDatHang, tvSoLuongSanPhamDatHang, tvGiaSanPhamDatHang, tvThuocTinhSanPhamDatHang;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các thành phần giao diện trong item_dat_hang.xml
            ivHinhAnhSanPhamDatHang = itemView.findViewById(R.id.iv_item_hinh_anh_san_pham_dat_hang);
            tvTenSanPhamDatHang = itemView.findViewById(R.id.tv_item_ten_san_pham_dat_hang);
            tvSoLuongSanPhamDatHang = itemView.findViewById(R.id.tv_item_so_luong_san_pham_dat_hang);
            tvGiaSanPhamDatHang = itemView.findViewById(R.id.tv_item_gia_san_pham_dat_hang);
            tvThuocTinhSanPhamDatHang = itemView.findViewById(R.id.tv_item_thuoc_tinh_san_pham_dat_hang);
        }
    }
}