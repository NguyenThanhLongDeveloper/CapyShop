package com.example.capyshop.admin.chitietdonhang;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capyshop.R;
import com.example.capyshop.common.activity.BaseActivity;
import com.example.capyshop.common.chitietdonhang.ChiTietDonHang;
import com.example.capyshop.common.donhang.DonHang;
import com.example.capyshop.common.retrofit.ApiAdmin;
import com.example.capyshop.common.retrofit.RetrofitClient;
import com.example.capyshop.common.utils.Utils;

import java.text.DecimalFormat;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class AdminChiTietDonHangActivity extends BaseActivity {

    private androidx.appcompat.widget.Toolbar tbChiTietDonHang;
    private TextView tvChiTietMaDonHang, tvChiTietTrangThai, tvChiTietNgayDat, tvChiTietTenKhachHang, tvChiTietSoDienThoai, btChiTietGoi, tvChiTietDiaChi;
    private TextView tvChiTietSoLuongSanPham, tvChiTietThanhToan, tvChiTietVanChuyen, tvChiTietTongQuanSoLuong, tvChiTietTamTinh, tvChiTietTongCong, tvChiTietPhiVanChuyen;
    private RecyclerView rvChiTietSanPham;
    private DonHang donHang;
    private AdminChiTietDonHangAdapter adapter;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiAdmin apiAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_chitietdonhang_activity);
        apiAdmin = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiAdmin.class);

        // Get data
        if (getIntent() != null && getIntent().getSerializableExtra("donhang") != null) {
            donHang = (DonHang) getIntent().getSerializableExtra("donhang");
        }

        anhXa();
        xuLySuKien();
        if (donHang != null) {
            setupData();
        }
    }

    private void anhXa() {
        tbChiTietDonHang = findViewById(R.id.tb_admin_chi_tiet_don_hang);
        setSupportActionBar(tbChiTietDonHang);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        tvChiTietMaDonHang = findViewById(R.id.tv_admin_chi_tiet_ma_don_hang);
        tvChiTietTrangThai = findViewById(R.id.tv_admin_chi_tiet_trang_thai);
        tvChiTietNgayDat = findViewById(R.id.tv_admin_chi_tiet_ngay_dat);
        tvChiTietTenKhachHang = findViewById(R.id.tv_admin_chi_tiet_ten_khach_hang);
        tvChiTietSoDienThoai = findViewById(R.id.tv_admin_chi_tiet_so_dien_thoai);
        btChiTietGoi = findViewById(R.id.bt_admin_chi_tiet_goi);
        tvChiTietDiaChi = findViewById(R.id.tv_admin_chi_tiet_dia_chi);
        tvChiTietSoLuongSanPham = findViewById(R.id.tv_admin_chi_tiet_so_luong_san_pham);
        tvChiTietThanhToan = findViewById(R.id.tv_admin_chi_tiet_thanh_toan);
        tvChiTietVanChuyen = findViewById(R.id.tv_admin_chi_tiet_van_chuyen);
        tvChiTietTongQuanSoLuong = findViewById(R.id.tv_admin_chi_tiet_tong_quan_so_luong);
        tvChiTietPhiVanChuyen = findViewById(R.id.tv_admin_chi_tiet_phi_van_chuyen);
        tvChiTietTamTinh = findViewById(R.id.tv_admin_chi_tiet_tam_tinh);
        tvChiTietTongCong = findViewById(R.id.tv_admin_chi_tiet_tong_cong);
        rvChiTietSanPham = findViewById(R.id.rv_admin_chi_tiet_san_pham);

        // Setup RecyclerView
        rvChiTietSanPham.setLayoutManager(new LinearLayoutManager(this));
    }

    private void xuLySuKien() {
        setSupportActionBar(tbChiTietDonHang);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }
        tbChiTietDonHang.setNavigationOnClickListener(v -> finish());

        btChiTietGoi.setOnClickListener(v -> {
            if (donHang != null && donHang.getSoDienThoai() != null) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + donHang.getSoDienThoai()));
                startActivity(intent);
            }
        });
    }

    private void setupData() {
        // Header
        tvChiTietMaDonHang.setText("#DH-" + donHang.getMaDonHang());
        tvChiTietNgayDat.setText(donHang.getThoiGianTao()); // Assuming formatted date

        // Status
        tvChiTietTrangThai.setText(layChuoiTrangThai(donHang.getTrangThai()));

        // Customer Info
        tvChiTietTenKhachHang.setText(donHang.getHoTenNguoiDung()); // Or tenNguoiDung if different
        tvChiTietSoDienThoai.setText(donHang.getSoDienThoai());
        tvChiTietDiaChi.setText(donHang.getDiaChi());

        // Payment & Shipping
        tvChiTietThanhToan.setText(donHang.getTenPhuongThucThanhToan());
        tvChiTietVanChuyen.setText(donHang.getTenDonViVanChuyen());

        // Products
        List<ChiTietDonHang> list = donHang.getChitietdonhang();
        if (list != null) {
            tvChiTietSoLuongSanPham.setText("SẢN PHẨM (" + list.size() + ")");
            tvChiTietTongQuanSoLuong.setText("Tạm tính (" + list.size() + " món)");
            adapter = new AdminChiTietDonHangAdapter(this, list);
            rvChiTietSanPham.setAdapter(adapter);
        }

        // Financials
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

        long tongTien = donHang.getTongTien();
        long phiVanChuyen = donHang.getPhiVanChuyen();
        long tamTinh = tongTien - phiVanChuyen;

        tvChiTietPhiVanChuyen.setText(decimalFormat.format(phiVanChuyen) + " đ");
        tvChiTietTamTinh.setText(decimalFormat.format(tamTinh) + " đ");
        tvChiTietTongCong.setText(decimalFormat.format(tongTien) + " đ");
    }

    private String layChuoiTrangThai(String status) {
        switch (status) {
            case "CHO_XAC_NHAN":
                return "CHỜ XÁC NHẬN";
            case "CHO_LAY_HANG":
                return "CHỜ LẤY HÀNG";
            case "DANG_GIAO_HANG":
                return "ĐANG GIAO HÀNG";
            case "DA_GIAO_HANG":
                return "ĐÃ GIAO HÀNG";
            case "DA_HUY":
                return "ĐÃ HỦY";
            default:
                return "ĐANG XỬ LÝ";
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
