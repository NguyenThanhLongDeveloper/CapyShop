package com.example.capyshop.admin.donvivanchuyen;

import android.os.Bundle;
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

import com.example.capyshop.common.donvivanchuyen.DonViVanChuyen;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import com.example.capyshop.common.Interface.ImageClickListener;
import android.view.View;

public class AdminQuanLyDonViVanChuyenActivity extends BaseActivity implements ImageClickListener {

    Toolbar tbAdminQuanLyDonViVanChuyen;
    RecyclerView rvAdminQuanLyDonViVanChuyen;
    AppCompatButton btAdminQuanLyDonViVanChuyenThem;

    ApiAdmin apiAdmin;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    List<DonViVanChuyen> mangDonVi = new ArrayList<>();
    AdminQuanLyDonViVanChuyenAdapter adapterDonVi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.admin_quanlydonvivanchuyen_activity);
        super.onCreate(savedInstanceState);

        anhXa();
        caiDatToolbar();
        caiDatRecyclerView();
        layDanhSachDonViVanChuyen();
        xuLySuKienClick();
    }

    private void anhXa() {
        tbAdminQuanLyDonViVanChuyen = findViewById(R.id.tb_admin_quan_ly_don_vi_van_chuyen);
        rvAdminQuanLyDonViVanChuyen = findViewById(R.id.rv_admin_quan_ly_don_vi_van_chuyen);
        btAdminQuanLyDonViVanChuyenThem = findViewById(R.id.bt_admin_quan_ly_don_vi_van_chuyen_them);

        apiAdmin = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiAdmin.class);
    }

    private void caiDatToolbar() {
        setSupportActionBar(tbAdminQuanLyDonViVanChuyen);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }
        tbAdminQuanLyDonViVanChuyen.setNavigationOnClickListener(v -> finish());
    }

    private void xuLySuKienClick() {
        btAdminQuanLyDonViVanChuyenThem.setOnClickListener(v -> hienThiBottomSheetForm(null));
    }

    private void hienThiBottomSheetForm(DonViVanChuyen donVi) {
        AdminQuanLyDonViVanChuyenBottomSheet bottomSheet;
        if (donVi != null) {
            bottomSheet = new AdminQuanLyDonViVanChuyenBottomSheet(donVi, () -> {
                layDanhSachDonViVanChuyen();
            });
        } else {
            bottomSheet = new AdminQuanLyDonViVanChuyenBottomSheet(() -> {
                layDanhSachDonViVanChuyen();
            });
        }
        bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
    }

    private void caiDatRecyclerView() {
        rvAdminQuanLyDonViVanChuyen.setHasFixedSize(true);
        rvAdminQuanLyDonViVanChuyen.setLayoutManager(new LinearLayoutManager(this));
        adapterDonVi = new AdminQuanLyDonViVanChuyenAdapter(this, mangDonVi, this, null); // Removed trangThaiListener as it's now in BottomSheet Edit
        rvAdminQuanLyDonViVanChuyen.setAdapter(adapterDonVi);
    }

    private void layDanhSachDonViVanChuyen() {
        compositeDisposable.add(apiAdmin.layDanhSachDonViVanChuyen()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            if (model.isSuccess()) {
                                mangDonVi.clear();
                                mangDonVi.addAll(model.getResult());
                                adapterDonVi.notifyDataSetChanged();
                            } else {
                                Toast.makeText(this, model.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(this, "Lỗi kết nối Server", Toast.LENGTH_SHORT).show();
                        }));
    }

    private void xuLyXoaDonViVanChuyen(DonViVanChuyen donVi) {
        compositeDisposable.add(apiAdmin.xoaDonViVanChuyen(donVi.getMaDonViVanChuyen())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            if (model.isSuccess()) {
                                Toast.makeText(this, model.getMessage(), Toast.LENGTH_SHORT).show();
                                layDanhSachDonViVanChuyen();
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

    @Override
    public void onImageClick(View view, int position, int isLongClick) {
        DonViVanChuyen donVi = mangDonVi.get(position);
        if (view.getId() == R.id.iv_admin_item_don_vi_van_chuyen_xoa) {
            Utils.thietLapBottomSheetDialog(
                    AdminQuanLyDonViVanChuyenActivity.this,
                    "Xác nhận xóa",
                    "Bạn có chắc chắn muốn xóa đơn vị '" + donVi.getTenDonViVanChuyen() + "' không?",
                    "Xóa ngay",
                    () -> xuLyXoaDonViVanChuyen(donVi));
        } else if (view.getId() == R.id.iv_admin_item_don_vi_van_chuyen_sua) {
            hienThiBottomSheetForm(donVi);
        }
    }
}
