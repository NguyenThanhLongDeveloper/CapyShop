package com.example.capyshop.admin.quangcao;

import android.os.Bundle;
import android.view.View;
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

import com.example.capyshop.common.quangcao.QuangCao;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdminQuanLyQuangCaoActivity extends BaseActivity implements ImageClickListener {

    Toolbar tbAdminQuanLyQuangCao;
    RecyclerView rvAdminQuanLyQuangCao;
    AppCompatButton btAdminQuanLyQuangCaoThem;

    ApiAdmin apiAdmin;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    List<QuangCao> mangAdminQuangCao = new ArrayList<>();
    AdminQuanLyQuangCaoAdapter adapterQuangCao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.admin_quanlyquangcao_activity);
        super.onCreate(savedInstanceState);

        anhXa();
        caiDatToolbar();
        caiDatRecyclerView();
        layDanhSachQuangCao();
        xuLySuKienClick();
    }

    private void anhXa() {
        tbAdminQuanLyQuangCao = findViewById(R.id.tb_admin_quan_ly_quang_cao);
        rvAdminQuanLyQuangCao = findViewById(R.id.rv_admin_quan_ly_quang_cao);
        btAdminQuanLyQuangCaoThem = findViewById(R.id.bt_admin_quan_ly_quang_cao_them);

        apiAdmin = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiAdmin.class);
    }

    private void caiDatToolbar() {
        setSupportActionBar(tbAdminQuanLyQuangCao);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }
        tbAdminQuanLyQuangCao.setNavigationOnClickListener(v -> finish());
    }

    private void xuLySuKienClick() {
        btAdminQuanLyQuangCaoThem.setOnClickListener(v -> hienThiBottomSheetForm());
    }

    private void hienThiBottomSheetForm() {
        AdminQuanLyQuangCaoBottomSheet bottomSheet = new AdminQuanLyQuangCaoBottomSheet(() -> {
            layDanhSachQuangCao(); // Tải lại danh sách khi Thêm thành công
        });
        bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
    }

    private void caiDatRecyclerView() {
        rvAdminQuanLyQuangCao.setHasFixedSize(true);
        rvAdminQuanLyQuangCao.setLayoutManager(new LinearLayoutManager(this));
        adapterQuangCao = new AdminQuanLyQuangCaoAdapter(this, mangAdminQuangCao, this);
        rvAdminQuanLyQuangCao.setAdapter(adapterQuangCao);
    }

    private void layDanhSachQuangCao() {
        compositeDisposable.add(apiAdmin.layDanhSachQuangCao()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            if (model.isSuccess()) {
                                mangAdminQuangCao.clear();
                                mangAdminQuangCao.addAll(model.getResult());
                                adapterQuangCao.notifyDataSetChanged();
                            } else {
                                Toast.makeText(this, model.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(this, "Lỗi kết nối Server", Toast.LENGTH_SHORT).show();
                        }));
    }

    private void xuLyXoaQuangCao(QuangCao adminQuangCao) {
        compositeDisposable.add(apiAdmin.xoaQuangCao(adminQuangCao.getMaQuangCao())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            if (model.isSuccess()) {
                                Toast.makeText(this, model.getMessage(), Toast.LENGTH_SHORT).show();
                                layDanhSachQuangCao();
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
        if (view.getId() == R.id.iv_admin_item_quang_cao_xoa) {
            QuangCao adminQuangCao = mangAdminQuangCao.get(position);
            Utils.thietLapBottomSheetDialog(
                    AdminQuanLyQuangCaoActivity.this,
                    "Xác nhận xóa",
                    "Bạn có chắc chắn muốn xóa quảng cáo này không?",
                    "Xóa ngay",
                    () -> xuLyXoaQuangCao(adminQuangCao));
        }
    }
}
