package com.example.capyshop.user.thongtincanhan;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.example.capyshop.R;
import com.example.capyshop.common.activity.BaseActivity;
import com.example.capyshop.common.activity.XemHinhAnhFullActivity;
import com.example.capyshop.common.donhang.DonHang;
import com.example.capyshop.common.retrofit.ApiCommon;
import com.example.capyshop.common.retrofit.ApiUser;
import com.example.capyshop.common.retrofit.RetrofitClient;
import com.example.capyshop.common.utils.Utils;
import com.example.capyshop.user.dangki.UserDangKiActivity;
import com.example.capyshop.user.dangnhap.UserDangNhapActivity;
import com.example.capyshop.user.donhang.UserDonHangActivity;
import com.example.capyshop.user.main.UserMainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserThongTinCaNhanActivity extends BaseActivity {
    FrameLayout flGioHangThongTinCaNhan;
    NotificationBadge nbGioHangThongTinCaNhan;
    CircleImageView civHinhAnhThongTinCaNhan;
    TextView tvHoVaTenThongTinCaNhan, tvSoDienThoaiThongTinCaNhan;
    FrameLayout flChoXacNhanThongTinCaNhan, flChoLayHangThongTinCaNhan;
    FrameLayout flDangGiaoHangThongTinCaNhan, flDaGiaoHangThongTinCaNhan;
    NotificationBadge nbChoXacNhanThongTinCaNhan, nbChoLayHangThongTinCaNhan;
    NotificationBadge nbDangGiaoHangThongTinCaNhan, nbDaGiaoHangThongTinCaNhan;
    LinearLayout llCaiDatTaiKhoanThongTinCaNhan, llDangXuatThongTinCaNhan;
    BottomNavigationView bnMenuThongTinCaNhan;
    Toolbar tbThongTinCaNhan;
    View vToolbarBackgroundThongTinCaNhan;
    CircleImageView civToolbarHinhAnhThongTinCaNhan;
    TextView tvToolbarHoVaTenThongTinCaNhan;
    NestedScrollView nsvThongTinCaNhan;
    CardView cvThongTinCaNhan, cvDangNhapThongTinCaNhan, cvTaoTaiKhoanThongTinCaNhan;
    LinearLayout llThongTinCaNhan;
    ImageView ivThayDoiAnhThongTinCaNhan;

    ApiUser apiUser;
    ApiCommon apiCommon;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final androidx.activity.result.ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == android.app.Activity.RESULT_OK && result.getData() != null) {
                    android.net.Uri uri = result.getData().getData();
                    if (uri != null) {
                        taiAnhLen(uri);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.user_thongtincanhan_activity);
        super.onCreate(savedInstanceState);
        anhXa();
        caiDatToolbar();
        hienThiThongTinCaNhan();
        caiDatIconSoLuongDonHang();
        xuLySuKienClickXemDonHang();
        xuLySuKienClickCaiDatTaiKhoan();
        xuLySuKienClickDangXuat();
        xuLySuKienClickThayDoiAnh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hienThiThongTinCaNhan();
        // Cập nhật lại số lượng sản phẩm trong giỏ hàng khi quay lại màn hình
        Utils.caiDatIconSoLuongGioHang(this, flGioHangThongTinCaNhan, nbGioHangThongTinCaNhan);
        // Xử lý sự kiện click trên các item trong menu
        Utils.xuLySuKienClickMenu(this, bnMenuThongTinCaNhan);
    }

    private void anhXa() {
        flGioHangThongTinCaNhan = findViewById(R.id.fl_gio_hang_thong_tin_ca_nhan);
        nbGioHangThongTinCaNhan = findViewById(R.id.nb_gio_hang_thong_tin_ca_nhan);
        tvHoVaTenThongTinCaNhan = findViewById(R.id.tv_ho_va_ten_thong_tin_ca_nhan);
        tvSoDienThoaiThongTinCaNhan = findViewById(R.id.tv_so_dien_thoai_thong_tin_ca_nhan);
        civHinhAnhThongTinCaNhan = findViewById(R.id.civ_hinh_anh_thong_tin_ca_nhan);
        flChoXacNhanThongTinCaNhan = findViewById(R.id.fl_cho_xac_nhan_thong_tin_ca_nhan);
        flChoLayHangThongTinCaNhan = findViewById(R.id.fl_cho_lay_hang_thong_tin_ca_nhan);
        flDangGiaoHangThongTinCaNhan = findViewById(R.id.fl_dang_giao_hang_thong_tin_ca_nhan);
        flDaGiaoHangThongTinCaNhan = findViewById(R.id.fl_da_giao_hang_thong_tin_ca_nhan);
        nbChoXacNhanThongTinCaNhan = findViewById(R.id.nb_cho_xac_nhan_thong_tin_ca_nhan);
        nbChoLayHangThongTinCaNhan = findViewById(R.id.nb_cho_lay_hang_thong_tin_ca_nhan);
        nbDangGiaoHangThongTinCaNhan = findViewById(R.id.nb_dang_giao_hang_thong_tin_ca_nhan);
        nbDaGiaoHangThongTinCaNhan = findViewById(R.id.nb_da_giao_hang_thong_tin_ca_nhan);
        llCaiDatTaiKhoanThongTinCaNhan = findViewById(R.id.ll_cai_dat_tai_khoan_thong_tin_ca_nhan);
        llDangXuatThongTinCaNhan = findViewById(R.id.ll_dang_xuat_thong_tin_ca_nhan);
        bnMenuThongTinCaNhan = findViewById(R.id.bn_menu_thong_tin_ca_nhan);
        tbThongTinCaNhan = findViewById(R.id.tb_thong_tin_ca_nhan);
        vToolbarBackgroundThongTinCaNhan = findViewById(R.id.v_toolbar_backgroud_thong_tin_ca_nhan);
        civToolbarHinhAnhThongTinCaNhan = findViewById(R.id.civ_toolbar_hinh_anh_thong_tin_ca_nhan);
        tvToolbarHoVaTenThongTinCaNhan = findViewById(R.id.tv_toolbar_ho_va_ten_thong_tin_ca_nhan);
        nsvThongTinCaNhan = findViewById(R.id.nsv_thong_tin_ca_nhan);
        cvThongTinCaNhan = findViewById(R.id.cv_thong_tin_ca_nhan);
        cvDangNhapThongTinCaNhan = findViewById(R.id.cv_dang_nhap_thong_tin_ca_nhan);
        cvTaoTaiKhoanThongTinCaNhan = findViewById(R.id.cv_tao_tai_khoan_thong_tin_ca_nhan);
        llThongTinCaNhan = findViewById(R.id.ll_thong_tin_ca_nhan);
        ivThayDoiAnhThongTinCaNhan = findViewById(R.id.iv_thay_doi_anh_thong_tin_ca_nhan);

        // Khởi tạo Retrofit để gọi API
        apiUser = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiUser.class);
        apiCommon = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiCommon.class);
    }

    // Cài đặt Toolbar
    private void caiDatToolbar() {
        setSupportActionBar(tbThongTinCaNhan);
        getSupportActionBar().setTitle(null);
        // Toolbar ban đầu nền trong suốt (alpha = 0)
        vToolbarBackgroundThongTinCaNhan.setAlpha(0);
        // Hình ảnh Toolbar ẩn đi ban đầu
        civToolbarHinhAnhThongTinCaNhan.setAlpha(0);
        // Tiêu đề ẩn đi ban đầu
        tvToolbarHoVaTenThongTinCaNhan.setAlpha(0);
        // Khoảng cách cuộn tối đa để Toolbar hiện lên hoàn toàn (Ví dụ: 200px)
        final int maxScroll = 400;
        // Xử lý sự kiện cuộn NestedScrollView
        nsvThongTinCaNhan.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // Tính toán tỷ lệ alpha dựa trên vị trí cuộn (từ 0.0 đến 1.0)
                float alpha = Math.min(1f, (float) scrollY / maxScroll);
                // Cập nhật độ mờ của nền trắng Toolbar
                vToolbarBackgroundThongTinCaNhan.setAlpha(alpha);
                // Cập nhật độ mờ của Hình ảnh Toolbar
                civToolbarHinhAnhThongTinCaNhan.setAlpha(alpha);
                // Cập nhật độ mờ của Text Title
                tvToolbarHoVaTenThongTinCaNhan.setAlpha(alpha);

            }
        });

    }

    private void hienThiThongTinCaNhan() {
        if (Utils.userNguoiDung_Current == null) {
            // CHƯA ĐĂNG NHẬP
            cvThongTinCaNhan.setVisibility(View.VISIBLE); // Hiện Card Đăng nhập đè lên
            llDangXuatThongTinCaNhan.setVisibility(View.GONE); // Ẩn nút đăng xuất

            // Làm mờ phần thông tin phía dưới
            civHinhAnhThongTinCaNhan.setAlpha(0.2f);
            tvHoVaTenThongTinCaNhan.setText("Khách");
            tvSoDienThoaiThongTinCaNhan.setText("Vui lòng đăng nhập để sử dụng");

            // Sự kiện nút đăng nhập/đăng ký trên Card đè
            cvDangNhapThongTinCaNhan.setOnClickListener(v -> {
                startActivity(new Intent(this, UserDangNhapActivity.class));
            });
            cvTaoTaiKhoanThongTinCaNhan.setOnClickListener(v -> {
                startActivity(new Intent(this, UserDangKiActivity.class));
            });
            return;
        }
        // ĐÃ ĐĂNG NHẬP
        cvThongTinCaNhan.setVisibility(View.GONE); // Ẩn Card đè
        llDangXuatThongTinCaNhan.setVisibility(View.VISIBLE);
        civHinhAnhThongTinCaNhan.setAlpha(1.0f);

        // Đổ dữ liệu
        tvHoVaTenThongTinCaNhan.setText(Utils.userNguoiDung_Current.getHoTenNguoiDung());
        tvSoDienThoaiThongTinCaNhan.setText(Utils.userNguoiDung_Current.getSoDienThoai());
        tvToolbarHoVaTenThongTinCaNhan.setText(Utils.userNguoiDung_Current.getHoTenNguoiDung());

        Glide.with(this)
                .load(Utils.userNguoiDung_Current.getHinhAnhNguoiDung())
                .placeholder(R.drawable.ic_hinh_anh)
                .into(civHinhAnhThongTinCaNhan);

        Glide.with(this)
                .load(Utils.userNguoiDung_Current.getHinhAnhNguoiDung())
                .placeholder(R.drawable.ic_hinh_anh)
                .into(civToolbarHinhAnhThongTinCaNhan);

        civHinhAnhThongTinCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.userNguoiDung_Current.getHinhAnhNguoiDung() != null) {
                    Intent intent = new Intent(getApplicationContext(), XemHinhAnhFullActivity.class);
                    intent.putExtra("hinhanh", Utils.userNguoiDung_Current.getHinhAnhNguoiDung());
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(),
                            android.R.anim.fade_in, android.R.anim.fade_out);
                    startActivity(intent, options.toBundle());
                    // Thêm Flag nếu context không phải từ Activity
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    private void caiDatIconSoLuongDonHang() {
        if (Utils.userNguoiDung_Current == null) {
            return;
        }
        // Cập nhật số lượng đơn hàng
        compositeDisposable.add(apiUser.layDonHang(Utils.userNguoiDung_Current.getMaNguoiDung(), "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donHangModel -> {
                            if (donHangModel.isSuccess() && donHangModel.getResult() != null) {
                                // 1. Khởi tạo các biến đếm cho từng trạng thái
                                int soLuongChoXacNhan = 0, soLuongChoLayHang = 0, soLuongDangGiaoHang = 0,
                                        soLuongDaGiaoHang = 0;
                                List<DonHang> mangUserDonHang = donHangModel.getResult();

                                // 2. Duyệt qua danh sách đơn hàng trả về từ Server
                                for (DonHang userDonHang : mangUserDonHang) {
                                    String trangThai = userDonHang.getTrangThai();
                                    if (trangThai == null)
                                        continue;

                                    // 3. Phân loại và tăng biến đếm tương ứng với trạng thái chuỗi
                                    switch (trangThai) {
                                        case "CHO_XAC_NHAN":
                                            soLuongChoXacNhan++;
                                            break;
                                        case "CHO_LAY_HANG":
                                            soLuongChoLayHang++;
                                            break;
                                        case "DANG_GIAO_HANG":
                                            soLuongDangGiaoHang++;
                                            break;
                                        case "DA_GIAO_HANG":
                                            soLuongDaGiaoHang++;
                                            break;
                                    }
                                }
                                // 4. Hiển thị số lượng lên Badge Chờ xác nhận (Ẩn nếu số lượng là 0)
                                if (soLuongChoXacNhan > 0)
                                    nbChoXacNhanThongTinCaNhan.setNumber(soLuongChoXacNhan);
                                else
                                    nbChoXacNhanThongTinCaNhan.clear();

                                // 5. Hiển thị số lượng lên Badge Chờ lấy hàng (Ẩn nếu số lượng là 0)
                                if (soLuongChoLayHang > 0)
                                    nbChoLayHangThongTinCaNhan.setNumber(soLuongChoLayHang);
                                else
                                    nbChoLayHangThongTinCaNhan.clear();

                                // 6. Hiển thị số lượng lên Badge Đang giao (Ẩn nếu số lượng là 0)
                                if (soLuongDangGiaoHang > 0)
                                    nbDangGiaoHangThongTinCaNhan.setNumber(soLuongDangGiaoHang);
                                else
                                    nbDangGiaoHangThongTinCaNhan.clear();

                                // 7. Hiển thị số lượng lên Badge Đã giao (Ẩn nếu số lượng là 0)
                                if (soLuongDaGiaoHang > 0)
                                    nbDaGiaoHangThongTinCaNhan.setNumber(soLuongDaGiaoHang);
                                else
                                    nbDaGiaoHangThongTinCaNhan.clear();

                            }
                        },
                        throwable -> Log.e("API_ERROR", "Lỗi lấy số lượng: " + throwable.getMessage())));

    }

    private void xuLySuKienClickXemDonHang() {
        View.OnClickListener trangThaiClickListenner = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Utils.userNguoiDung_Current == null) {
                    Utils.thietLapBottomSheetDialog(UserThongTinCaNhanActivity.this, "Vui lòng đăng nhập",
                            "Vui lòng đăng nhập để sử dụng", "Đăng nhập", () -> {
                                Intent intent = new Intent(UserThongTinCaNhanActivity.this, UserDangNhapActivity.class);
                                startActivity(intent);
                            });
                    return;
                }
                String trangThai = "";
                int id = v.getId();
                if (id == R.id.fl_cho_xac_nhan_thong_tin_ca_nhan) {
                    trangThai = "CHO_XAC_NHAN";
                } else if (id == R.id.fl_cho_lay_hang_thong_tin_ca_nhan) {
                    trangThai = "CHO_LAY_HANG";
                } else if (id == R.id.fl_dang_giao_hang_thong_tin_ca_nhan) {
                    trangThai = "DANG_GIAO_HANG";
                } else if (id == R.id.fl_da_giao_hang_thong_tin_ca_nhan) {
                    trangThai = "DA_GIAO_HANG";
                }
                // Nếu trangThai không rỗng, chuyển sang màn hình DonHangActivity
                if (!trangThai.isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(), UserDonHangActivity.class);
                    intent.putExtra("trangthai", trangThai);
                    startActivity(intent);
                }

            }
        };
        flChoXacNhanThongTinCaNhan.setOnClickListener(trangThaiClickListenner);
        flChoLayHangThongTinCaNhan.setOnClickListener(trangThaiClickListenner);
        flDangGiaoHangThongTinCaNhan.setOnClickListener(trangThaiClickListenner);
        flDaGiaoHangThongTinCaNhan.setOnClickListener(trangThaiClickListenner);

    }

    private void xuLySuKienClickCaiDatTaiKhoan() {
        llCaiDatTaiKhoanThongTinCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.userNguoiDung_Current == null) {
                    Utils.thietLapBottomSheetDialog(UserThongTinCaNhanActivity.this, "Vui lòng đăng nhập",
                            "Vui lòng đăng nhập để sử dụng", "Đăng nhập", () -> {
                                Intent intent = new Intent(UserThongTinCaNhanActivity.this, UserDangNhapActivity.class);
                                startActivity(intent);
                            });
                    return;
                }
                // Khởi tạo BottomSheet với Interface nhận 3 tham số
                UserThongTinCaNhanBottomSheetCaiDatTaiKhoan bottomSheet = new UserThongTinCaNhanBottomSheetCaiDatTaiKhoan(
                        new UserThongTinCaNhanBottomSheetCaiDatTaiKhoan.OnAddressSaveListener() {
                            @Override
                            public void onSave(String hoVaTen, String soDienThoai, String diaChi) {
                                int maNguoiDung = Utils.userNguoiDung_Current.getMaNguoiDung();
                                // 1. Cập nhật UI (Hiển thị địa chỉ lên TextView)
                                compositeDisposable
                                        .add(apiUser
                                                .capNhapThongTinCaNhan(maNguoiDung, hoVaTen, soDienThoai, diaChi,
                                                        Utils.userNguoiDung_Current.getHinhAnhNguoiDung())
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(
                                                        nguoiDungModel -> {
                                                            if (nguoiDungModel.isSuccess()) {
                                                                Utils.userNguoiDung_Current = nguoiDungModel.getResult()
                                                                        .get(0);
                                                                Toast.makeText(getApplicationContext(),
                                                                        "Thêm địa chỉ thành công", Toast.LENGTH_SHORT)
                                                                        .show();
                                                                hienThiThongTinCaNhan();
                                                            }
                                                        },
                                                        throwable -> {
                                                            Log.e("API_ERROR",
                                                                    "Lỗi thêm địa chỉ: " + throwable.getMessage());
                                                        }));
                            }
                        });
                bottomSheet.show(getSupportFragmentManager(), "ThemDiaChi");

            }
        });
    }

    private void xuLySuKienClickDangXuat() {
        if (Utils.userNguoiDung_Current != null) {
            llDangXuatThongTinCaNhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.thietLapBottomSheetDialog(UserThongTinCaNhanActivity.this, "Đăng xuất?",
                            "Bạn có chắc muốn thoát không?", "Đăng xuất", () -> {
                                // Đăng xuất bằng Firebase
                                FirebaseAuth.getInstance().signOut();
                                // Xóa dữ liệu người dùng trong biến toàn cục
                                Paper.book().destroy();
                                int maNguoiDung = Utils.userNguoiDung_Current.getMaNguoiDung();
                                compositeDisposable.add(apiCommon.xoaToken(maNguoiDung)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(
                                                nguoiDungModel -> {

                                                }, throwable -> {
                                                    Toast.makeText(getApplicationContext(), "Không thể kết nối đến Server", Toast.LENGTH_SHORT).show();
                                                }
                                        ));
                                Utils.userNguoiDung_Current = null;

                                Intent intent = new Intent(getApplicationContext(), UserMainActivity.class);

                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            });

                }
            });
        }
    }

    private void xuLySuKienClickThayDoiAnh() {
        ivThayDoiAnhThongTinCaNhan.setOnClickListener(v -> {
            if (Utils.userNguoiDung_Current == null) {
                Utils.thietLapBottomSheetDialog(this, "Vui lòng đăng nhập",
                        "Vui lòng đăng nhập để sử dụng tính năng này", "Đăng nhập", () -> {
                            startActivity(new Intent(this, UserDangNhapActivity.class));
                        });
                return;
            }
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });
    }

    private void taiAnhLen(android.net.Uri uri) {
        Utils.taiAnhLenServer(this, uri, "nguoidung", compositeDisposable, new Utils.OnUploadCallback() {
            @Override
            public void onThanhCong(String tenFileMoi) {
                capNhatHinhAnhUser(tenFileMoi);
            }

            @Override
            public void onThatBai(String message) {
                Toast.makeText(UserThongTinCaNhanActivity.this, "Lỗi upload: " + message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void capNhatHinhAnhUser(String tenFileMoi) {
        String fullUrl = Utils.BASE_URL + "common/images/" + tenFileMoi;
        int maNguoiDung = Utils.userNguoiDung_Current.getMaNguoiDung();
        String hoTen = Utils.userNguoiDung_Current.getHoTenNguoiDung();
        String sdt = Utils.userNguoiDung_Current.getSoDienThoai();
        String diaChi = Utils.userNguoiDung_Current.getDiaChi();

        compositeDisposable.add(apiUser.capNhapThongTinCaNhan(maNguoiDung, hoTen, sdt, diaChi, fullUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            if (model.isSuccess()) {
                                Toast.makeText(this, "Cập nhật ảnh đại diện thành công", Toast.LENGTH_SHORT).show();
                                Utils.userNguoiDung_Current = model.getResult().get(0);
                                Paper.book().write("user", Utils.userNguoiDung_Current); // Cập nhật local storage
                                hienThiThongTinCaNhan();
                            } else {
                                Toast.makeText(this, model.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> Toast
                                .makeText(this, "Lỗi cập nhật server: " + throwable.getMessage(), Toast.LENGTH_SHORT)
                                .show()));
    }
}