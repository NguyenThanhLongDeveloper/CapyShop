package com.example.capyshop.user.donhang;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capyshop.R;
import com.example.capyshop.common.activity.BaseActivity;
import com.example.capyshop.common.retrofit.ApiUser;
import com.example.capyshop.common.retrofit.RetrofitClient;
import com.example.capyshop.common.utils.Utils;
import com.google.android.material.tabs.TabLayout;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserDonHangActivity extends BaseActivity {
    Toolbar tbDonHang;
    TabLayout tlDonHang;
    RecyclerView rvDonHang;
    TextView tvThongBaoDonHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiUser apiUser;
    String trangThai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.user_donhang_activity);
        super.onCreate(savedInstanceState);
        anhXa();
        caiDatToolbar();
        xuLySuKienClickXemDonHang();
        hienThiDanhSachDonHang(trangThai);

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    private void anhXa() {
        tbDonHang = findViewById(R.id.tb_don_hang);
        tlDonHang = findViewById(R.id.tl_don_hang);
        rvDonHang = findViewById(R.id.rv_don_hang);
        tvThongBaoDonHang = findViewById(R.id.tv_thong_bao_gio_hang);
        // Khởi tạo Retrofit service
        apiUser = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiUser.class);
        // Cấu hình RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvDonHang.setLayoutManager(linearLayoutManager);
        // Cài đặt kích thước cố định cho RecyclerView
        rvDonHang.setHasFixedSize(true);
        // Lấy trạng thái đơn hàng từ Intent
        trangThai = getIntent().getStringExtra("trangthai");

    }

    private void caiDatToolbar() {
        setSupportActionBar(tbDonHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        tbDonHang.setNavigationOnClickListener(v -> finish());

    }

    private void xuLySuKienClickXemDonHang() {
        tlDonHang.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String trangThai = "";

                switch (tab.getPosition()) {
                    case 0:
                        trangThai = "CHO_XAC_NHAN";
                        hienThiDanhSachDonHang(trangThai);
                        break;
                    case 1:
                        trangThai = "CHO_LAY_HANG";
                        hienThiDanhSachDonHang(trangThai);
                        break;
                    case 2:
                        trangThai = "DANG_GIAO_HANG";
                        hienThiDanhSachDonHang(trangThai);
                        break;
                    case 3:
                        trangThai = "DA_GIAO_HANG";
                        hienThiDanhSachDonHang(trangThai);
                        break;
                    case 4:
                        trangThai = "DA_HUY";
                        hienThiDanhSachDonHang(trangThai);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void hienThiDanhSachDonHang(String trangThai) {
        if (trangThai != null) {
            int viTri = 0;
            switch (trangThai) {
                case "CHO_XAC_NHAN": viTri = 0; break;
                case "CHO_LAY_HANG": viTri = 1; break;
                case "DANG_GIAO_HANG": viTri = 2; break;
                case "DA_GIAO_HANG": viTri = 3; break;
                case "DA_HUY": viTri = 4; break;
            }
            tlDonHang.getTabAt(viTri).select();
        }
        // Thực hiện lệnh gọi API để lấy danh sách đơn hàng
        compositeDisposable.add(apiUser.layDonHang(Utils.userNguoiDung_Current.getMaNguoiDung(), trangThai)
                .subscribeOn(Schedulers.io()) // Thực hiện trên luồng I/O (mạng)
                .observeOn(AndroidSchedulers.mainThread()) // Quan sát kết quả trên luồng chính (UI)
                .subscribe(
                        donHangModel -> {
                            // Kiểm tra nếu thành công và có dữ liệu
                            if (donHangModel.isSuccess() && donHangModel.getResult() != null && !donHangModel.getResult().isEmpty()) {
                                // CÓ DỮ LIỆU: Hiện danh sách, ẩn thông báo
                                tvThongBaoDonHang.setVisibility(View.GONE);
                                rvDonHang.setVisibility(View.VISIBLE);

                                UserDonHangAdapter userDonHangAdapter = new UserDonHangAdapter(getApplicationContext(), donHangModel.getResult());
                                rvDonHang.setAdapter(userDonHangAdapter);
                            } else {
                                // TRỐNG HOẶC LỖI: Ẩn danh sách, hiện thông báo
                                rvDonHang.setVisibility(View.GONE);
                                tvThongBaoDonHang.setVisibility(View.VISIBLE);
                                tvThongBaoDonHang.setText("Bạn chưa có đơn hàng nào");
                            }

                        }
                        , throwable -> {
                            Toast.makeText(getApplicationContext(), "Lỗi kết nối: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ));
    }
}