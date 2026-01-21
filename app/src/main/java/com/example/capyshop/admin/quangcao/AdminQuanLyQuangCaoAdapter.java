package com.example.capyshop.admin.quangcao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capyshop.R;

import com.example.capyshop.common.quangcao.QuangCao;
import java.util.List;

import com.example.capyshop.common.Interface.ImageClickListener;

public class AdminQuanLyQuangCaoAdapter extends RecyclerView.Adapter<AdminQuanLyQuangCaoAdapter.ViewHolder> {

    private Context context;
    private List<QuangCao> mangAdminQuangCao;
    private ImageClickListener listener;

    public AdminQuanLyQuangCaoAdapter(Context context, List<QuangCao> mangAdminQuangCao,
            ImageClickListener listener) {
        this.context = context;
        this.mangAdminQuangCao = mangAdminQuangCao;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_quanlyquangcao_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuangCao adminQuangCao = mangAdminQuangCao.get(position);
        Glide.with(context)
                .load(adminQuangCao.getHinhAnhQuangCao())
                .placeholder(R.drawable.ic_hinh_anh) // Assuming this exists from danhmuc adapter
                .into(holder.ivAdminItemQuangCaoHinhAnh);

        holder.ivAdminItemQuangCaoXoa.setOnClickListener(v -> {
            if (listener != null) {
                listener.onImageClick(v, position, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mangAdminQuangCao != null ? mangAdminQuangCao.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAdminItemQuangCaoHinhAnh, ivAdminItemQuangCaoXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAdminItemQuangCaoHinhAnh = itemView.findViewById(R.id.iv_admin_item_quang_cao_hinh_anh);
            ivAdminItemQuangCaoXoa = itemView.findViewById(R.id.iv_admin_item_quang_cao_xoa);
        }
    }
}
