package com.example.capyshop.admin.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.capyshop.R;
import com.example.capyshop.admin.danhmuc.AdminQuanLyDanhMucActivity;
import com.example.capyshop.common.activity.BaseActivity;
import com.example.capyshop.common.retrofit.ApiAdmin;
import com.example.capyshop.common.retrofit.RetrofitClient;
import com.example.capyshop.common.utils.Utils;
import com.example.capyshop.user.main.UserMainActivity;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Lớp điều khiển chính cho màn hình Admin
 * File Layout: admin_main_activity.xml
 * Chức năng: Quản lý tổng quan doanh thu, hiển thị biểu đồ sóng (Wave Chart)
 * trượt mượt mà.
 */
public class AdminMainActivity extends BaseActivity {

    // --- Khai báo các thành phần giao diện (UI Components) ---
    Toolbar tbAdminTrangChu;
    CircleImageView civAdminThanhCongCuHinhAnhChinh;
    TextView tvAdminThanhCongCuTen;
    ImageView ivAdminThanhCongCuThongBao;

    TextView tvAdminTongDoanhThu, tvAdminBoLocTongDoanhThu;
    CardView cvAdminTrangChuDonHangChoXacNhan, cvAdminTrangChuDonHangChoGiaoHang;
    TextView tvAdminDonHangChoXacNhan, tvAdminDonHangChoLayHang;
    LinearLayout llAdminDanhMuc, llAdminSanPham, llAdminDonHang, llAdminNguoiDung,
            llAdminDonViVanChuyen, llAdminQuangCao, llAdminPhuongThucThanhToan;
    ImageView ivAdminBoLocTongDoanhThuChinh;

    // Biểu đồ Doanh thu (Sử dụng MPAndroidChart)
    com.github.mikephil.charting.charts.LineChart lcAdminTongDoanhThuChinh;
    AppCompatButton btAdminDangXuat;

    // --- Xử lý dữ liệu và API ---
    ApiAdmin apiAdmin;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    List<AdminTongDoanhThu> mangTongDoanhThu = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.admin_main_activity);
        super.onCreate(savedInstanceState);

        // Khởi tạo các thành phần
        anhXa();
        caiDatToolbar();
        caiDatBieuDoBanDau();
        hienThiThongTinTaiKhoan();
        xuLySuKienClick();
        layDuLieuTongDoanhThu();
    }

    @Override
    protected void onResume() {
        super.onResume();
        layDuLieuTongDoanhThu();
    }

    private void anhXa() {
        tvAdminTongDoanhThu = findViewById(R.id.tv_admin_tong_doanh_thu);
        llAdminDanhMuc = findViewById(R.id.ll_admin_danh_muc);
        llAdminSanPham = findViewById(R.id.ll_admin_san_pham);
        llAdminDonHang = findViewById(R.id.ll_admin_don_hang);
        llAdminNguoiDung = findViewById(R.id.ll_admin_nguoi_dung);
        llAdminDonViVanChuyen = findViewById(R.id.ll_admin_don_vi_van_chuyen);
        llAdminQuangCao = findViewById(R.id.ll_admin_quang_cao);
        llAdminPhuongThucThanhToan = findViewById(R.id.ll_admin_phuong_thuc_thanh_toan);
        lcAdminTongDoanhThuChinh = findViewById(R.id.lc_admin_tong_doanh_thu_chinh);

        btAdminDangXuat = findViewById(R.id.bt_admin_dang_xuat);
        tbAdminTrangChu = findViewById(R.id.tb_admin_trang_chu);
        civAdminThanhCongCuHinhAnhChinh = findViewById(R.id.civ_admin_thanh_cong_cu_hinh_anh_chinh);
        tvAdminThanhCongCuTen = findViewById(R.id.tv_admin_thanh_cong_cu_ten);
        ivAdminThanhCongCuThongBao = findViewById(R.id.iv_admin_thanh_cong_cu_thong_bao);
        cvAdminTrangChuDonHangChoXacNhan = findViewById(R.id.cv_admin_don_hang_cho_xac_nhan);
        cvAdminTrangChuDonHangChoGiaoHang = findViewById(R.id.cv_admin_don_hang_cho_giao_hang);
        tvAdminDonHangChoXacNhan = findViewById(R.id.tv_admin_don_hang_cho_xac_nhan);
        tvAdminDonHangChoLayHang = findViewById(R.id.tv_admin_don_hang_cho_lay_hang);
        tvAdminBoLocTongDoanhThu = findViewById(R.id.tv_admin_bo_loc_tong_doanh_thu);
        ivAdminBoLocTongDoanhThuChinh = findViewById(R.id.iv_admin_bo_loc_tong_doanh_thu_chinh);

        // Kết nối API
        apiAdmin = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiAdmin.class);

    }
    private void caiDatToolbar() {
        setSupportActionBar(tbAdminTrangChu);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }
    }

    //Đưa thông tin tài khoản Admin đang đăng nhập lên Toolbar
    private void hienThiThongTinTaiKhoan() {
        if (Utils.userNguoiDung_Current != null) {
            tvAdminThanhCongCuTen.setText(Utils.userNguoiDung_Current.getHoTenNguoiDung());
            Glide.with(getApplicationContext())
                    .load(Utils.userNguoiDung_Current.getHinhAnhNguoiDung())
                    .placeholder(R.drawable.ic_hinh_anh)
                    .into(civAdminThanhCongCuHinhAnhChinh);
        }
    }

    //Lấy toàn bộ lịch sử doanh thu từ máy chủ
    private void layDuLieuTongDoanhThu() {
        compositeDisposable.add(apiAdmin.layTongDoanhThu()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            if (model.isSuccess()) {
                                mangTongDoanhThu = model.getResult();
                                long tongTien = 0;
                                if (mangTongDoanhThu != null) {
                                    for (AdminTongDoanhThu item : mangTongDoanhThu) {
                                        tongTien += item.getTongTien();
                                    }
                                }
                                DecimalFormat formatTien = new DecimalFormat("###,###,###");
                                tvAdminTongDoanhThu.setText(formatTien.format(tongTien) + " đ");

                                // Mặc định hiện toàn bộ
                                capNhatBieuDo(mangTongDoanhThu, null, null);
                            } else {
                                Toast.makeText(this, model.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        loi -> {
                            Toast.makeText(this, "Không thể kết nối đến Server", Toast.LENGTH_SHORT).show();
                        }));
    }

    private void caiDatBieuDoBanDau() {
        lcAdminTongDoanhThuChinh.getDescription().setEnabled(false);
        lcAdminTongDoanhThuChinh.setDrawGridBackground(false);
        lcAdminTongDoanhThuChinh.setTouchEnabled(true);
        lcAdminTongDoanhThuChinh.setDragEnabled(true); // Cho phép trượt biểu đồ

        // Vô hiệu hóa Zoom để giữ tỷ lệ hiển thị ổn định
        lcAdminTongDoanhThuChinh.setScaleEnabled(false);
        lcAdminTongDoanhThuChinh.setPinchZoom(false);
        lcAdminTongDoanhThuChinh.setDoubleTapToZoomEnabled(false);

        // Tạo khoảng đệm bên dưới để hiện nhãn trục X không bị cắt
        lcAdminTongDoanhThuChinh.setViewPortOffsets(0f, 0f, 0f, 60f);

        // Cấu hình Trục X (Trục nằm ngang)
        XAxis trucX = lcAdminTongDoanhThuChinh.getXAxis();
        trucX.setEnabled(true);
        trucX.setPosition(XAxis.XAxisPosition.BOTTOM);
        trucX.setDrawGridLines(false);
        trucX.setDrawAxisLine(false);
        trucX.setTextSize(10f);
        trucX.setTextColor(ContextCompat.getColor(this, R.color.black));

        // Khắc phục lỗi lặp nhãn: Đảm bảo bước nhảy tối thiểu là 1 đơn vị thời gian
        trucX.setGranularityEnabled(true);
        trucX.setGranularity(1f);
        trucX.setAvoidFirstLastClipping(true);

        // Ẩn trục Y và chú thích để giao diện tối giản, hiện đại
        lcAdminTongDoanhThuChinh.getAxisLeft().setEnabled(false);
        lcAdminTongDoanhThuChinh.getAxisRight().setEnabled(false);
        lcAdminTongDoanhThuChinh.getLegend().setEnabled(false);
    }

    //Hàm hiển thị biểu đồ doanh thu theo khoảng thời gian động
    private void capNhatBieuDo(List<AdminTongDoanhThu> duLieuGoc, Date tuNgay, Date denNgay) {
        if (duLieuGoc == null)
            return;

        ArrayList<com.github.mikephil.charting.data.Entry> danhSachDiem = new ArrayList<>();
        Map<Long, Long> mapDuLieu = new TreeMap<>();

        boolean isFiltering = (tuNgay != null && denNgay != null);

        // 1. Xác định dải thời gian thực tế
        long minKey = Long.MAX_VALUE;
        long maxKey = Long.MIN_VALUE;

        SimpleDateFormat sdfDisplay;
        int stepUnit;

        if (isFiltering) {
            // Chế độ Lọc: Hiển thị theo Tháng
            sdfDisplay = new SimpleDateFormat("MM/yyyy", Locale.getDefault());
            stepUnit = Calendar.MONTH;

            Calendar cStart = Calendar.getInstance();
            cStart.setTime(tuNgay);
            cStart.set(Calendar.DAY_OF_MONTH, 1);
            minKey = lamTronDonViThoiGian(cStart, Calendar.MONTH).getTimeInMillis();

            Calendar cEnd = Calendar.getInstance();
            cEnd.setTime(denNgay);
            cEnd.set(Calendar.DAY_OF_MONTH, 1);
            maxKey = lamTronDonViThoiGian(cEnd, Calendar.MONTH).getTimeInMillis();
        } else {
            // Chế độ Mặc định: Hiển thị theo Năm
            sdfDisplay = new SimpleDateFormat("yyyy", Locale.getDefault());
            stepUnit = Calendar.YEAR;
        }

        // Gom dữ liệu thô
        Map<Long, Long> rawData = new HashMap<>();
        for (AdminTongDoanhThu item : duLieuGoc) {
            try {
                SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date dateItem = sdfInput.parse(item.getThoiGianTao().split(" ")[0]);

                if (isFiltering && (dateItem.before(tuNgay) || dateItem.after(denNgay)))
                    continue;

                Calendar cal = lamTronDonViThoiGian(dateItem, stepUnit);
                long key = cal.getTimeInMillis();

                if (!isFiltering) {
                    if (key < minKey)
                        minKey = key;
                    if (key > maxKey)
                        maxKey = key;
                }

                rawData.put(key, (rawData.getOrDefault(key, 0L)) + item.getTongTien());
            } catch (Exception e) {
            }
        }

        // 2. Điền đầy các mốc bị khuyết để đảm bảo khoảng cách đều đặn
        if (minKey != Long.MAX_VALUE) {
            Calendar current = Calendar.getInstance();
            current.setTimeInMillis(minKey);

            // Nếu chỉ có 1 điểm, thêm 1 điểm phía trước cho đẹp biểu đồ
            if (minKey == maxKey && !isFiltering) {
                current.add(stepUnit, -1);
                minKey = current.getTimeInMillis();
            }

            current.setTimeInMillis(minKey);
            while (current.getTimeInMillis() <= maxKey) {
                long key = current.getTimeInMillis();
                mapDuLieu.put(key, rawData.getOrDefault(key, 0L));
                current.add(stepUnit, 1);
            }
        }

        // 3. Chuyển sang danh sách điểm cho biểu đồ
        int index = 0;
        final List<String> labels = new ArrayList<>();
        for (Map.Entry<Long, Long> entry : mapDuLieu.entrySet()) {
            danhSachDiem.add(new com.github.mikephil.charting.data.Entry(index, (float) entry.getValue()));
            labels.add(sdfDisplay.format(new Date(entry.getKey())));
            index++;
        }

        // 4. Định dạng nhãn trục X
        lcAdminTongDoanhThuChinh.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float giaTri) {
                int i = (int) giaTri;
                if (i >= 0 && i < labels.size())
                    return labels.get(i);
                return "";
            }
        });
        lcAdminTongDoanhThuChinh.getXAxis().setGranularity(1f);

        // 5. Thiết lập dataset sóng
        com.github.mikephil.charting.data.LineDataSet boDuLieu = new com.github.mikephil.charting.data.LineDataSet(
                danhSachDiem, "Doanh Thu");
        int mauChinh = ContextCompat.getColor(this, R.color.teal_700);

        boDuLieu.setMode(com.github.mikephil.charting.data.LineDataSet.Mode.CUBIC_BEZIER);
        boDuLieu.setDrawFilled(true);
        boDuLieu.setDrawCircles(true);
        boDuLieu.setCircleColor(mauChinh);
        boDuLieu.setCircleRadius(4f);
        boDuLieu.setDrawCircleHole(false);
        boDuLieu.setLineWidth(3f);
        boDuLieu.setColor(mauChinh);
        boDuLieu.setFillColor(mauChinh);
        boDuLieu.setFillAlpha(40);
        boDuLieu.setDrawValues(false);
        lcAdminTongDoanhThuChinh.setData(new com.github.mikephil.charting.data.LineData(boDuLieu));
        lcAdminTongDoanhThuChinh.fitScreen(); // Reset trạng thái zoom/stretch cũ
        lcAdminTongDoanhThuChinh.setVisibleXRangeMaximum(5f);

        lcAdminTongDoanhThuChinh.moveViewToX(index);
        lcAdminTongDoanhThuChinh.animateX(800);
        lcAdminTongDoanhThuChinh.invalidate();
    }

    private Calendar lamTronDonViThoiGian(Date date, int unit) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (unit == Calendar.YEAR) {
            cal.set(Calendar.MONTH, 0);
        }
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    private Calendar lamTronDonViThoiGian(Calendar calInput, int unit) {
        Calendar cal = (Calendar) calInput.clone();
        if (unit == Calendar.YEAR) {
            cal.set(Calendar.MONTH, 0);
        }
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

     //Xử lý khi nhấn Áp dụng từ BottomSheet
    private void xuLyLocTheoKhoangThoiGian(Date tuNgay, Date denNgay) {
        if (mangTongDoanhThu == null)
            return;

        long tongTienHienThi = 0;
        DecimalFormat dinhDang = new DecimalFormat("###,###,###");

        if (tuNgay != null && denNgay != null) {
            SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat sdfOutput = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

            for (AdminTongDoanhThu item : mangTongDoanhThu) {
                try {
                    Date dateItem = sdfInput.parse(item.getThoiGianTao().split(" ")[0]);
                    if (!dateItem.before(tuNgay) && !dateItem.after(denNgay)) {
                        tongTienHienThi += item.getTongTien();
                    }
                } catch (Exception e) {
                }
            }
            tvAdminBoLocTongDoanhThu.setText("Doanh thu " + sdfOutput.format(tuNgay) + " - " + sdfOutput.format(denNgay));
        } else {
            // Trường hợp Reset: Tính lại tổng doanh thu thực tế
            for (AdminTongDoanhThu item : mangTongDoanhThu) {
                tongTienHienThi += item.getTongTien();
            }
            tvAdminBoLocTongDoanhThu.setText("Tổng doanh thu");
        }

        tvAdminTongDoanhThu.setText(dinhDang.format(tongTienHienThi) + " đ");
        capNhatBieuDo(mangTongDoanhThu, tuNgay, denNgay);
    }

    /**
     * Đăng ký sự kiện Click cho các nút chức năng quản lý
     */
    private void xuLySuKienClick() {
        ivAdminThanhCongCuThongBao.setOnClickListener(v -> Toast.makeText(this, "Thông báo", Toast.LENGTH_SHORT).show());
        llAdminDanhMuc.setOnClickListener(v -> startActivity(new Intent(this, AdminQuanLyDanhMucActivity.class)));
        llAdminQuangCao.setOnClickListener(v -> startActivity(
                new Intent(this, com.example.capyshop.admin.quangcao.AdminQuanLyQuangCaoActivity.class)));
        llAdminDonViVanChuyen.setOnClickListener(v -> startActivity(
                new Intent(this, com.example.capyshop.admin.donvivanchuyen.AdminQuanLyDonViVanChuyenActivity.class)));
        llAdminSanPham.setOnClickListener(
                v -> startActivity(
                        new Intent(this, com.example.capyshop.admin.sanpham.AdminQuanLySanPhamActivity.class)));
        llAdminDonHang.setOnClickListener(v -> startActivity(
                new Intent(this, com.example.capyshop.admin.donhang.AdminQuanLyDonHangActivity.class)));
        llAdminNguoiDung.setOnClickListener(v -> startActivity(
                new Intent(this, com.example.capyshop.admin.nguoidung.AdminQuanLyNguoiDungActivity.class)));
        llAdminPhuongThucThanhToan.setOnClickListener(v -> startActivity(
                new Intent(this,
                        com.example.capyshop.admin.phuongthucthanhtoan.AdminQuanLyPhuongThucThanhToanActivity.class)));

        cvAdminTrangChuDonHangChoXacNhan
                .setOnClickListener(v -> Toast.makeText(this, "Chờ xác nhận", Toast.LENGTH_SHORT).show());
        cvAdminTrangChuDonHangChoGiaoHang
                .setOnClickListener(v -> Toast.makeText(this, "Chờ giao hàng", Toast.LENGTH_SHORT).show());

        // Mở bộ lọc mới
        ivAdminBoLocTongDoanhThuChinh.setOnClickListener(v -> {
            new AdminMainBottomSheetBoLoc(this::xuLyLocTheoKhoangThoiGian)
                    .show(getSupportFragmentManager(), "FilterRange");
        });

        btAdminDangXuat.setOnClickListener(v -> {
            Utils.thietLapBottomSheetDialog(AdminMainActivity.this, "Đăng xuất?", "Bạn có chắc muốn thoát không?",
                    "Đăng xuất", () -> {
                        Paper.book().destroy();
                        Utils.userNguoiDung_Current = null;
                        Intent intent = new Intent(getApplicationContext(), UserMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    });
        });
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
