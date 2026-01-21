package com.example.capyshop.user.tìmkiemsanpham;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capyshop.R;
import com.example.capyshop.common.activity.BaseActivity;
import com.example.capyshop.common.sanpham.SanPham;
import com.example.capyshop.common.retrofit.ApiUser;
import com.example.capyshop.common.retrofit.RetrofitClient;
import com.example.capyshop.common.utils.Utils;
import com.example.capyshop.user.sanpham.UserSanPhamAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class UserTimKiemSanPhamActivity extends BaseActivity {
    Toolbar tbTimKiem;
    SearchView svTimKiem;
    RecyclerView rvTimKiem;
    TextView tvThongBaoTimKiem;

    UserSanPhamAdapter userSanPhamAdapter;
    List<SanPham> mangUserSanPham;;
    ApiUser apiUser;
    CompositeDisposable compositeDisposable = new CompositeDisposable();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.user_timkiemsanpham_activity);
        super.onCreate(savedInstanceState);
        anhXa();
        caiDatToolbar();
        xuLyTimKiem();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void anhXa() {
        tbTimKiem = findViewById(R.id.tb_tim_kiem);
        svTimKiem = findViewById(R.id.sv_tim_kiem);
        rvTimKiem = findViewById(R.id.rv_tim_kiem);
        tvThongBaoTimKiem = findViewById(R.id.tv_thong_bao_tim_kiem_san_pham);

        // Khởi tạo Retrofit
        apiUser = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiUser.class);
        // Cấu hình RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvTimKiem.setLayoutManager(linearLayoutManager);
        rvTimKiem.setHasFixedSize(true);
        //
        mangUserSanPham = new ArrayList<>();

    }

    private void caiDatToolbar() {
        setSupportActionBar(tbTimKiem);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        tbTimKiem.setNavigationOnClickListener(v -> finish());

    }



    private void xuLyTimKiem() {
        io.reactivex.rxjava3.subjects.PublishSubject<String> searchSubject = io.reactivex.rxjava3.subjects.PublishSubject.create();
        // 1. Cấu hình RxJava Debounce
        compositeDisposable.add(searchSubject
                .debounce(400, TimeUnit.MILLISECONDS) // Đợi 400ms sau khi người dùng ngừng gõ
                .distinctUntilChanged()              // Chỉ gọi API nếu nội dung thay đổi so với lần trước
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        query -> {
                            if (!query.trim().isEmpty()) {
                                compositeDisposable.add(apiUser.timKiemSanPham(query)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(
                                                sanPhamModel -> {
                                                    if (sanPhamModel.isSuccess()) {
                                                        tvThongBaoTimKiem.setVisibility(View.GONE);
                                                        mangUserSanPham = sanPhamModel.getResult();
                                                        userSanPhamAdapter = new UserSanPhamAdapter(getApplicationContext(), mangUserSanPham);
                                                        rvTimKiem.setAdapter(userSanPhamAdapter);
                                                    } else {
                                                        mangUserSanPham.clear();
                                                        if (userSanPhamAdapter != null) {
                                                            userSanPhamAdapter.notifyDataSetChanged();
                                                        }
                                                        tvThongBaoTimKiem.setVisibility(View.VISIBLE);
                                                    }
                                                },
                                                throwable -> {
                                                    Log.e("APIError", "Lỗi: " + throwable.getMessage());
                                                }
                                        ));
                            } else {
                                // Nếu xóa hết chữ, xóa danh sách trên UI
                                runOnUiThread(() -> {
                                    mangUserSanPham.clear();
                                    if (userSanPhamAdapter != null) {
                                        userSanPhamAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        },
                        throwable -> Log.e("SearchError", throwable.getMessage())
                ));

        // 2. Kết nối SearchView với PublishSubject
        svTimKiem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Khi nhấn nút tìm kiếm trên bàn phím, thực hiện ngay lập tức
                searchSubject.onNext(query);
                svTimKiem.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Đưa nội dung mới gõ vào luồng xử lý của RxJava (sẽ được debounce)
                return true;
            }
        });
    }


}