package com.example.capyshop.user.dathang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.capyshop.R;
import com.example.capyshop.user.donhang.UserDonHangActivity;
import com.example.capyshop.user.main.UserMainActivity;

public class UserDatHangThanhCongActivity extends AppCompatActivity {
    // Khai báo các biến giao diện (Views)
    AppCompatButton btTiepTucMuaSam, btXemChiTietDonHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.user_dathang_thanhcong_activity);
        super.onCreate(savedInstanceState);
        anhXa();
        xuLySuKienClick();
    }

    private void anhXa() {
        btTiepTucMuaSam = findViewById(R.id.bt_tiep_tuc_mua_sam);
        btXemChiTietDonHang = findViewById(R.id.bt_xem_chi_tiet_don_hang);

    }



    private void xuLySuKienClick() {
        btTiepTucMuaSam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        btXemChiTietDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyen den man hinh lich su don hang
                String trangThai = "CHO_XAC_NHAN";
                Intent intent = new Intent(getApplicationContext(), UserDonHangActivity.class);
                intent.putExtra("trangthai", trangThai);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}

