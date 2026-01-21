package com.example.capyshop.admin.sanpham;

import com.example.capyshop.common.sanpham.SanPham;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capyshop.R;
import com.example.capyshop.common.activity.BaseActivity;
import com.example.capyshop.common.retrofit.ApiAdmin;
import com.example.capyshop.common.retrofit.RetrofitClient;
import com.example.capyshop.common.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import com.example.capyshop.common.Interface.ImageClickListener;
import android.view.View;

public class AdminQuanLySanPhamActivity extends BaseActivity implements ImageClickListener {

    Toolbar tbAdminQuanLySanPham;
    RecyclerView rvAdminQuanLySanPham;
    TextView tvAdminQuanLySanPhamSoLuongDanhSach;
    AppCompatButton btAdminQuanLySanPhamThem;

    ApiAdmin apiAdmin;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    List<SanPham> mangSanPham = new ArrayList<>();
    AdminQuanLySanPhamAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.admin_quanlysanpham_activity);
        super.onCreate(savedInstanceState);

        anhXa();
        caiDatToolbar();
        caiDatRecyclerView();
        xuLySuKienClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        layDanhSachSanPham();
    }

    private void anhXa() {
        tbAdminQuanLySanPham = findViewById(R.id.tb_admin_quan_ly_san_pham);
        rvAdminQuanLySanPham = findViewById(R.id.rv_admin_quan_ly_san_pham);
        tvAdminQuanLySanPhamSoLuongDanhSach = findViewById(R.id.tv_admin_quan_ly_san_pham_so_luong_danh_sach);
        btAdminQuanLySanPhamThem = findViewById(R.id.bt_admin_quan_ly_san_pham_them);

        apiAdmin = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiAdmin.class);
    }

    private void caiDatToolbar() {
        setSupportActionBar(tbAdminQuanLySanPham);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }
        tbAdminQuanLySanPham.setNavigationOnClickListener(v -> finish());
    }

    private void caiDatRecyclerView() {
        rvAdminQuanLySanPham.setHasFixedSize(true);
        rvAdminQuanLySanPham.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdminQuanLySanPhamAdapter(this, mangSanPham, this);
        rvAdminQuanLySanPham.setAdapter(adapter);
    }

    private void xuLySuKienClick() {
        btAdminQuanLySanPhamThem.setOnClickListener(v -> {
            Intent intent = new Intent(AdminQuanLySanPhamActivity.this, AdminThemSanPhamActivity.class);
            intent.putExtra("isEdit", false);
            startActivity(intent);
        });
    }

    private void layDanhSachSanPham() {
        compositeDisposable.add(apiAdmin.layDanhSachSanPham()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            mangSanPham.clear();
                            if (model.isSuccess()) {
                                mangSanPham.addAll(model.getResult());
                            } else {
                                // If failed, check if it's just no data
                                if (!model.getMessage().equals("không có dữ liệu")) {
                                    Toast.makeText(this, model.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            adapter.notifyDataSetChanged();
                            tvAdminQuanLySanPhamSoLuongDanhSach.setText("DANH SÁCH (" + mangSanPham.size() + ")");
                        },
                        throwable -> {
                            Toast.makeText(this, "Lỗi kết nối Server: " + throwable.getMessage(), Toast.LENGTH_SHORT)
                                    .show();
                        }));
    }

    private void xuLyXoaSanPham(SanPham sanPham) {
        compositeDisposable.add(apiAdmin.xoaSanPham(sanPham.getMaSanPham())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            if (model.isSuccess()) {
                                Toast.makeText(this, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
                                layDanhSachSanPham();
                            } else {
                                Toast.makeText(this, model.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(this, "Lỗi khi xóa: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    public void onImageClick(View view, int position, int isLongClick) {
        SanPham sanPham = mangSanPham.get(position);
        if (view.getId() == R.id.iv_admin_item_san_pham_sua) {
            Intent intent = new Intent(AdminQuanLySanPhamActivity.this, AdminThemSanPhamActivity.class);
            intent.putExtra("sanpham", sanPham);
            intent.putExtra("isEdit", true);
            startActivity(intent);
        } else if (view.getId() == R.id.iv_admin_item_san_pham_xoa) {
            Utils.thietLapBottomSheetDialog(
                    AdminQuanLySanPhamActivity.this,
                    "Xác nhận xóa",
                    "Bạn có chắc chắn muốn xóa sản phẩm '" + sanPham.getTenSanPham() + "' không?",
                    "Xóa ngay",
                    () -> xuLyXoaSanPham(sanPham));
        }
    }
}
