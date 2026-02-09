package com.example.capyshop.admin.phuongthucthanhtoan;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capyshop.R;
import com.example.capyshop.common.activity.BaseActivity;
import com.example.capyshop.common.retrofit.ApiAdmin;
import com.example.capyshop.common.retrofit.RetrofitClient;
import com.example.capyshop.common.utils.Utils;

import com.example.capyshop.common.phuongthucthanhtoan.PhuongThucThanhToan;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdminQuanLyPhuongThucThanhToanActivity extends BaseActivity {

    Toolbar tbQuanLyPhuongThuc;
    RecyclerView rvPhuongThucHoatDong, rvViDienTu;
    ApiAdmin apiAdmin;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    List<PhuongThucThanhToan> mangHoatDong = new ArrayList<>();
    List<PhuongThucThanhToan> mangViDienTu = new ArrayList<>();

    AdminQuanLyPhuongThucThanhToanAdapter adapterHoatDong, adapterViDienTu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.admin_quanlyphuongthucthanhtoan_activity);
        super.onCreate(savedInstanceState);

        anhXa();
        caiDatToolbar();
        layDanhSachPhuongThucThanhToan();
    }

    private void anhXa() {
        tbQuanLyPhuongThuc = findViewById(R.id.tb_admin_quan_ly_phuong_thuc_thanh_toan);
        rvPhuongThucHoatDong = findViewById(R.id.rv_admin_quan_ly_phuong_thuc_thanh_toan_hoat_dong);
        rvViDienTu = findViewById(R.id.rv_admin_quan_ly_phuong_thuc_thanh_toan_vi_dien_tu);

        apiAdmin = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiAdmin.class);

        setupRecyclerView(rvPhuongThucHoatDong);
        setupRecyclerView(rvViDienTu);

        adapterHoatDong = new AdminQuanLyPhuongThucThanhToanAdapter(this, mangHoatDong, this::capNhatTrangThaiPhuongThucThanhToan);
        adapterViDienTu = new AdminQuanLyPhuongThucThanhToanAdapter(this, mangViDienTu, this::capNhatTrangThaiPhuongThucThanhToan);

        rvPhuongThucHoatDong.setAdapter(adapterHoatDong);
        rvViDienTu.setAdapter(adapterViDienTu);
    }

    private void setupRecyclerView(RecyclerView rv) {
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setNestedScrollingEnabled(false);
    }

    private void caiDatToolbar() {
        setSupportActionBar(tbQuanLyPhuongThuc);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }
        tbQuanLyPhuongThuc.setNavigationOnClickListener(v -> finish());
    }

    private void layDanhSachPhuongThucThanhToan() {
        compositeDisposable.add(apiAdmin.layDanhSachPhuongThucThanhToan()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            if (model.isSuccess()) {
                                listSeparator(model.getResult());
                            }
                        },
                        throwable -> Toast.makeText(this, "Lỗi kết nối", Toast.LENGTH_SHORT).show()));
    }

    private void listSeparator(List<PhuongThucThanhToan> result) {
        mangHoatDong.clear();
        mangViDienTu.clear();

        for (PhuongThucThanhToan item : result) {
            String ten = item.getTenPhuongThucThanhToan().toLowerCase();
            if (ten.contains("momo") || ten.contains("zalopay") || ten.contains("ví")) {
                mangViDienTu.add(item);
            } else {
                mangHoatDong.add(item);
            }
        }

        adapterHoatDong.notifyDataSetChanged();
        adapterViDienTu.notifyDataSetChanged();
    }

    private void capNhatTrangThaiPhuongThucThanhToan(PhuongThucThanhToan item, boolean isChecked) {
        String newStatus = isChecked ? "HOAT_DONG" : "TAM_TAT";
        compositeDisposable.add(apiAdmin.updateTrangThaiPhuongThucThanhToan(item.getMaPhuongThucThanhToan(), newStatus)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            if (model.isSuccess()) {
                                Toast.makeText(this, "Đã cập nhập trạng thái", Toast.LENGTH_SHORT).show();
                                item.setTrangThai(newStatus);
                                // Required to update subtitle if it depends on status
                                adapterHoatDong.notifyDataSetChanged();
                                adapterViDienTu.notifyDataSetChanged();
                            } else {
                                Toast.makeText(this, "Lỗi: " + model.getMessage(), Toast.LENGTH_SHORT).show();
                                adapterHoatDong.notifyDataSetChanged();
                                adapterViDienTu.notifyDataSetChanged();
                            }
                        },
                        throwable -> {
                            Toast.makeText(this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                            adapterHoatDong.notifyDataSetChanged();
                            adapterViDienTu.notifyDataSetChanged();
                        }));
    }


    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
