package com.example.capyshop.admin.danhmuc;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capyshop.R;
import com.example.capyshop.common.Interface.ImageClickListener;
import com.example.capyshop.common.activity.BaseActivity;
import com.example.capyshop.common.retrofit.ApiAdmin;
import com.example.capyshop.common.retrofit.RetrofitClient;
import com.example.capyshop.common.utils.Utils;

import com.example.capyshop.common.danhmuc.DanhMuc;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdminQuanLyDanhMucActivity extends BaseActivity implements ImageClickListener {

    Toolbar tbAdminQuanLyDanhMuc;
    RecyclerView rvAdminQuanLyDanhMuc;
    TextView tvAdminQuanLyDanhMucSoLuong;
    AppCompatButton btAdminQuanLyDanhMucThem;

    ApiAdmin apiAdmin;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    List<DanhMuc> mangDanhMuc = new ArrayList<>();
    AdminQuanLyDanhMucAdapter adminQuanLyDanhMucAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.admin_quanlydanhmuc_activity);
        super.onCreate(savedInstanceState);

        anhXa();
        caiDatToolbar();
        hienThiDanhSachDanhMuc();
        xuLySuKienClickThemDanhMuc();
    }

    private void anhXa() {
        tbAdminQuanLyDanhMuc = findViewById(R.id.tb_admin_quan_ly_danh_muc);
        rvAdminQuanLyDanhMuc = findViewById(R.id.rv_admin_quan_ly_danh_muc);
        tvAdminQuanLyDanhMucSoLuong = findViewById(R.id.tv_admin_quan_ly_danh_muc_so_luong);
        btAdminQuanLyDanhMucThem = findViewById(R.id.bt_admin_quan_ly_danh_muc_them);

        apiAdmin = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiAdmin.class);
        rvAdminQuanLyDanhMuc.setHasFixedSize(true);
        rvAdminQuanLyDanhMuc.setLayoutManager(new LinearLayoutManager(this));

    }

    private void caiDatToolbar() {
        setSupportActionBar(tbAdminQuanLyDanhMuc);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }
        tbAdminQuanLyDanhMuc.setNavigationOnClickListener(v -> finish());
    }

    // xử lý sự kiện click sửa và xóa danh mục
    @Override
    public void onImageClick(View view, int position, int isLongClick) {
        DanhMuc danhMuc = mangDanhMuc.get(position);
        if (view.getId() == R.id.iv_admin_item_danh_muc_sua) {
            AdminQuanLyDanhMucBottomSheet bottomSheet = new AdminQuanLyDanhMucBottomSheet(danhMuc, () -> {
                hienThiDanhSachDanhMuc(); // Tải lại danh sách khi Sửa thành công
            });
            bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
        } else if (view.getId() == R.id.iv_admin_item_danh_muc_xoa) {
            Utils.thietLapBottomSheetDialog(
                    AdminQuanLyDanhMucActivity.this,
                    "Xác nhận xóa",
                    "Bạn có chắc chắn muốn xóa danh mục '" + danhMuc.getTenDanhMuc() + "' không?",
                    "Xóa ngay",
                    () -> compositeDisposable.add(apiAdmin.xoaDanhMuc(danhMuc.getMaDanhMuc())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    model -> {
                                        if (model.isSuccess()) {
                                            Toast.makeText(this, model.getMessage(), Toast.LENGTH_SHORT).show();
                                            hienThiDanhSachDanhMuc();
                                        } else {
                                            Toast.makeText(this, model.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(this, "Lỗi kết nối Server", Toast.LENGTH_SHORT).show();
                                    })));
        }
    }
    private void xuLySuKienClickThemDanhMuc() {
        btAdminQuanLyDanhMucThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminQuanLyDanhMucBottomSheet bottomSheet = new AdminQuanLyDanhMucBottomSheet(null, () -> {
                    hienThiDanhSachDanhMuc(); // Tải lại danh sách khi Thêm thành công
                });
                bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
            }
        });

    }

    private void hienThiDanhSachDanhMuc() {
        compositeDisposable.add(apiAdmin.layDanhSachDanhMuc()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            if (model.isSuccess()) {
                                mangDanhMuc.clear();
                                mangDanhMuc.addAll(model.getResult());
                                
                                if (adminQuanLyDanhMucAdapter == null) {
                                    adminQuanLyDanhMucAdapter = new AdminQuanLyDanhMucAdapter(this, mangDanhMuc, this);
                                    rvAdminQuanLyDanhMuc.setAdapter(adminQuanLyDanhMucAdapter);
                                } else {
                                    adminQuanLyDanhMucAdapter.notifyDataSetChanged();
                                }
                                
                                tvAdminQuanLyDanhMucSoLuong.setText(mangDanhMuc.size() + " Danh mục");
                            } else {
                                Toast.makeText(this, model.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(this, "Lỗi kết nối Server", Toast.LENGTH_SHORT).show();
                        }));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}