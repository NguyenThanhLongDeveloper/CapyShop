package com.example.capyshop.common.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.capyshop.R;
import com.github.chrisbanes.photoview.PhotoView;

public class XemHinhAnhFullActivity extends BaseActivity {
    //Thư viện mở rộng của ImageView hỗ trợ zoom đa điểm
    PhotoView pvXemHinhAnhFull;
    // Nút đóng màn hình
    ImageView ivXemHinhAnhFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.common_xemhinhanhfull_activity);
        super.onCreate(savedInstanceState);

        anhXa();
        hienThiHinhAnh();
    }

    //Ánh xạ các thành phần giao diện từ file XML
    private void anhXa() {
        pvXemHinhAnhFull = findViewById(R.id.pv_xem_hinh_anh_full);
        ivXemHinhAnhFull = findViewById(R.id.iv_xem_hinh_anh_full);
    }

    //Nhận dữ liệu đường dẫn ảnh từ Intent và hiển thị lên màn hình
    private void hienThiHinhAnh() {
        // Lấy chuỗi URL hoặc đường dẫn ảnh được truyền từ Activity trước đó
        String hinhAnh = getIntent().getStringExtra("hinhanh");

        // Sử dụng thư viện Glide để tải ảnh
        Glide.with(this)
                .load(hinhAnh)
                .placeholder(R.drawable.ic_hinh_anh)
                .into(pvXemHinhAnhFull);


        // Khi người dùng nhấn vào sẽ đóng lại và quay về màn hình trước đó
        ivXemHinhAnhFull.setOnClickListener(v -> finish());
    }
}