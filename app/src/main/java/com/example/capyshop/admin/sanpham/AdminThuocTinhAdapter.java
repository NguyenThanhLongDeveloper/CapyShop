package com.example.capyshop.admin.sanpham;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capyshop.R;
import com.example.capyshop.common.chitietsanpham.ChiTietSanPhamGiaTriThuocTinh;
import com.example.capyshop.common.chitietsanpham.ChiTietSanPhamThuocTinh;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class AdminThuocTinhAdapter extends RecyclerView.Adapter<AdminThuocTinhAdapter.MyViewHolder> {

    Context context;
    List<ChiTietSanPhamThuocTinh> list;

    public AdminThuocTinhAdapter(Context context, List<ChiTietSanPhamThuocTinh> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_quanlysanpham_themsanpham_item,
                parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChiTietSanPhamThuocTinh thuocTinh = list.get(position);
        holder.tvTenThuocTinh.setText(thuocTinh.getTenThuocTinh());

        // Remove listener before setting text to avoid infinite loops or unwanted triggers
        if (holder.textWatcher != null) {
            holder.etGiaTriThuocTinh.removeTextChangedListener(holder.textWatcher);
        }

        // Set value
        if (thuocTinh.getGiaTri() != null && !thuocTinh.getGiaTri().isEmpty()) {
            List<String> valuesStr = new ArrayList<>();
            for (ChiTietSanPhamGiaTriThuocTinh gv : thuocTinh.getGiaTri()) {
                valuesStr.add(gv.getGiaTri());
            }
            holder.etGiaTriThuocTinh.setText(TextUtils.join(", ", valuesStr));
        } else {
            holder.etGiaTriThuocTinh.setText("");
        }

        // Add new listener to update model
        holder.textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strGiaTri = s.toString().trim();
                List<ChiTietSanPhamGiaTriThuocTinh> listVal = new ArrayList<>();
                if (!TextUtils.isEmpty(strGiaTri)) {
                    String[] values = strGiaTri.split(",");
                    for (String val : values) {
                        ChiTietSanPhamGiaTriThuocTinh gv = new ChiTietSanPhamGiaTriThuocTinh();
                        gv.setGiaTri(val.trim());
                        listVal.add(gv);
                    }
                }
                thuocTinh.setGiaTri(listVal);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        holder.etGiaTriThuocTinh.addTextChangedListener(holder.textWatcher);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenThuocTinh;
        TextInputEditText etGiaTriThuocTinh;
        TextWatcher textWatcher;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenThuocTinh = itemView.findViewById(R.id.tv_admin_item_them_san_pham_thuoc_tinh_ten);
            etGiaTriThuocTinh = itemView.findViewById(R.id.et_admin_item_them_san_pham_thuoc_tinh_gia_tri);
        }
    }
}
