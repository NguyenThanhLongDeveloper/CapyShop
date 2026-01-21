package com.example.capyshop.user.chitietsanpham;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.capyshop.R;
import com.example.capyshop.common.activity.BaseActivity;
import com.example.capyshop.common.chitietsanpham.ChiTietSanPhamAlbumAdapter;
import com.example.capyshop.common.retrofit.ApiUser;
import com.example.capyshop.common.retrofit.RetrofitClient;
import com.example.capyshop.common.utils.Utils;
import com.example.capyshop.common.sanpham.SanPham;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.NumberFormat;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserChiTietSanPhamActivity extends BaseActivity {
    // UI chính của màn hình
    NestedScrollView nsvChiTietSanPham;
    ImageView ivHinhAnhChiTietSanPham;
    ViewPager2 vpHinhAnhChiTietSanPham;
    TextView tvTenChiTietSanPham, tvGiaChiTietSanPham, tvMoTaChiTietSanPham, tvSoLuongHinhAnhChiTietSanPham;
    AppCompatButton btThemGioHangChiTietSanPham;

    // Toolbar
    Toolbar tbChiTietSanPham;
    View vToolbarChiTietSanPham;
    TextView tvToolbarTenChiTietSanPham;
    FrameLayout flToolbarGioHangChiTietSanPham;
    NotificationBadge nbToolbarGioHangChiTietSanPham;

    // Dữ liệu
    SanPham userSanPham;
    int maSanPham;
    ChiTietSanPhamAlbumAdapter userChiTietSanPhamAlbumAdapter;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiUser apiUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.user_chitietsanpham_activity);
        super.onCreate(savedInstanceState);

        maSanPham = getIntent().getIntExtra("maSanPham", 0);

        anhXa();
        caiDatToolbar();
        hienThiChiTietSanPham(maSanPham);
        xuLySuKienBamNutThem(); // Hàm mới để mở BottomSheet
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Chỉ cần cập nhật Badge giỏ hàng ở đây
        Utils.caiDatIconSoLuongGioHang(this, flToolbarGioHangChiTietSanPham, nbToolbarGioHangChiTietSanPham);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    private void anhXa() {
        nsvChiTietSanPham = findViewById(R.id.nsv_chi_tiet_san_pham);
        ivHinhAnhChiTietSanPham = findViewById(R.id.iv_hinh_anh_chi_tiet_san_pham);
        vpHinhAnhChiTietSanPham = findViewById(R.id.vp_hinh_anh_chi_tiet_san_pham);
        tvSoLuongHinhAnhChiTietSanPham = findViewById(R.id.tv_so_luong_hinh_anh_chi_tiet_san_pham);
        tvTenChiTietSanPham = findViewById(R.id.tv_ten_chi_tiet_san_pham);
        tvGiaChiTietSanPham = findViewById(R.id.tv_gia_chi_tiet_san_pham);
        tvMoTaChiTietSanPham = findViewById(R.id.tv_mo_ta_chi_tiet_san_pham);
        btThemGioHangChiTietSanPham = findViewById(R.id.bt_them_gio_hang_chi_tiet_san_pham);

        tbChiTietSanPham = findViewById(R.id.tb_chi_tiet_san_pham);
        vToolbarChiTietSanPham = findViewById(R.id.v_toolbar_chi_tiet_san_pham);
        tvToolbarTenChiTietSanPham = findViewById(R.id.tv_toolbar_ten_chi_tiet_san_pham);
        flToolbarGioHangChiTietSanPham = findViewById(R.id.fl_toolbar_gio_hang_chi_tiet_san_pham);
        nbToolbarGioHangChiTietSanPham = findViewById(R.id.nb_chi_tiet_san_pham);

        apiUser = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiUser.class);
    }

    private void caiDatToolbar() {
        setSupportActionBar(tbChiTietSanPham);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }

        tbChiTietSanPham.setNavigationOnClickListener(v -> finish());
        vToolbarChiTietSanPham.setAlpha(0);
        tvToolbarTenChiTietSanPham.setAlpha(0);

        final int maxScroll = 400;
        nsvChiTietSanPham.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            float alpha = Math.min(1f, (float) scrollY / maxScroll);
            vToolbarChiTietSanPham.setAlpha(alpha);
            tvToolbarTenChiTietSanPham.setAlpha(alpha);
        });
    }

    private void hienThiChiTietSanPham(int id) {
        compositeDisposable.add(apiUser.layChiTietSanPham(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamModel -> {
                            if (sanPhamModel.isSuccess() && !sanPhamModel.getResult().isEmpty()) {
                                userSanPham = sanPhamModel.getResult().get(0);
                                tvTenChiTietSanPham.setText(userSanPham.getTenSanPham());
                                tvToolbarTenChiTietSanPham.setText(userSanPham.getTenSanPham());

                                NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
                                String giaStr = numberFormat.format(userSanPham.getGiaSanPham()) + " đ";
                                tvGiaChiTietSanPham.setText(giaStr);
                                tvMoTaChiTietSanPham.setText(userSanPham.getMoTaSanPham());

                                // Xử lý ViewPager ảnh sản phẩm
                                if (userSanPham.getAlbum() != null && !userSanPham.getAlbum().isEmpty()) {
                                    vpHinhAnhChiTietSanPham.setVisibility(View.VISIBLE);
                                    ivHinhAnhChiTietSanPham.setVisibility(View.GONE);

                                    int soLuongHinhAnh = userSanPham.getAlbum().size();
                                    tvSoLuongHinhAnhChiTietSanPham.setText("1/" + soLuongHinhAnh);

                                    userChiTietSanPhamAlbumAdapter = new ChiTietSanPhamAlbumAdapter(this, userSanPham.getAlbum());
                                    vpHinhAnhChiTietSanPham.setAdapter(userChiTietSanPhamAlbumAdapter);

                                    vpHinhAnhChiTietSanPham.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                        @Override
                                        public void onPageSelected(int position) {
                                            super.onPageSelected(position);
                                            tvSoLuongHinhAnhChiTietSanPham.setText((position + 1) + "/" + soLuongHinhAnh);
                                        }
                                    });
                                } else {
                                    vpHinhAnhChiTietSanPham.setVisibility(View.GONE);
                                    tvSoLuongHinhAnhChiTietSanPham.setVisibility(View.GONE);
                                    ivHinhAnhChiTietSanPham.setVisibility(View.VISIBLE);
                                    Glide.with(this).load(userSanPham.getHinhAnhSanPham()).into(ivHinhAnhChiTietSanPham);
                                }
                            }
                        },
                        throwable -> Log.e("Error", throwable.getMessage())
                ));
    }

    private void xuLySuKienBamNutThem() {
        btThemGioHangChiTietSanPham.setOnClickListener(v -> {
            if (userSanPham != null) {
                // Khởi tạo và hiển thị BottomSheetDialogFragment mới
                UserChiTietSanPhamBottomSheetThemGioHang bottomSheet = new UserChiTietSanPhamBottomSheetThemGioHang(userSanPham, () -> {
                    // Callback: Khi thêm thành công trong BottomSheet, Activity cập nhật lại Badge
                    Utils.caiDatIconSoLuongGioHang(this, flToolbarGioHangChiTietSanPham, nbToolbarGioHangChiTietSanPham);
                });
                bottomSheet.show(getSupportFragmentManager(), "ThemGioHangBottomSheet");
            }
        });
    }
}