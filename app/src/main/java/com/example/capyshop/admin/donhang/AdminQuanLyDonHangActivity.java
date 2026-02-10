package com.example.capyshop.admin.donhang;


import android.os.Bundle;
import android.util.Log;
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
import com.example.capyshop.common.retrofit.ApiCommon;
import com.example.capyshop.common.retrofit.Authorization;
import com.example.capyshop.common.retrofit.RetrofitClient;
import com.example.capyshop.common.retrofit.RetrofitClientThongBao;
import com.example.capyshop.common.thongbao.GuiThongBao;
import com.example.capyshop.common.thongbao.Message;
import com.example.capyshop.common.thongbao.Notification;
import com.example.capyshop.common.utils.Utils;
import com.google.android.material.tabs.TabLayout;

import com.example.capyshop.common.donhang.DonHang;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;

public class AdminQuanLyDonHangActivity extends BaseActivity {

    Toolbar tbQuanLyDonHang;
    TabLayout tlThanhDieuHuong;
    RecyclerView rvDonHang;
    ProgressBar pbDonHang;
    TextView tvThongBaoTrong;

    ApiAdmin apiAdmin;
    ApiCommon apiCommon;
    OkHttpClient okHttpClient;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    List<DonHang> mangDonHang = new ArrayList<>();
    AdminQuanLyDonHangAdapter adminQuanLyDonHangAdapter;

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
        apiCommon = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiCommon.class);

        rvDonHang.setHasFixedSize(true);
        rvDonHang.setLayoutManager(new LinearLayoutManager(this));

        adminQuanLyDonHangAdapter = new AdminQuanLyDonHangAdapter(getApplicationContext(), mangDonHang, this::xacNhanDonHang);
        rvDonHang.setAdapter(adminQuanLyDonHangAdapter);
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

    private void layDanhSachDonHang(String trangThai) {
        pbDonHang.setVisibility(View.VISIBLE);
        rvDonHang.setVisibility(View.GONE);
        tvThongBaoTrong.setVisibility(View.GONE);

        compositeDisposable.add(apiAdmin.layDanhSachDonHang(trangThai)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            pbDonHang.setVisibility(View.GONE);
                            if (model.isSuccess() && model.getResult() != null && !model.getResult().isEmpty()) {
                                mangDonHang.clear();
                                mangDonHang.addAll(model.getResult());
                                adminQuanLyDonHangAdapter.notifyDataSetChanged();
                                rvDonHang.setVisibility(View.VISIBLE);
                            } else {
                                mangDonHang.clear();
                                adminQuanLyDonHangAdapter.notifyDataSetChanged();
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
        String trangThai = donHang.getTrangThai();
        String thayDoiTrangThai = "";

        switch (trangThai) {
            case "CHO_XAC_NHAN":
                thayDoiTrangThai = "CHO_LAY_HANG";
                break;
            case "CHO_LAY_HANG":
                thayDoiTrangThai = "DANG_GIAO_HANG";
                break;
            case "DANG_GIAO_HANG":
                thayDoiTrangThai = "DA_GIAO_HANG";
                break;
            default:
                return; // No action for max status
        }

        final String finalNextStatus = thayDoiTrangThai; // For lambda
        final String trangThaiMoi = thayDoiTrangThai;
        int maNguoiDung = donHang.getMaNguoiDung();
        int maDonHang = donHang.getMaDonHang();
        compositeDisposable.add(apiAdmin.updateTrangThaiDonHang(donHang.getMaDonHang(), trangThaiMoi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            if (model.isSuccess()) {
                                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                guiThongBaoThayDoiTrangThaiThanhCong(trangThaiMoi, maNguoiDung, maDonHang);
                                mangDonHang.remove(donHang);
                                adminQuanLyDonHangAdapter.notifyDataSetChanged();

                                if (mangDonHang.isEmpty()) {
                                    tvThongBaoTrong.setVisibility(View.VISIBLE);
                                }
                            } else {
                                Toast.makeText(this, "Lỗi: " + model.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> Toast.makeText(this, "Lỗi kết nối", Toast.LENGTH_SHORT).show()));
    }

    private void guiThongBaoThayDoiTrangThaiThanhCong(String trangThaiMoi, int maNguoiDung, int maDonHang) {
        if (Utils.accessTokenSend != null) {
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Authorization(Utils.accessTokenSend))
                    .build();
        }
        // lấy token người dùng
        compositeDisposable.add(apiCommon.layToken(maNguoiDung,"NGUOI_MUA")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        nguoiDungModel -> {
                            if (nguoiDungModel.isSuccess()) {
                                // lấy token nguười dùng
                                String token = nguoiDungModel.getResult().get(0).getToken();
                                String body = "";
                                switch (trangThaiMoi) {
                                    case "CHO_LAY_HANG":
                                        body = "Đơn hàng của đã được xác nhận, vui lòng kiểm tra";
                                        break;
                                    case "DANG_GIAO_HANG":
                                        body = "Đơn hàng của trên đường giao, vui lòng kiểm tra";
                                        break;
                                    case "DA_GIAO_HANG":
                                        body = "Đơn hàng của đã được giao thành công, vui lòng kiểm tra";
                                        break;
                                    default:
                                        return; // No action for max status
                                }

                                Notification notification = new Notification("Đơn hàng " + maDonHang, body);
                                Message message = new Message(token, notification);
                                GuiThongBao guiThongBao = new GuiThongBao(message);
                                ApiCommon apiCommon = RetrofitClientThongBao.getInstance(okHttpClient).create(ApiCommon.class);
                                compositeDisposable.add(apiCommon.guiNhanThongBao(guiThongBao)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(
                                                nhanThongBao -> {

                                                }, throwable -> {
                                                    Log.d("log", throwable.getMessage());
                                                }
                                        ));
                            }
                        }, throwable -> {
                            Log.d("logg", throwable.getMessage());
                        }
                ));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
