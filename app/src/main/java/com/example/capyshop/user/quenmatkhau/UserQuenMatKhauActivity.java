package com.example.capyshop.user.quenmatkhau;

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
import com.example.capyshop.common.activity.BaseActivity;
import com.example.capyshop.user.dangnhap.UserDangNhapActivity;
import com.example.capyshop.common.retrofit.ApiUser;
import com.example.capyshop.common.retrofit.RetrofitClient;
import com.example.capyshop.common.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserQuenMatKhauActivity extends BaseActivity {
    // Khai báo các thành phần UI
    TextInputEditText edtEmailQuenMatKhau;
    AppCompatButton btQuenMatKhau;
    TextView tvDangNhapQuenMatKhau;
    ProgressBar pbQuenMatKhau;

    // Khai báo Retrofit Service và RxJava Disposable
    ApiUser apiUser;
    CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.user_quenmatkhau_activity);
        super.onCreate(savedInstanceState);
        // Khởi tạo các thành phần và xử lý sự kiện
        anhXa();
        xuLyXuKienClick();
        xuLySuKienQuenMatKhau();
    }
    // Xóa CompositeDisposable để ngăn rò rỉ bộ nhớ (memory leaks) khi Activity bị hủy.
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

    // Ánh xạ các thành phần UI từ layout XML
    private void anhXa() {
        edtEmailQuenMatKhau = findViewById(R.id.edt_email_quen_mat_khau);
        btQuenMatKhau = findViewById(R.id.bt_quen_mat_khau);
        tvDangNhapQuenMatKhau = findViewById(R.id.tv_dang_nhap_quen_mat_khau);
        pbQuenMatKhau = findViewById(R.id.pb_quen_mat_khau);

        // Khởi tạo Retrofit để giao tiếp với API server
        apiUser = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiUser.class);
    }

    private void xuLyXuKienClick() {
        tvDangNhapQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserDangNhapActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Xử lý sự kiện khi nút Quên Mật Khẩu được nhấn
    private void xuLySuKienQuenMatKhau() {
        btQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy email và loại bỏ khoảng trắng dư thừa
                String email = edtEmailQuenMatKhau.getText().toString().trim();

                // 1. Kiểm tra Email: Phải có nội dung
                if (TextUtils.isEmpty(email)) {
                    edtEmailQuenMatKhau.setError("Vui lòng nhập email");

                    // 2. Kiểm tra Email: Phải đúng định dạng chuẩn
                }else if (!isValidEmail(email)) {
                    edtEmailQuenMatKhau.setError("Email không đúng định dạng.");

                }else {
                    // Hiển thị ProgressBar để thông báo đang xử lý
                    pbQuenMatKhau.setVisibility(View.VISIBLE);
                    compositeDisposable.add(apiUser.quenMatKhau(email)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    // Xử lý khi nhận được phản hồi từ Server (Success/Failure)
                                    taiKhoanModel -> {
                                        if (taiKhoanModel.isSuccess()) {
                                            // Yêu cầu thành công: Thông báo và chuyển về màn hình đăng nhập
                                            Toast.makeText(getApplicationContext(), taiKhoanModel.getMessage(), Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), UserDangNhapActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else {
                                            // Yêu cầu thất bại: Thông báo lỗi từ Server
                                            Toast.makeText(getApplicationContext(), taiKhoanModel.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                        // Ẩn ProgressBar sau khi xử lý xong
                                        pbQuenMatKhau.setVisibility(View.INVISIBLE);
                                    },
                                    // Xử lý khi có lỗi mạng hoặc lỗi hệ thống khác
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                        pbQuenMatKhau.setVisibility(View.INVISIBLE);
                                    }

                            ));
                }
            }
        });
    }
}