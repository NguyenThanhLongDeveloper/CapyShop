package com.example.capyshop.admin.donhang;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capyshop.R;
import com.example.capyshop.common.activity.BaseActivity;
import com.example.capyshop.common.retrofit.ApiAdmin;
import com.example.capyshop.common.retrofit.RetrofitClient;
import com.example.capyshop.common.utils.Utils;
import com.google.android.material.tabs.TabLayout;

import com.example.capyshop.common.donhang.DonHang;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdminQuanLyDonHangActivity extends BaseActivity {

    Toolbar tbQuanLyDonHang;
    TabLayout tlThanhDieuHuong;
    RecyclerView rvDonHang;
    ProgressBar pbDonHang;
    TextView tvThongBaoTrong;

    ApiAdmin apiAdmin;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    List<DonHang> mangDonHang = new ArrayList<>();
    AdminQuanLyDonHangAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.admin_quanlydonhang_activity);
        super.onCreate(savedInstanceState);

        anhXa();
        caiDatControl();
        layDanhSachDonHang("CHO_XAC_NHAN"); // Load initial state
    }

    private void anhXa() {
        tbQuanLyDonHang = findViewById(R.id.tb_admin_quan_ly_don_hang);
        tlThanhDieuHuong = findViewById(R.id.tl_admin_quan_ly_don_hang_bo_loc);
        rvDonHang = findViewById(R.id.rv_admin_quan_ly_don_hang);
        pbDonHang = findViewById(R.id.pb_admin_quan_ly_don_hang);
        tvThongBaoTrong = findViewById(R.id.tv_admin_quan_ly_don_hang_thong_bao);

        apiAdmin = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiAdmin.class);

        rvDonHang.setHasFixedSize(true);
        rvDonHang.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AdminQuanLyDonHangAdapter(getApplicationContext(), mangDonHang, this::xacNhanDonHang);
        rvDonHang.setAdapter(adapter);
    }

    private void caiDatControl() {
        setSupportActionBar(tbQuanLyDonHang);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }
        tbQuanLyDonHang.setNavigationOnClickListener(v -> finish());

        tlThanhDieuHuong.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String status = "";
                switch (tab.getPosition()) {
                    case 0:
                        status = "CHO_XAC_NHAN";
                        break;
                    case 1:
                        status = "CHO_LAY_HANG";
                        break;
                    case 2:
                        status = "DANG_GIAO_HANG";
                        break;
                    case 3:
                        status = "DA_GIAO_HANG";
                        break;
                    case 4:
                        status = "DA_HUY";
                        break;
                }
                layDanhSachDonHang(status);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void layDanhSachDonHang(String status) {
        pbDonHang.setVisibility(View.VISIBLE);
        rvDonHang.setVisibility(View.GONE);
        tvThongBaoTrong.setVisibility(View.GONE);

        compositeDisposable.add(apiAdmin.layDanhSachDonHang(status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            pbDonHang.setVisibility(View.GONE);
                            if (model.isSuccess() && model.getResult() != null && !model.getResult().isEmpty()) {
                                mangDonHang.clear();
                                mangDonHang.addAll(model.getResult());
                                adapter.notifyDataSetChanged();
                                rvDonHang.setVisibility(View.VISIBLE);
                            } else {
                                mangDonHang.clear();
                                adapter.notifyDataSetChanged();
                                tvThongBaoTrong.setVisibility(View.VISIBLE);
                                tvThongBaoTrong.setText("Không có đơn hàng nào");
                            }
                        },
                        throwable -> {
                            pbDonHang.setVisibility(View.GONE);
                            Toast.makeText(this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                        }));
    }

    private void xacNhanDonHang(DonHang donHang) {
        String currentStatus = donHang.getTrangThai();
        String nextStatus = "";

        switch (currentStatus) {
            case "CHO_XAC_NHAN":
                nextStatus = "CHO_LAY_HANG";
                break;
            case "CHO_LAY_HANG":
                nextStatus = "DANG_GIAO_HANG";
                break;
            case "DANG_GIAO_HANG":
                nextStatus = "DA_GIAO_HANG";
                break;
            default:
                return; // No action for max status
        }

        final String finalNextStatus = nextStatus; // For lambda

        compositeDisposable.add(apiAdmin.updateTrangThaiDonHang(donHang.getMaDonHang(), nextStatus)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            if (model.isSuccess()) {
                                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                // Refresh current list (which removes the item since it changed status)
                                // Or we could just update the item if we weren't filtering strictly

                                // Since logic is tab-based filtering, item should disappear
                                mangDonHang.remove(donHang);
                                adapter.notifyDataSetChanged();

                                if (mangDonHang.isEmpty()) {
                                    tvThongBaoTrong.setVisibility(View.VISIBLE);
                                }
                            } else {
                                Toast.makeText(this, "Lỗi: " + model.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> Toast.makeText(this, "Lỗi kết nối", Toast.LENGTH_SHORT).show()));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
