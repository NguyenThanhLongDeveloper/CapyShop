package com.example.capyshop.user.sanpham;

import com.example.capyshop.common.sanpham.SanPham;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capyshop.common.Interface.ItemClickListener;
import com.example.capyshop.R;
import com.example.capyshop.user.chitietsanpham.UserChiTietSanPhamActivity;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

// Adapter cho RecyclerView hiển thị danh sách sản phẩm, có hỗ trợ loading
public class UserSanPhamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<SanPham> array;
    private static final int VIEW_TYPE_DATA = 0; // Loại view cho dữ liệu sản phẩm
    private static final int VIEW_TYPE_LOADING = 1; // Loại view cho trạng thái loading

    public UserSanPhamAdapter(Context context, List<SanPham> array) {
        this.context = context;
        this.array = array;
    }

    // Tạo ViewHolder dựa trên loại view
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DATA) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_sanpham_item, parent, false);
            return new MyViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_sanpham_loading_item, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    // Gán dữ liệu cho ViewHolder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            SanPham userSanPham = array.get(position);

            // Gán dữ liệu cho các View của sản phẩm
            myViewHolder.tvItemTenSanPham.setText(userSanPham.getTenSanPham());
            NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
            String giaFormatted = formatter.format(userSanPham.getGiaSanPham()) + " đ";
            myViewHolder.tvItemGiaSanPham.setText(giaFormatted);
            myViewHolder.tvItemMoTaSanPham.setText(userSanPham.getMoTaSanPham());
            Glide.with(context).load(userSanPham.getHinhAnhSanPham()).into(myViewHolder.ivItemHinhAnhSanPham);

            // Xử lý sự kiện click để mở màn hình chi tiết sản phẩm
            myViewHolder.setItemClickListener((view, pos, isLongClick) -> {
                if (!isLongClick) {
                    Intent intent = new Intent(context, UserChiTietSanPhamActivity.class);
                    // CHỈ TRUYỀN ID
                    intent.putExtra("maSanPham", array.get(position).getMaSanPham());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        } else {
            // Hiển thị loading spinner cho item loading
            ((LoadingViewHolder) holder).progressBarItemLoading.setIndeterminate(true);
        }
    }

    // Xác định loại view (sản phẩm hay loading)
    @Override
    public int getItemViewType(int position) {
        return array.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_DATA;
    }

    // Trả về tổng số item
    @Override
    public int getItemCount() {
        return array.size();
    }

    // ViewHolder cho item loading
    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBarItemLoading;
        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBarItemLoading = itemView.findViewById(R.id.progressBarItemLoading);
        }
    }

    // ViewHolder cho item sản phẩm
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvItemTenSanPham, tvItemGiaSanPham, tvItemMoTaSanPham;
        ImageView ivItemHinhAnhSanPham;
        private ItemClickListener itemClickListener;

        public MyViewHolder(View itemView) {
            super(itemView);
            // Ánh xạ các View
            tvItemTenSanPham = itemView.findViewById(R.id.tv_item_ten_san_pham);
            tvItemGiaSanPham = itemView.findViewById(R.id.tv_item_gia_san_pham);
            tvItemMoTaSanPham = itemView.findViewById(R.id.tv_item_mo_ta_san_pham);
            ivItemHinhAnhSanPham = itemView.findViewById(R.id.iv_item_hinh_anh_san_pham);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            // Gọi interface để xử lý click
            itemClickListener.onClicḳ(view, getAdapterPosition(), false);
        }
    }
}