package com.example.capyshop.user.sanpham;

import com.example.capyshop.common.sanpham.SanPham;

import android.os.Bundle;
import android.os.Handler;
import android.widget.FrameLayout;
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
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserSanPhamActivity extends BaseActivity {
    // Khai báo các thành phần UI và biến
    Toolbar tbSanPham;
    TextView tvToolbarSanPham;
    RecyclerView rvSanPham;
    FrameLayout flToolbarGioHangSanPham;
    NotificationBadge nbSanPham;
    LinearLayoutManager linearLayoutManager;

    ApiUser apiUser;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int trang = 1; // Trang hiện tại của dữ liệu
    int maDanhMuc; // ID loại sản phẩm
    UserSanPhamAdapter userSanPhamAdapter;
    List<SanPham> mangUserSanPham;

    Handler handler = new Handler();
    boolean isLoading = false; // Cờ để kiểm tra trạng thái loading

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.user_sanpham_activity);
        super.onCreate(savedInstanceState);
        // Lấy ID danh muc từ Intent
        maDanhMuc = getIntent().getIntExtra("madanhmuc", -1);
        // Khởi tạo Retrofit
        apiUser = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiUser.class);

        anhXa(); // Ánh xạ UI
        caiDatToolbar(); // Cấu hình Toolbar
        hienThiSanPham(trang); // Tải dữ liệu sản phẩm ban đầu
        themSuKienLoad(); // Thêm sự kiện cuộn để tải thêm dữ liệu

    }

    @Override
    protected void onDestroy() {
        // Hủy các yêu cầu mạng khi Activity bị hủy
        compositeDisposable.clear();
        super.onDestroy();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật lại số lượng sản phẩm trong giỏ hàng khi quay lại màn hình
        Utils.caiDatIconSoLuongGioHang(this, flToolbarGioHangSanPham, nbSanPham);

    }

    // Ánh xạ các View và khởi tạo
    private void anhXa() {
        tbSanPham = findViewById(R.id.tb_san_pham);
        tvToolbarSanPham = findViewById(R.id.tv_toolbar_san_pham);
        rvSanPham = findViewById(R.id.rv_san_pham);
        flToolbarGioHangSanPham = findViewById(R.id.fl_toolbar_gio_hang_san_pham);
        nbSanPham = findViewById(R.id.nb_san_pham);

        // Cấu hình RecyclerView
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvSanPham.setLayoutManager(linearLayoutManager);
        rvSanPham.setHasFixedSize(true);

        mangUserSanPham = new ArrayList<>();
    }

    // Cấu hình Toolbar
    private void caiDatToolbar() {
        setSupportActionBar(tbSanPham);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        // Lấy tên danh mục từ Intent và đặt làm tiêu đề
        String tenDanhMuc = getIntent().getStringExtra("tendanhmuc");
        if (tenDanhMuc != null) {
            tvToolbarSanPham.setText(tenDanhMuc);
        }
        // Quay về màn hình chính khi click nút back
        tbSanPham.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    // Tải dữ liệu sản phẩm từ API
    private void hienThiSanPham(int trangHienTai) {
        compositeDisposable.add(apiUser.laySanPham(trangHienTai, maDanhMuc)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamModel -> {
                            if (sanPhamModel.isSuccess()) {
                                if (userSanPhamAdapter == null) {
                                    mangUserSanPham = sanPhamModel.getResult();
                                    userSanPhamAdapter = new UserSanPhamAdapter(getApplicationContext(), mangUserSanPham);
                                    rvSanPham.setAdapter(userSanPhamAdapter);
                                } else {
                                    int viTriHienTai = mangUserSanPham.size();
                                    mangUserSanPham.addAll(sanPhamModel.getResult());
                                    userSanPhamAdapter.notifyItemRangeInserted(viTriHienTai, sanPhamModel.getResult().size());
                                }
                                isLoading = false;
                            } else {
                                if (trangHienTai > 1) Toast.makeText(this, "Đã hết sản phẩm", Toast.LENGTH_SHORT).show();
                                isLoading = true; // Ngừng load thêm khi hết dữ liệu
                            }
                        },
                        throwable -> Toast.makeText(this, "Lỗi server", Toast.LENGTH_SHORT).show()
                ));
    }

    // Thêm sự kiện lắng nghe cuộn RecyclerView để tải thêm dữ liệu
    private void themSuKienLoad() {
        rvSanPham.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // Kiểm tra nếu cuộn đến cuối và không đang loading
                if (!isLoading && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mangUserSanPham.size() - 1) {
                    isLoading = true;
                    loadMore(); // Gọi phương thức tải thêm dữ liệu
                }
            }
        });
    }

    // Phương thức xử lý việc tải thêm dữ liệu
    private void loadMore() {
        // Thêm một item null để hiển thị loading spinner
        handler.post(() -> {
            mangUserSanPham.add(null);
            userSanPhamAdapter.notifyItemInserted(mangUserSanPham.size() - 1);
        });

        // Giả lập delay 1.5 giây để mô phỏng quá trình tải
        handler.postDelayed(() -> {
            int loadingPos = mangUserSanPham.size() - 1;
            // Xóa item loading
            if (loadingPos >= 0 && mangUserSanPham.get(loadingPos) == null) {
                mangUserSanPham.remove(loadingPos);
                userSanPhamAdapter.notifyItemRemoved(loadingPos);
            }

            // Tăng trang và gọi API để tải dữ liệu mới
            trang++;
            hienThiSanPham(trang);
            isLoading = true; // Kết thúc loading
        }, 1500);
    }
}