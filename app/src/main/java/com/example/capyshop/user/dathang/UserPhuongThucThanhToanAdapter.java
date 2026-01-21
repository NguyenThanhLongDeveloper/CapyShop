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

import com.example.capyshop.common.phuongthucthanhtoan.PhuongThucThanhToan;
import java.util.List;

public class UserPhuongThucThanhToanAdapter extends RecyclerView.Adapter<UserPhuongThucThanhToanAdapter.MyViewHolder> {

    private Context context;
    private List<PhuongThucThanhToan> array;
    private int daChon = -1;

    public UserPhuongThucThanhToanAdapter(Context context, List<PhuongThucThanhToan> array, int daChon) {
        this.context = context;
        this.array = array;
        this.daChon = daChon;
    }

    @NonNull
    @Override
    public UserPhuongThucThanhToanAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.user_dathang_phuongthucthanhtoan_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserPhuongThucThanhToanAdapter.MyViewHolder holder, int position) {
        PhuongThucThanhToan userPhuongThucThanhToan = array.get(position);

        holder.tvItemPhuongThucThanhToanDatHang.setText(userPhuongThucThanhToan.getTenPhuongThucThanhToan());
        holder.rbItemPhuongThucThanhToanDatHang.setChecked(position == daChon);

        holder.itemView.setOnClickListener(v -> {
            int old = daChon;
            daChon = holder.getAdapterPosition();

            if (old != -1) notifyItemChanged(old);
            notifyItemChanged(daChon);
        });


    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public int getMaPhuongThucThanhToanDangChon() {
        if (daChon != -1) {
            return array.get(daChon).getMaPhuongThucThanhToan();
        }
        return -1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemPhuongThucThanhToanDatHang;
        RadioButton rbItemPhuongThucThanhToanDatHang;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemPhuongThucThanhToanDatHang = itemView.findViewById(R.id.tv_item_phuong_thuc_thanh_toan_dat_hang);
            rbItemPhuongThucThanhToanDatHang = itemView.findViewById(R.id.rb_item_phuong_thuc_thanh_toan_dat_hang);
        }
    }
}
