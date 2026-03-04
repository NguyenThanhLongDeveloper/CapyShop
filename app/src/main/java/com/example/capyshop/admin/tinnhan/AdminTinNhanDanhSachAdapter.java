package com.example.capyshop.admin.tinnhan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capyshop.R;
import com.example.capyshop.common.Interface.ItemClickListener;
import com.example.capyshop.common.nguoidung.NguoiDung;
import com.example.capyshop.common.utils.Utils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminTinNhanDanhSachAdapter extends RecyclerView.Adapter<AdminTinNhanDanhSachAdapter.MyViewHolder> {
    Context context;
    List<NguoiDung> array;

    public AdminTinNhanDanhSachAdapter(Context context, List<NguoiDung> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_tin_nhan_danh_sach_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvItemHoTenNguoiDungTinNhan.setText(array.get(position).getHoTenNguoiDung());
        Glide.with(context).load(array.get(position).getHinhAnhNguoiDung()).into(holder.civItemHinhAnhNguoiDungTinNhan);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClicḳ(View view, int position, boolean isLongClick) {
                if (!isLongClick) {
                    Intent intent = new Intent(context, AdminTinNhanActivity.class);
                    Utils.maNguoiNhanTinNhan = String.valueOf(array.get(position).getMaNguoiDung());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView civItemHinhAnhNguoiDungTinNhan;
        TextView tvItemHoTenNguoiDungTinNhan;
        TextView tvItemTinNhanMoiNguoiDung;
        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            civItemHinhAnhNguoiDungTinNhan = itemView.findViewById(R.id.civ_admin_item_hinh_anh_nguoi_dung_tin_nhan);
            tvItemHoTenNguoiDungTinNhan = itemView.findViewById(R.id.tv_admin_item_ho_ten_nguoi_dung_tin_nhan);
            tvItemTinNhanMoiNguoiDung = itemView.findViewById(R.id.tv_admin_item_tin_nhan_moi_nguoi_dung);
            //
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClicḳ(v, getAdapterPosition(), false);
        }
    }
}
