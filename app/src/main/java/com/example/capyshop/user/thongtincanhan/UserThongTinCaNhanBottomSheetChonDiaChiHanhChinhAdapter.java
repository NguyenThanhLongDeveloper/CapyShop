package com.example.capyshop.user.thongtincanhan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capyshop.R;

import java.util.List;

public class UserThongTinCaNhanBottomSheetChonDiaChiHanhChinhAdapter extends RecyclerView.Adapter<UserThongTinCaNhanBottomSheetChonDiaChiHanhChinhAdapter.MyViewHolder> {
    private List<String> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(String name);
    }

    public UserThongTinCaNhanBottomSheetChonDiaChiHanhChinhAdapter(List<String> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Sử dụng layout mặc định của hệ thống để nhanh và gọn
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_thongtincanhan_bottomsheet_chondiachihanhchinh_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name = list.get(position);
        holder.tvItemBottomSheetChonDiaChiThongTinCaNhan.setText(name);
        holder.itemView.setOnClickListener(v -> listener.onClick(name));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemBottomSheetChonDiaChiThongTinCaNhan;
        MyViewHolder(View v) {
            super(v);
            tvItemBottomSheetChonDiaChiThongTinCaNhan = itemView.findViewById(R.id.tv_item_ten_bottom_sheet_chon_dia_chi_thong_tin_ca_nhan);
        }
    }
}