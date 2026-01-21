package com.example.capyshop.user.dathang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capyshop.R;

import com.example.capyshop.common.donvivanchuyen.DonViVanChuyen;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Adapter hiển thị danh sách ĐƠN VỊ VẬN CHUYỂN trong màn hình Đặt hàng
 */
public class UserDonViVanChuyenAdapter
        extends RecyclerView.Adapter<UserDonViVanChuyenAdapter.MyViewHolder> {

    private Context context;
    private List<DonViVanChuyen> array;
    private int daChon = -1;

    public UserDonViVanChuyenAdapter(Context context, List<DonViVanChuyen> array, int daChon) {
        this.context = context;
        this.array = array;
        this.daChon = daChon;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.user_dathang_donvivanchuyen_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DonViVanChuyen userDonViVanChuyen = array.get(position);

        holder.tvItemTenDonViVanChuyenDatHang.setText(userDonViVanChuyen.getTenDonViVanChuyen());

        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        String gia = formatter.format(userDonViVanChuyen.getGiaDonViVanChuyen()) + " đ";
        holder.tvItemGiaDonViVanChuyenDatHang.setText(gia);

        holder.rbItemDonViVanChuyenDatHang.setChecked(position == daChon);

        holder.itemView.setOnClickListener(v -> {
            int old = daChon;
            daChon = holder.getAdapterPosition();

            if (old != -1) notifyItemChanged(old);
            notifyItemChanged(daChon);

            // Cập nhật giá vận chuyển sang Activity
            if (context instanceof UserDatHangActivity) {
                UserDatHangActivity activity = (UserDatHangActivity) context;
                activity.giaDonViVanChuyen(userDonViVanChuyen.getGiaDonViVanChuyen());
                activity.capNhatTongThanhToan();
            }
        });


    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    /**
     * Lấy đơn vị vận chuyển đang được chọn
     */
    public DonViVanChuyen getDonViVanChuyenDangChon() {
        if (daChon != -1) {
            return array.get(daChon);
        }
        return null;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemTenDonViVanChuyenDatHang, tvItemGiaDonViVanChuyenDatHang;
        RadioButton rbItemDonViVanChuyenDatHang;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemTenDonViVanChuyenDatHang = itemView.findViewById(R.id.tv_item_ten_don_vi_van_chuyen_dat_hang);
            tvItemGiaDonViVanChuyenDatHang = itemView.findViewById(R.id.tv_item_gia_don_vi_van_chuyen_dat_hang);
            rbItemDonViVanChuyenDatHang = itemView.findViewById(R.id.rb_item_don_vi_van_chuyen_dat_hang);
        }
    }
}
