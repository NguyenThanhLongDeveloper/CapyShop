package com.example.capyshop.user.dangki;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.example.capyshop.R;
import com.example.capyshop.common.activity.BaseActivity;
import com.example.capyshop.common.retrofit.ApiUser;
import com.example.capyshop.common.retrofit.RetrofitClient;
import com.example.capyshop.common.utils.Utils;
import com.example.capyshop.user.dangnhap.UserDangNhapActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserDangKiActivity extends BaseActivity {
    // Khai báo các thành phần UI
    TextInputEditText edtEmailDangKi, edtSoDienThoaiDangKi, edtHoTenNguoiDungDangKi, edtMatKhauDangKi, edtNhapLaiMatKhauDangKi;
    AppCompatButton btDangKi;
    TextView tvDangNhap;
    ProgressBar pbDangKi;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    // Khai báo Retrofit Service và RxJava Disposable
    ApiUser apiUser;
    CompositeDisposable compositeDisposable = new CompositeDisposable(); // Quản lý các luồng RxJava để tránh rò rỉ bộ nhớ


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.user_dangki_activity);
        super.onCreate(savedInstanceState);
        anhXa();    // Ánh xạ các thành phần UI từ layout XML
        xuLySuKienClick();  // Thiết lập xử lý sự kiện click cho TextView "Đăng nhập"
        xuLySuKienDangKi(); // Thiết lập xử lý sự kiện chính (Nút Đăng ký)
    }

    // Dọn dẹp tất cả các Disposable trong RxJava để tránh rò rỉ bộ nhớ khi Activity bị hủy
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
        // Ánh xạ các thành phần UI từ layout XML
        edtEmailDangKi = findViewById(R.id.edt_email_dang_ki);
        edtSoDienThoaiDangKi = findViewById(R.id.edt_so_dien_thoai_dang_ki);
        edtHoTenNguoiDungDangKi = findViewById(R.id.edt_ten_nguoi_dung_dang_ki);
        edtMatKhauDangKi = findViewById(R.id.edt_mat_khau_dang_ki);
        edtNhapLaiMatKhauDangKi = findViewById(R.id.edt_nhap_lai_mat_khau_dang_ki);
        btDangKi = findViewById(R.id.bt_dang_ki);
        tvDangNhap = findViewById(R.id.tv_dang_nhap);
        pbDangKi = findViewById(R.id.pb_dang_ki);

        // Khởi tạo Retrofit client để giao tiếp với API
        apiUser = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiUser.class);
        // Khởi tạo Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();
    }

    // Xử lý sự kiện click cho TextView "Đăng nhập"
    private void xuLySuKienClick() {
        tvDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang màn hình Đăng nhập
                Intent intent = new Intent(getApplicationContext(), UserDangNhapActivity.class);
                startActivity(intent);
                finish(); // Đóng Activity hiện tại
            }
        });
    }

    // Xử lý sự kiện nút Đăng kí (Thực hiện validation và gọi API)
    private void xuLySuKienDangKi() {
        btDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ các trường nhập liệu và loại bỏ khoảng trắng thừa
                String email = edtEmailDangKi.getText().toString().trim();
                String soDienThoai = edtSoDienThoaiDangKi.getText().toString().trim();
                String hoTenNguoiDung = edtHoTenNguoiDungDangKi.getText().toString().trim();
                String matKhau = edtMatKhauDangKi.getText().toString().trim();
                String nhapLaiMatKhau = edtNhapLaiMatKhauDangKi.getText().toString().trim();

                // Kiểm tra dữ liệu đầu vào
                if (TextUtils.isEmpty(email)) {
                    edtEmailDangKi.setError("Vui lòng nhập email");
                }else if (!isValidEmail(email)) {
                    edtEmailDangKi.setError("Email không đúng định dạng.");
                }else if (TextUtils.isEmpty(soDienThoai)) {
                    edtSoDienThoaiDangKi.setError("Vui lòng nhập số điện thoại");
                }else if (soDienThoai.length() != 10) {
                    edtSoDienThoaiDangKi.setError("Số điện thoại phải có 10 chữ số.");
                }else if (TextUtils.isEmpty(hoTenNguoiDung)) {
                    edtHoTenNguoiDungDangKi.setError("Vui lòng nhập tên người dùng");
                }else if (TextUtils.isEmpty(matKhau)) {
                    edtMatKhauDangKi.setError("Vui lòng nhập mật khẩu");
                }else if (TextUtils.isEmpty(nhapLaiMatKhau)) {
                    edtNhapLaiMatKhauDangKi.setError("Vui lòng nhập lại mật khẩu để xác minh");
                }else if (!matKhau.equals(nhapLaiMatKhau)) {
                    edtNhapLaiMatKhauDangKi.setError("Mật khẩu xác minh không khớp.");
                }else {
                    // Đăng ký tài khoản bằng Firebase Authentication
                    firebaseAuth.createUserWithEmailAndPassword(email, matKhau)
                            .addOnCompleteListener(UserDangKiActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Đăng ký thành công: Thực hiện đăng ký thông tin người dùng
                                        firebaseUser = firebaseAuth.getCurrentUser();
                                        if (firebaseUser != null) {
                                            String uID = firebaseUser.getUid();
                                            dangKi(email, soDienThoai, hoTenNguoiDung, matKhau, uID);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                }
            }
        });
    }

    private void dangKi(String email, String soDienThoai, String hoTenNguoiDung, String matKhau, String uID) {
        // post data lên server bằng Retrofit và RxJava
        pbDangKi.setVisibility(View.VISIBLE);
        compositeDisposable.add(apiUser.dangKi(email, soDienThoai, hoTenNguoiDung, matKhau, uID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        nguoiDungModel -> {
                            // Xử lý kết quả trả về từ API
                            if (nguoiDungModel.isSuccess()) {

                                // Đăng ký thành công: Thông báo và chuyển màn hình
                                Toast.makeText(getApplicationContext(),"Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), UserDangNhapActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                // Xử lý lỗi từ server (ví dụ: Email đã tồn tại)
                                Toast.makeText(getApplicationContext(), nguoiDungModel.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            pbDangKi.setVisibility(View.INVISIBLE);
                        },
                        throwable -> {
                            // Xử lý lỗi kết nối mạng hoặc lỗi hệ thống
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            pbDangKi.setVisibility(View.INVISIBLE);
                        }

                ));
    }

}