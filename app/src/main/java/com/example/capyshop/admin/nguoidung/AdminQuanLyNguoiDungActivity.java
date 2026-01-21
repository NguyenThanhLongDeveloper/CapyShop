package com.example.capyshop.admin.nguoidung;

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

import com.example.capyshop.common.nguoidung.NguoiDung;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdminQuanLyNguoiDungActivity extends BaseActivity {

    Toolbar tbAdminQuanLyNguoiDung;
    RecyclerView rvAdminQuanLyNguoiDung;
    private com.google.android.material.tabs.TabLayout tlAdminBoLocNguoiDung;

    ApiAdmin apiAdmin;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    List<NguoiDung> mangAdminNguoiDungGoc = new ArrayList<>();
    List<NguoiDung> mangAdminNguoiDungHienThi = new ArrayList<>();
    AdminQuanLyNguoiDungAdapter adapterNguoiDung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.admin_quanlynguoidung_activity);
        super.onCreate(savedInstanceState);

        anhXa();
        caiDatToolbar();
        caiDatRecyclerView();
        caiDatTabs();
        layDanhSachNguoiDung();
    }

    private void anhXa() {
        tbAdminQuanLyNguoiDung = findViewById(R.id.tb_admin_quan_ly_nguoi_dung);
        rvAdminQuanLyNguoiDung = findViewById(R.id.rv_admin_quan_ly_nguoi_dung);
        tlAdminBoLocNguoiDung = findViewById(R.id.tl_admin_bo_loc_nguoi_dung);

        apiAdmin = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiAdmin.class);
    }

    private void caiDatToolbar() {
        setSupportActionBar(tbAdminQuanLyNguoiDung);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        tbAdminQuanLyNguoiDung.setNavigationOnClickListener(v -> finish());
    }


    private void caiDatTabs() {
        tlAdminBoLocNguoiDung.addOnTabSelectedListener(new com.google.android.material.tabs.TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(com.google.android.material.tabs.TabLayout.Tab tab) {
                locDuLieuLocals();
            }

            @Override
            public void onTabUnselected(com.google.android.material.tabs.TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(com.google.android.material.tabs.TabLayout.Tab tab) { }
        });
    }

    private void locDuLieuLocals() {
        int tabPosition = tlAdminBoLocNguoiDung.getSelectedTabPosition();

        List<NguoiDung> temp = new ArrayList<>();
        for (NguoiDung user : mangAdminNguoiDungGoc) {
            boolean matchesTab = (tabPosition == 0) || (tabPosition == 1 && "KHOA".equals(user.getTrangThai()));

            if (matchesTab) {
                temp.add(user);
            }
        }

        mangAdminNguoiDungHienThi.clear();
        mangAdminNguoiDungHienThi.addAll(temp);
        adapterNguoiDung.notifyDataSetChanged();
    }

    private void caiDatRecyclerView() {
        rvAdminQuanLyNguoiDung.setHasFixedSize(true);
        rvAdminQuanLyNguoiDung.setLayoutManager(new LinearLayoutManager(this));
        adapterNguoiDung = new AdminQuanLyNguoiDungAdapter(this, mangAdminNguoiDungHienThi, this::capNhatTrangThaiTaiKhoan);
        rvAdminQuanLyNguoiDung.setAdapter(adapterNguoiDung);
    }

    private void layDanhSachNguoiDung() {
        compositeDisposable.add(apiAdmin.layDanhSachNguoiDung()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            if (model.isSuccess()) {
                                mangAdminNguoiDungGoc.clear();
                                mangAdminNguoiDungGoc.addAll(model.getResult());
                                capNhatTabCount();
                                locDuLieuLocals();
                            } else {
                                Toast.makeText(this, model.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> Toast.makeText(this, "Lỗi kết nối Server", Toast.LENGTH_SHORT).show()));
    }


    private void capNhatTabCount() {
        int total = mangAdminNguoiDungGoc.size();
        int locked = 0;
        for (NguoiDung u : mangAdminNguoiDungGoc) {
            if ("KHOA".equals(u.getTrangThai())) locked++;
        }

        if (tlAdminBoLocNguoiDung.getTabAt(0) != null) {
            tlAdminBoLocNguoiDung.getTabAt(0).setText("Tất cả (" + total + ")");
        }
        if (tlAdminBoLocNguoiDung.getTabAt(1) != null) {
            tlAdminBoLocNguoiDung.getTabAt(1).setText("Bị khóa (" + locked + ")");
        }
    }

    private void capNhatTrangThaiTaiKhoan(NguoiDung user, boolean isChecked) {
        String trangThaiMoi = isChecked ? "HOAT_DONG" : "KHOA";
        compositeDisposable.add(apiAdmin.khoaTaiKhoan(user.getMaNguoiDung(), trangThaiMoi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            if (model.isSuccess()) {
                                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                // Cập nhật danh sách gốc
                                for (NguoiDung u : mangAdminNguoiDungGoc) {
                                    if (u.getMaNguoiDung() == user.getMaNguoiDung()) {
                                        u.setTrangThai(trangThaiMoi);
                                        break;
                                    }
                                }
                                user.setTrangThai(trangThaiMoi);
                                capNhatTabCount();
                                locDuLieuLocals();
                            } else {
                                Toast.makeText(this, model.getMessage(), Toast.LENGTH_SHORT).show();
                                adapterNguoiDung.notifyDataSetChanged();
                            }
                        },
                        throwable -> {
                            Toast.makeText(this, "Lỗi kết nối Server", Toast.LENGTH_SHORT).show();
                            adapterNguoiDung.notifyDataSetChanged();
                        }));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
