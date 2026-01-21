package com.example.capyshop.common.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.example.capyshop.R;

import io.paperdb.Paper;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Khởi tạo PaperDB
        Paper.init(this);
        super.onCreate(savedInstanceState);

        // Kích hoạt chế độ EdgeToEdge
        EdgeToEdge.enable(this);
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                // v.setPadding(systemBars.left, systemBars.top, systemBars.right,
                // systemBars.bottom);
                return insets;
            });
        }

        thietLapMauThanhHeThong();
    }

    // Tinh chỉnh màu sắc và biểu tượng của thanh hệ thống.
    public void thietLapMauThanhHeThong() {
        // Lấy đối tượng Window để quản lý các thành phần giao diện cấp hệ thống
        Window window = getWindow();

        // Thiết lập màu nền trong suốt cho thanh trạng thái và thanh điều hướng
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);

        // Lấy DecorView và WindowInsetsController để điều khiển các biểu tượng (icons)
        // trên thanh hệ thống
        View view = window.getDecorView();
        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(window, view);

        if (controller != null) {
            // chuyển các biểu tượng trên status bar sang màu tối
            controller.setAppearanceLightStatusBars(true);

            // chuyển các biểu tượng duói navigation bar sang màu tối
            controller.setAppearanceLightNavigationBars(true);
        }
    }
}