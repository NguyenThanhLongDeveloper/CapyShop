package com.example.capyshop.user.dathang;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capyshop.R;
import com.example.capyshop.common.activity.BaseActivity;
import com.example.capyshop.common.retrofit.ApiCommon;
import com.example.capyshop.common.retrofit.ApiUser;
import com.example.capyshop.common.retrofit.Authorization;
import com.example.capyshop.common.retrofit.RetrofitClient;
import com.example.capyshop.common.retrofit.RetrofitClientThongBao;
import com.example.capyshop.common.thongbao.GuiThongBao;
import com.example.capyshop.common.thongbao.Message;
import com.example.capyshop.common.thongbao.Notification;
import com.example.capyshop.common.utils.Utils;
import com.example.capyshop.user.donhang.UserDonHangActivity;
import com.example.capyshop.user.giohang.UserGioHang;
import com.example.capyshop.user.main.UserMainActivity;
import com.example.capyshop.user.thongtincanhan.UserThongTinCaNhanBottomSheetCaiDatTaiKhoan;
import com.google.gson.Gson;

import org.apache.commons.logging.LogFactory;

import java.text.DecimalFormat;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;

public class UserDatHangActivity extends BaseActivity {
    private static final org.apache.commons.logging.Log log = LogFactory.getLog(UserDatHangActivity.class);
    Toolbar tbDatHang;
    TextView tvTongTienDatHang, tvHoVaTenDatHang, tvSoDienThoaiDatHang, tvDiaChiDatHang;
    CardView cvThongTinCaNhanDatHang;
    RecyclerView rvDanhSachSanPhamDatHang, rvDonViVanChuyenDatHang, rvPhuongThucThanhToanDatHang;
    AppCompatButton btDatHang;
    // Khai báo Retrofit Service và RxJava Disposable
    ApiUser apiUser;
    ApiCommon apiCommon;

    OkHttpClient okHttpClient;
    // Quản lý các luồng RxJava để tránh rò rỉ bộ nhớ
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    UserDatHangDanhSachSanPhamAdapter userDatHangDanhSachSanPhamAdapter;
    UserPhuongThucThanhToanAdapter userPhuongThucThanhToanAdapter;
    UserDonViVanChuyenAdapter userDonViVanChuyenAdapter;
    //bien toan cuc cho tong tien
    long giaDonViVanChuyen = 0; // Biến lưu giá vận chuyển đang chọn
    long tongTienGoc = 0;
    long tongTien = 0; // Biến lưu tiền hàng (chưa có ship)
    
    // bien toan cuc cho so luong san pham
    int soLuong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.user_dathang_activity);
        super.onCreate(savedInstanceState);
        anhXa();    // Ánh xạ UI
        caiDatToolbar();    // Cấu hình Toolbar
        tinhSoLuong();  // Tính số lượng sản phẩm trong giỏ hàng
        hienThiDonViVanChuyen();
        hienThiPhuongThucThanhToan();
        hienThiThongTinDonHang(); // day thong tin tu gio hang len trang dat hang va lay thong tin nguoi dung
        xuLySuKienDatHang();    // Xu ly su kien cho button dat hang

    }

    // Dọn dẹp tất cả các Disposable trong RxJava để tránh rò rỉ bộ nhớ khi Activity bị hủy
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    // Anh xa UI tu file xml
    private void anhXa() {
        tbDatHang = findViewById(R.id.tb_dat_hang);
        tvTongTienDatHang = findViewById(R.id.tv_tong_tien_dat_hang);
        rvDanhSachSanPhamDatHang = findViewById(R.id.rv_danh_sach_san_pham_dat_hang);
        rvDonViVanChuyenDatHang = findViewById(R.id.rv_don_vi_van_chuyen_dat_hang);
        rvPhuongThucThanhToanDatHang = findViewById(R.id.rv_phuong_thuc_thanh_toan_dat_hang);
        tvHoVaTenDatHang = findViewById(R.id.tv_ho_va_ten_dat_hang);
        tvSoDienThoaiDatHang = findViewById(R.id.tv_so_dien_thoai_dat_hang);
        tvDiaChiDatHang = findViewById(R.id.tv_dia_chi_dat_hang);
        cvThongTinCaNhanDatHang = findViewById(R.id.cv_thong_tin_ca_nhan_dat_hang);
        btDatHang = findViewById(R.id.bt_dat_hang);
        // Khởi tạo Retrofit client để giao tiếp với API
        apiUser = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiUser.class);
        apiCommon = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiCommon.class);
        // Cấu hình RecyclerView
        rvDanhSachSanPhamDatHang.setHasFixedSize(true);
        rvDanhSachSanPhamDatHang.setLayoutManager(new LinearLayoutManager(this));
        rvDonViVanChuyenDatHang.setHasFixedSize(true);
        rvDonViVanChuyenDatHang.setLayoutManager(new LinearLayoutManager(this));
        rvPhuongThucThanhToanDatHang.setHasFixedSize(true);
        rvPhuongThucThanhToanDatHang.setLayoutManager(new LinearLayoutManager(this));


    }

    // Cấu hình Toolbar
    private void caiDatToolbar() {
        setSupportActionBar(tbDatHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        tbDatHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.thietLapBottomSheetDialog(UserDatHangActivity.this, "Xác nhận rời đi",
                        "Bạn có chắc muốn rời khỏi bước đặt hàng?", "Đồng ý", () -> {
                            finish();
                        });

            }
        });
    }

    //tinh so luong san pham trong gio hang
    private void tinhSoLuong() {
        soLuong = 0;
        for (int i = 0; i < Utils.mangMuaHang.size(); i++) {
            soLuong = soLuong + Utils.mangMuaHang.get(i).getSoLuong();
        }
    }

    private void hienThiDonViVanChuyen() {
        compositeDisposable.add(
                apiUser.layDonViVanChuyen()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                donViVanChuyenModel -> {
                                    if (donViVanChuyenModel.isSuccess()) {
                                        userDonViVanChuyenAdapter = new UserDonViVanChuyenAdapter(
                                                this,
                                                donViVanChuyenModel.getResult(),
                                                -1
                                        );
                                        rvDonViVanChuyenDatHang.setAdapter(userDonViVanChuyenAdapter);
                                    }
                                },
                                throwable -> {
                                    Toast.makeText(this, "Lỗi tải đơn vị vận chuyển", Toast.LENGTH_SHORT).show();
                                }
                        )
        );
    }

    private void hienThiPhuongThucThanhToan() {
        compositeDisposable.add(
                apiUser.layPhuongThucThanhToan()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                phuongThucThanhToanModel -> {
                                    if (phuongThucThanhToanModel.isSuccess()) {
                                        userPhuongThucThanhToanAdapter = new UserPhuongThucThanhToanAdapter(
                                                this,
                                                phuongThucThanhToanModel.getResult(),
                                                -1
                                        );
                                        rvPhuongThucThanhToanDatHang.setAdapter(userPhuongThucThanhToanAdapter);
                                    }
                                },
                                throwable -> {
                                    Log.e("PTTT_ERROR", throwable.getMessage());
                                    Toast.makeText(this, "Lỗi tải phương thức thanh toán", Toast.LENGTH_SHORT).show();
                                }
                        )
        );
    }

    //hien thi thong tin don hang va nguoi dung
    private void hienThiThongTinDonHang() {
        // Định dạng và hiển thị tổng tiền
        tongTienGoc = getIntent().getLongExtra("tongtien", 0);

        // Ban đầu tổng thanh toán = tiền hàng (vì chưa chọn ship)
        capNhatTongThanhToan();

        // hien thi teen va so dien thoai
        tvHoVaTenDatHang.setText(Utils.userNguoiDung_Current.getHoTenNguoiDung());
        tvSoDienThoaiDatHang.setText(Utils.userNguoiDung_Current.getSoDienThoai());
        if (TextUtils.isEmpty(Utils.userNguoiDung_Current.getDiaChi())) {
            tvDiaChiDatHang.setText("Vui lòng nhập địa chỉ đặt hàng");
        } else {
            tvDiaChiDatHang.setText(Utils.userNguoiDung_Current.getDiaChi());
        }

        cvThongTinCaNhanDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khởi tạo BottomSheet với Interface nhận 3 tham số
                UserThongTinCaNhanBottomSheetCaiDatTaiKhoan bottomSheet = new UserThongTinCaNhanBottomSheetCaiDatTaiKhoan(new UserThongTinCaNhanBottomSheetCaiDatTaiKhoan.OnAddressSaveListener() {
                    @Override
                    public void onSave(String hoVaTen, String soDienThoai, String diaChi) {
                        int maNguoiDung = Utils.userNguoiDung_Current.getMaNguoiDung();
                        // 1. Cập nhật UI (Hiển thị địa chỉ lên TextView)
                        compositeDisposable.add(apiUser.capNhapThongTinCaNhan(maNguoiDung, hoVaTen, soDienThoai, diaChi, Utils.userNguoiDung_Current.getHinhAnhNguoiDung())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        nguoiDungModel -> {
                                            if (nguoiDungModel.isSuccess()) {
                                                Utils.userNguoiDung_Current = nguoiDungModel.getResult().get(0);
                                                Paper.book().write("user", Utils.userNguoiDung_Current);
                                                Toast.makeText(getApplicationContext(), "Thêm địa chỉ thành công", Toast.LENGTH_SHORT).show();
                                                hienThiThongTinDonHang();
                                            } else {
                                                Toast.makeText(getApplicationContext(), nguoiDungModel.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        },
                                        throwable -> {
                                            Log.e("API_ERROR", "Lỗi thêm địa chỉ: " + throwable.getMessage());
                                        }
                                ));
                    }
                });

                bottomSheet.show(getSupportFragmentManager(), "ThemDiaChi");
            }
        });

        userDatHangDanhSachSanPhamAdapter = new UserDatHangDanhSachSanPhamAdapter(this, Utils.mangMuaHang);
        rvDanhSachSanPhamDatHang.setAdapter(userDatHangDanhSachSanPhamAdapter);

    }

    public void giaDonViVanChuyen(long gia) {
        this.giaDonViVanChuyen = gia;
    }

    public void capNhatTongThanhToan() {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        // Tổng thanh toán = Tiền hàng + Giá vận chuyển
        tongTien = tongTienGoc + giaDonViVanChuyen;

        // Cập nhật lên TextView
        tvTongTienDatHang.setText(decimalFormat.format(tongTien) + "đ");

    }

    // xu ly su kien cho button dat hang
    private  void xuLySuKienDatHang() {
        btDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userPhuongThucThanhToanAdapter == null ||
                        userPhuongThucThanhToanAdapter.getMaPhuongThucThanhToanDangChon() == -1) {
                    Toast.makeText(getApplicationContext(), "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (userDonViVanChuyenAdapter == null ||
                        userDonViVanChuyenAdapter.getDonViVanChuyenDangChon() == null) {
                    Toast.makeText(getApplicationContext(), "Vui lòng chọn đơn vị vận chuyển", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Lấy dữ liệu từ các trường nhập liệu và loại bỏ khoảng trắng thừa
                String hoVaTenNguoiDung = tvHoVaTenDatHang.getText().toString().trim();
                String soDienThoai = tvSoDienThoaiDatHang.getText().toString().trim();
                String diaChi = tvDiaChiDatHang.getText().toString().trim();

                //lay du lieu nguoi dung hien tai va so luong san pham trong gio hang
                int maNguoiDung = Utils.userNguoiDung_Current.getMaNguoiDung();
                int maPhuongThucThanhToan = userPhuongThucThanhToanAdapter.getMaPhuongThucThanhToanDangChon();
                int maDonViVanChuyen = userDonViVanChuyenAdapter.getDonViVanChuyenDangChon().getMaDonViVanChuyen();
                //lay du lieu san pham trong gio hang duoi dang json
                String chiTietDonHang = new Gson().toJson(Utils.mangMuaHang);

                // Bắt đầu chuỗi Validation (Kiểm tra dữ liệu đầu vào)
                if (TextUtils.isEmpty(Utils.userNguoiDung_Current.getDiaChi())) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập địa chỉ nhận hàng", Toast.LENGTH_SHORT).show();
                }else {
                    // post dữ liệu đơn hàng lên database
                    compositeDisposable.add(apiUser.guiDonHang(maNguoiDung, maPhuongThucThanhToan, maDonViVanChuyen, hoVaTenNguoiDung, soDienThoai, diaChi, String.valueOf(tongTien), soLuong, chiTietDonHang)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    donHangModel -> {
                                        if (donHangModel.isSuccess()) {
                                            guiThongBaoDatHangThanhCong();

                                            // 1. Duyệt qua danh sách sản phẩm vừa mua để xóa trên Server
                                            for (UserGioHang itemMua : Utils.mangMuaHang) {
                                                compositeDisposable.add(apiUser.xoaGioHang(maNguoiDung, itemMua.getMaGioHang()) // Dùng maGioHang chính xác hơn maSanPham
                                                        .subscribeOn(Schedulers.io())
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe(
                                                                res -> Log.d("DeleteCart", "Đã xóa: " + itemMua.getMaGioHang()),
                                                                throwable -> Log.e("DeleteCartError", throwable.getMessage())
                                                        ));
                                            }

                                            // 2. Cập nhật danh sách giỏ hàng tổng (Utils.mangGioHang)
                                            // Loại bỏ những món đã mua khỏi giỏ hàng chung
                                            for (UserGioHang itemMua : Utils.mangMuaHang) {
                                                for (int j = 0; j < Utils.mangGioHang.size(); j++) {
                                                    if (Utils.mangGioHang.get(j).getMaGioHang() == itemMua.getMaGioHang()) {
                                                        Utils.mangGioHang.remove(j);
                                                        break;
                                                    }
                                                }
                                            }

                                            // 3. Giải phóng danh sách mua hàng tạm thời
                                            Utils.mangMuaHang.clear();

                                            // 4. Hien thi BottomSheet Thanh Cong
                                            UserDatHangThanhCongBottomSheet bottomSheet = new UserDatHangThanhCongBottomSheet(new UserDatHangThanhCongBottomSheet.OnActionListener() {
                                                @Override
                                                public void onTiepTucMuaSam() {
                                                    Intent intent = new Intent(getApplicationContext(), UserMainActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                }

                                                @Override
                                                public void onXemChiTietDonHang() {
                                                    // Chuyen den man hinh lich su don hang
                                                    String trangThai = "CHO_XAC_NHAN";
                                                    Intent intent = new Intent(getApplicationContext(), UserDonHangActivity.class);
                                                    intent.putExtra("trangthai", trangThai);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                    finish();

                                                }
                                            });
                                            bottomSheet.setCancelable(false);
                                            bottomSheet.show(getSupportFragmentManager(), "OrderSuccessBottomSheet");
                                        } else {
                                            Toast.makeText(getApplicationContext(), donHangModel.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
    }

    private void guiThongBaoDatHangThanhCong() {
        if (Utils.accessTokenSend != null) {
             okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Authorization(Utils.accessTokenSend))
                    .build();
        }
        // lấy token quản trị viên
        compositeDisposable.add(apiCommon.layToken("ADMIN")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        nguoiDungModel -> {
                            if (nguoiDungModel.isSuccess()) {
                                for (int i = 0; i < nguoiDungModel.getResult().size(); i++) {
                                    String token = nguoiDungModel.getResult().get(i).getToken();
                                    Notification notification = new Notification("Đơn hàng mới", "Bạn có đơn hàng mới, vui lòng kiểm tra");
                                    Message message = new Message(token, notification);
                                    GuiThongBao guiThongBao = new GuiThongBao(message);
                                    ApiCommon apiCommon = RetrofitClientThongBao.getInstance(okHttpClient).create(ApiCommon.class);
                                    compositeDisposable.add(apiCommon.guiNhanThongBao(guiThongBao)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(
                                                    nhanThongBao -> {

                                                    }, throwable -> {
                                                        Log.d("log", throwable.getMessage());
                                                    }
                                            ));
                                }
                            }
                        }, throwable -> {
                            Log.d("logg", throwable.getMessage());
                        }
                ));

    }
}