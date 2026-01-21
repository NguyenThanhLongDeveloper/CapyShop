package com.example.capyshop.admin.sanpham;

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
import com.example.capyshop.admin.chitietsanpham.AdminChiTietSanPhamActivity;
import com.example.capyshop.common.Interface.ItemClickListener;
import android.content.Intent;

import com.example.capyshop.common.sanpham.SanPham;
import java.text.DecimalFormat;
import java.util.List;

import com.example.capyshop.common.Interface.ImageClickListener;

public class AdminQuanLySanPhamAdapter extends RecyclerView.Adapter<AdminQuanLySanPhamAdapter.ViewHolder> {

    private Context context;
    private List<SanPham> mangSanPham;
    private ImageClickListener listener;

    public AdminQuanLySanPhamAdapter(Context context, List<SanPham> mangSanPham,
            ImageClickListener listener) {
        this.context = context;
        this.mangSanPham = mangSanPham;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_quanlysanpham_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sanPham = mangSanPham.get(position);

        holder.tvAdminItemSanPhamTen.setText(sanPham.getTenSanPham());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvAdminItemSanPhamGia.setText(decimalFormat.format(sanPham.getGiaSanPham()) + "đ");

        holder.tvAdminItemSanPhamKho.setText("Kho: " + sanPham.getSoLuongTon());

        Glide.with(context)
                .load(sanPham.getHinhAnhSanPham())
                .placeholder(R.drawable.ic_hinh_anh)
                .error(R.drawable.ic_hinh_anh)
                .into(holder.ivAdminItemSanPhamHinhAnh);

        holder.ivAdminItemSanPhamSua.setOnClickListener(v -> {
            if (listener != null)
                listener.onImageClick(v, position, 0); // 0: normal click
        });

        holder.ivAdminItemSanPhamXoa.setOnClickListener(v -> {
            if (listener != null)
                listener.onImageClick(v, position, 1); // 1: value for delete? Or just rely on View ID?
            // Suggest passing View so Activity can check ID. 'val' is commonly used for
            // specialized flags.
            // Keeping 0 for now as ImageClickListener signature implies 'isLongClick'.
            // Recalling user said "dùng chung cho việc click image".
            // Let's pass 0, distinguishing by View ID is standard.
        });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClicḳ(View view, int position, boolean isLongClick) {
                if (!isLongClick) {
                    Intent intent = new Intent(context, AdminChiTietSanPhamActivity.class);
                    intent.putExtra("maSanPham", sanPham.getMaSanPham());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mangSanPham != null ? mangSanPham.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        ImageView ivAdminItemSanPhamHinhAnh, ivAdminItemSanPhamSua, ivAdminItemSanPhamXoa;
        TextView tvAdminItemSanPhamTen, tvAdminItemSanPhamGia, tvAdminItemSanPhamKho;
        private ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAdminItemSanPhamHinhAnh = itemView.findViewById(R.id.iv_admin_item_san_pham_hinh_anh);
            ivAdminItemSanPhamSua = itemView.findViewById(R.id.iv_admin_item_san_pham_sua);
            ivAdminItemSanPhamXoa = itemView.findViewById(R.id.iv_admin_item_san_pham_xoa);
            tvAdminItemSanPhamTen = itemView.findViewById(R.id.tv_admin_item_san_pham_ten);
            tvAdminItemSanPhamGia = itemView.findViewById(R.id.tv_admin_item_san_pham_gia);
            tvAdminItemSanPhamKho = itemView.findViewById(R.id.tv_admin_item_san_pham_kho);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClicḳ(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClicḳ(v, getAdapterPosition(), true);
            return true;
        }
    }
}
