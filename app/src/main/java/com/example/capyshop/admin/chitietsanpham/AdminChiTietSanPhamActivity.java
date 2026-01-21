package com.example.capyshop.admin.chitietsanpham;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.capyshop.R;
import com.example.capyshop.common.sanpham.SanPham;
import com.example.capyshop.common.activity.BaseActivity;
import com.example.capyshop.common.chitietsanpham.ChiTietSanPhamAlbumAdapter;
import com.example.capyshop.common.retrofit.ApiAdmin;
import com.example.capyshop.common.retrofit.RetrofitClient;
import com.example.capyshop.common.utils.Utils;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdminChiTietSanPhamActivity extends BaseActivity {
    private Toolbar tbAdminChiTietSanPham;
    private View vAdminChiTietSanPhamThanhCongCu;
    private TextView tvAdminChiTietSanPhamTieuDeThanhCongCu;
    private NestedScrollView nsvAdminChiTietSanPham;
    private ImageView ivAdminChiTietXoaSanPham;
    private ViewPager2 vpAdminChiTietHinhAnhSanPham;
    private ImageView ivAdminChiTietHinhAnhGiuCho;
    private TextView tvAdminChiTietSoLuongAnh;
    private TextView tvAdminChiTietThuongHieuSanPham;
    private TextView tvAdminChiTietTenSanPham;
    private TextView tvAdminChiTietGiaSanPham;
    private TextView tvAdminChiTietTonKhoSanPham;
    private RecyclerView rvAdminChiTietDanhSachThuocTinh;
    private TextView tvAdminChiTietMoTaSanPham;

    SanPham adminQuanLySanPham;
    private int maSanPham;
    private ApiAdmin apiAdmin;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_chitietsanpham_activity);

        apiAdmin = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiAdmin.class);
        maSanPham = getIntent().getIntExtra("maSanPham", -1);

        anhXa();
        caiDatToolbar();
        hienThiChiTietSanPham();
        xuLySuKienClicKXoaSanPham();

    }

    private void anhXa() {
        tbAdminChiTietSanPham = findViewById(R.id.tb_admin_chi_tiet_san_pham);
        vAdminChiTietSanPhamThanhCongCu = findViewById(R.id.v_admin_chi_tiet_san_pham_thanh_cong_cu);
        tvAdminChiTietSanPhamTieuDeThanhCongCu = findViewById(R.id.tv_admin_chi_tiet_san_pham_tieu_de_thanh_cong_cu);
        nsvAdminChiTietSanPham = findViewById(R.id.nsv_admin_chi_tiet_san_pham);
        ivAdminChiTietXoaSanPham = findViewById(R.id.iv_admin_chi_tiet_xoa_san_pham);
        vpAdminChiTietHinhAnhSanPham = findViewById(R.id.vp_admin_chi_tiet_hinh_anh_san_pham);
        ivAdminChiTietHinhAnhGiuCho = findViewById(R.id.iv_admin_chi_tiet_hinh_anh_giu_cho);
        tvAdminChiTietSoLuongAnh = findViewById(R.id.tv_admin_chi_tiet_so_luong_anh);
        tvAdminChiTietThuongHieuSanPham = findViewById(R.id.tv_admin_chi_tiet_thuong_hieu_san_pham);
        tvAdminChiTietTenSanPham = findViewById(R.id.tv_admin_chi_tiet_ten_san_pham);
        tvAdminChiTietGiaSanPham = findViewById(R.id.tv_admin_chi_tiet_gia_san_pham);
        tvAdminChiTietTonKhoSanPham = findViewById(R.id.tv_admin_chi_tiet_ton_kho_san_pham);
        rvAdminChiTietDanhSachThuocTinh = findViewById(R.id.rv_admin_chi_tiet_danh_sach_thuoc_tinh);
        tvAdminChiTietMoTaSanPham = findViewById(R.id.tv_admin_chi_tiet_mo_ta_san_pham);
        //
        rvAdminChiTietDanhSachThuocTinh.setLayoutManager(new LinearLayoutManager(this));
    }

    private void caiDatToolbar() {
        setSupportActionBar(tbAdminChiTietSanPham);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
        tbAdminChiTietSanPham.setNavigationOnClickListener(v -> finish());

        vAdminChiTietSanPhamThanhCongCu.setAlpha(0);
        tvAdminChiTietSanPhamTieuDeThanhCongCu.setAlpha(0);

        final int maxScroll = 400;
        nsvAdminChiTietSanPham.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            float alpha = Math.min(1f, (float) scrollY / maxScroll);
            vAdminChiTietSanPhamThanhCongCu.setAlpha(alpha);
            tvAdminChiTietSanPhamTieuDeThanhCongCu.setAlpha(alpha);
        });
    }

    private void hienThiChiTietSanPham() {
        compositeDisposable.add(apiAdmin.layChiTietSanPham(maSanPham)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        adminQuanLySanPhamModel -> {
                            if (adminQuanLySanPhamModel.isSuccess() && adminQuanLySanPhamModel.getResult() != null && !adminQuanLySanPhamModel.getResult().isEmpty()) {
                                // 1. Basic Info
                                adminQuanLySanPham = adminQuanLySanPhamModel.getResult().get(0);
                                tvAdminChiTietThuongHieuSanPham.setText(adminQuanLySanPham.getTenThuongHieu() != null ? adminQuanLySanPham.getTenThuongHieu() : "Chưa cập nhật");
                                tvAdminChiTietTenSanPham.setText(adminQuanLySanPham.getTenSanPham());
                                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                                tvAdminChiTietGiaSanPham.setText(decimalFormat.format(adminQuanLySanPham.getGiaSanPham()) + "₫");
                                tvAdminChiTietTonKhoSanPham.setText(adminQuanLySanPham.getSoLuongTon() + " đơn vị");
                                tvAdminChiTietMoTaSanPham.setText(adminQuanLySanPham.getMoTaSanPham());

                                // 2. Images (Album)
                                if (adminQuanLySanPham.getAlbum() != null && !adminQuanLySanPham.getAlbum().isEmpty()) {
                                    vpAdminChiTietHinhAnhSanPham.setVisibility(View.VISIBLE);
                                    ivAdminChiTietHinhAnhGiuCho.setVisibility(View.GONE);
                                    tvAdminChiTietSoLuongAnh.setVisibility(View.VISIBLE);

                                    int totalImages = adminQuanLySanPham.getAlbum().size();
                                    tvAdminChiTietSoLuongAnh.setText("1/" + totalImages);

                                    ChiTietSanPhamAlbumAdapter adapter = new ChiTietSanPhamAlbumAdapter(this, adminQuanLySanPham.getAlbum());
                                    vpAdminChiTietHinhAnhSanPham.setAdapter(adapter);

                                    vpAdminChiTietHinhAnhSanPham.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                        @Override
                                        public void onPageSelected(int position) {
                                            tvAdminChiTietSoLuongAnh.setText((position + 1) + "/" + totalImages);
                                        }
                                    });

                                } else if (adminQuanLySanPham.getHinhAnhSanPham() != null && !adminQuanLySanPham.getHinhAnhSanPham().isEmpty()) {
                                    // Fallback to main image
                                    vpAdminChiTietHinhAnhSanPham.setVisibility(View.GONE);
                                    ivAdminChiTietHinhAnhGiuCho.setVisibility(View.VISIBLE);
                                    tvAdminChiTietSoLuongAnh.setVisibility(View.GONE);
                                    Glide.with(this).load(adminQuanLySanPham.getHinhAnhSanPham()).into(ivAdminChiTietHinhAnhGiuCho);
                                } else {
                                    // No image at all
                                    vpAdminChiTietHinhAnhSanPham.setVisibility(View.GONE);
                                    ivAdminChiTietHinhAnhGiuCho.setVisibility(View.VISIBLE);
                                    tvAdminChiTietSoLuongAnh.setVisibility(View.GONE);
                                    ivAdminChiTietHinhAnhGiuCho.setImageResource(R.drawable.ic_hinh_anh); // Default placeholder
                                }

                                // 3. Attributes
                                if (adminQuanLySanPham.getThuocTinh() != null) {
                                    AdminChiTietSanPhamThuocTinhAdapter thuocTinhAdapter = new AdminChiTietSanPhamThuocTinhAdapter(this,
                                            adminQuanLySanPham.getThuocTinh());
                                    rvAdminChiTietDanhSachThuocTinh.setAdapter(thuocTinhAdapter);
                                }
                            } else {
                                Toast.makeText(this, "Không thể tải dữ liệu sản phẩm", Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> Toast.makeText(this, "Lỗi kết nối: " + throwable.getMessage(), Toast.LENGTH_SHORT)
                                .show()));
    }

    private void xuLySuKienClicKXoaSanPham() {
        ivAdminChiTietXoaSanPham.setOnClickListener(v -> {
            Utils.thietLapBottomSheetDialog(
                    this,
                    "Xác nhận xóa",
                    "Bạn có chắc chắn muốn xóa sản phẩm không?",
                    "Xóa ngay", () -> {
                        compositeDisposable.add(apiAdmin.xoaSanPham(maSanPham)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        model -> {
                                            if (model.isSuccess()) {
                                                Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                                setResult(RESULT_OK); // Notify back to List Activity
                                                finish();
                                            } else {
                                                Toast.makeText(this, model.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        },
                                        throwable -> Toast.makeText(this, "Lỗi: " + throwable.getMessage(), Toast.LENGTH_SHORT)
                                                .show()));
                    });
        });
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
