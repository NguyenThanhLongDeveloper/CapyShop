package com.example.capyshop.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.capyshop.R;
import com.example.capyshop.admin.main.AdminMainActivity;
import com.example.capyshop.common.retrofit.ApiUser;
import com.example.capyshop.common.retrofit.RetrofitClient;
import com.example.capyshop.common.utils.Utils;
import com.example.capyshop.user.main.UserMainActivity;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {
    // Quản lý các luồng xử lý bất đồng bộ (RxJava)
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    // Khởi tạo interface kết nối API
    ApiUser apiUser = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiUser.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.common_splash_activity);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //trong Paper không có dữ liệu email xóa trắng thông tin người dùng hiện tại trong Utils.
        if (Paper.book().read("email") == null) {
            Utils.userNguoiDung_Current = null;
        }

        // Khởi tạo và thực hiện hiệu ứng Animation cho Logo và Tên App
        Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        animation.setDuration(1500); // Thời gian hiệu ứng xuất hiện (1.5 giây)
        findViewById(R.id.cv_logo).startAnimation(animation);
        findViewById(R.id.tv_app_name).startAnimation(animation);

        // 2. Tạo một khoảng trễ
        new Handler().postDelayed(() -> {
            kiemTraDangNhapTuDong(); //kiểm tra đăng nhập tự động
        }, 1500);
    }

    //Phương thức kiểm tra thông tin tài khoản đã lưu trong máy (PaperDB).
    private void kiemTraDangNhapTuDong() {
        // Đọc dữ liệu email và mật khẩu đã được lưu từ lần đăng nhập trước
        String email = Paper.book().read("email");
        String matKhau = Paper.book().read("matkhau");

        // Không tìm thấy dữ liệu chuyển sang màn hình chính
        if (email == null || matKhau == null) {
            Intent intent = new Intent(getApplicationContext(), UserMainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // Có dữ liệu tài khoản gọi api đang nhập
        if (email != null && matKhau != null) {
            compositeDisposable.add(apiUser.dangNhap(email, matKhau)
                    .subscribeOn(Schedulers.io()) // Chạy API trên luồng I/O (không gây lag UI)
                    .observeOn(AndroidSchedulers.mainThread()) // Trả kết quả về luồng chính để cập nhật UI
                    .subscribe(
                            nguoiDungModel -> {
                                if (nguoiDungModel.isSuccess()) {
                                    //Lưu dữ liệu người dùng vào biến toàn cục
                                    Utils.userNguoiDung_Current = nguoiDungModel.getResult().get(0);

                                    //kiểm tra phân quyền người dùng
                                    if ("ADMIN".equals(Utils.userNguoiDung_Current.getVaiTro())) {
                                        // chuyển vào AdminMainActivity
                                        Intent intent = new Intent(getApplicationContext(), AdminMainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // Chuyển vào MainActivity
                                        Intent intent = new Intent(getApplicationContext(), UserMainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                } else {
                                    // Đăng nhập thất bại
                                    Toast.makeText(getApplicationContext(), nguoiDungModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), UserMainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            },
                            throwable -> {
                                // Lỗi kết nối
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                    ));
        }
    }
}