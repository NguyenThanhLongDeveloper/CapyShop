package com.example.capyshop.user.dangnhap;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.example.capyshop.R;
import com.example.capyshop.user.dangki.UserDangKiActivity;
import com.example.capyshop.common.activity.BaseActivity;
import com.example.capyshop.common.retrofit.ApiUser;
import com.example.capyshop.common.retrofit.RetrofitClient;
import com.example.capyshop.common.utils.Utils;
import com.example.capyshop.admin.main.AdminMainActivity;
import com.example.capyshop.user.quenmatkhau.UserQuenMatKhauActivity;
import com.example.capyshop.user.main.UserMainActivity;
import com.google.android.material.textfield.TextInputEditText;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserDangNhapActivity extends BaseActivity {
    // Khai báo các thành phần UI
    TextInputEditText edtEmailDangNhap, edtMatKhauDangNhap;
    TextView tvDangKy, tvQuenMatKhau;
    AppCompatButton btDangNhap;
    ProgressBar pbDangNhap;

    // Khai báo Retrofit Service và RxJava Disposable
    ApiUser apiUser;
    CompositeDisposable compositeDisposable = new CompositeDisposable(); // Quản lý các luồng RxJava để tránh rò rỉ bộ
                                                                         // nhớ

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.user_dangnhap_activity);
        super.onCreate(savedInstanceState);
        anhXa(); // Ánh xạ các thành phần UI từ layout XML
        xuLySuKienClick(); // Xử lý sự kiện click cho "Đăng ký" và "Quên mật khẩu"
        xuLySuKienDangNhap(); // Xử lý sự kiện chính (Nút Đăng nhập)
    }

    // Phương thức được gọi khi Activity tiếp tục/quay lại
    @Override
    protected void onResume() {
        super.onResume();
    }

    // Dọn dẹp tất cả các Disposable trong RxJava để tránh rò rỉ bộ nhớ khi Activity
    // bị hủy
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    // Hàm tĩnh để kiểm tra định dạng email hợp lệ
    private static boolean isValidEmail(CharSequence target) {
        // Sử dụng Patterns.EMAIL_ADDRESS để kiểm tra định dạng email chuẩn
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    // Ánh xạ các thành phần UI
    private void anhXa() {
        tvDangKy = findViewById(R.id.tv_dang_ky);
        tvQuenMatKhau = findViewById(R.id.tv_quen_mat_khau);
        btDangNhap = findViewById(R.id.bt_dang_nhap);
        edtEmailDangNhap = findViewById(R.id.edt_email_dang_nhap);
        edtMatKhauDangNhap = findViewById(R.id.edt_mat_khau_dang_nhap);
        pbDangNhap = findViewById(R.id.pb_dang_nhap);

        // Khởi tạo Retrofit client để giao tiếp với API
        apiUser = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiUser.class);

    }

    // Xử lý sự kiện click cho Text View chuyển hướng
    private void xuLySuKienClick() {
        // Chuyển sang màn hình Đăng ký
        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserDangKiActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // Chuyển sang màn hình Quên Mật Khẩu
        tvQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserQuenMatKhauActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Xử lý sự kiện nút Đăng nhập (Validation và gọi API)
    private void xuLySuKienDangNhap() {
        btDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmailDangNhap.getText().toString().trim();
                String matKhau = edtMatKhauDangNhap.getText().toString().trim();

                // 1. Kiểm tra Email (Không trống)
                if (TextUtils.isEmpty(email)) {
                    edtEmailDangNhap.setError("Vui lòng nhập email");

                    // 2. Kiểm tra Email (Định dạng hợp lệ)
                } else if (!isValidEmail(email)) {
                    edtEmailDangNhap.setError("Email không đúng định dạng.");

                    // 3. Kiểm tra Mật khẩu (Không trống)
                } else if (TextUtils.isEmpty(matKhau)) {
                    edtMatKhauDangNhap.setError("Vui lòng nhập mật khẩu");
                } else {
                    // post data lên server bằng Retrofit và RxJava
                    pbDangNhap.setVisibility(View.VISIBLE);
                    compositeDisposable.add(apiUser.dangNhap(email, matKhau)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    // Xử lý kết quả trả về từ server
                                    nguoiDungModel -> {
                                        if (nguoiDungModel.isSuccess()) {
                                            // Đăng nhập thành công: Lưu thông tin người dùng và chuyển màn hình chính
                                            Utils.userNguoiDung_Current = nguoiDungModel.getResult().get(0);

                                            Paper.book().write("email", email);
                                            Paper.book().write("matkhau", matKhau);
                                            if (Utils.userNguoiDung_Current.getVaiTro().equals("ADMIN")) {
                                                Intent intent = new Intent(getApplicationContext(),
                                                        AdminMainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Intent intent = new Intent(getApplicationContext(),
                                                        UserMainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        } else {
                                            // Đăng nhập thất bại
                                            Toast.makeText(getApplicationContext(), nguoiDungModel.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                        pbDangNhap.setVisibility(View.INVISIBLE);

                                    },
                                    throwable -> {
                                        // Xử lý lỗi kết nối mạng hoặc lỗi hệ thống
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                        pbDangNhap.setVisibility(View.INVISIBLE);
                                    }));
                }
            }
        });
    }

}