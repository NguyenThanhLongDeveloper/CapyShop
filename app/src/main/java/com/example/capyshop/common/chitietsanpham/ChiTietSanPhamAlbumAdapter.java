package com.example.capyshop.common.chitietsanpham;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capyshop.common.activity.XemHinhAnhFullActivity;

import java.util.List;

public class ChiTietSanPhamAlbumAdapter extends RecyclerView.Adapter<ChiTietSanPhamAlbumAdapter.MyViewHolder> {
    Context context;
    List<ChiTietSanPhamAlbum> array;

    public ChiTietSanPhamAlbumAdapter(Context context, List<ChiTietSanPhamAlbum> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new MyViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChiTietSanPhamAlbum albumItem = array.get(position);
        Glide.with(context).load(albumItem.getHinhAnh()).into((ImageView) holder.itemView);

        // Bắt sự kiện click để phóng to
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, XemHinhAnhFullActivity.class);
            intent.putExtra("hinhanh", albumItem.getHinhAnh());
            ActivityOptions options = ActivityOptions.makeCustomAnimation(context, android.R.anim.fade_in, android.R.anim.fade_out);
            
            // Thêm Flag nếu context không phải từ Activity
            if (!(context instanceof android.app.Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else {
                context.startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return array != null ? array.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}