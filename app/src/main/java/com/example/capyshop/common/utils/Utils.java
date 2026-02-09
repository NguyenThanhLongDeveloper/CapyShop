package com.example.capyshop.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.capyshop.R;
import com.example.capyshop.common.Interface.BottomSheetDialogClickListener;
import com.example.capyshop.common.retrofit.ApiCommon;
import com.example.capyshop.common.retrofit.ApiUser;
import com.example.capyshop.common.retrofit.RetrofitClient;
import com.example.capyshop.user.dangnhap.UserDangNhapActivity;
import com.example.capyshop.common.nguoidung.NguoiDung;
import com.example.capyshop.user.giohang.UserGioHang;
import com.example.capyshop.user.giohang.UserGioHangActivity;
import com.example.capyshop.user.main.UserMainActivity;
import com.example.capyshop.user.thongtincanhan.UserThongTinCaNhanActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nex3z.notificationbadge.NotificationBadge;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

//Utils: Lớp tiện ích chứa các hằng số, biến static toàn cục và các phương thức dùng chung.
public class Utils {
    // URL cơ sở để kết nối API.
    public static final String BASE_URL = "http://192.168.0.102/capyshop_api/";

    // Danh sách các sản phẩm hiện có trong giỏ
    public static List<UserGioHang> mangGioHang;

    // Danh sách các sản phẩm người dùng đã chọn để tiến hành đặt hàng
    public static List<UserGioHang> mangMuaHang = new ArrayList<>();

    // Đối tượng lưu trữ thông tin người dùng đang đăng nhập hiện tại
    public static NguoiDung userNguoiDung_Current = new NguoiDung();
    //
    public static String accessTokenSend;

    // Kiểm tra thiết bị có đang kết nối Wifi hoặc mạng di động hay không.
    public static boolean kiemTraKetNoi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return (wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected());
    }

    // Hàm thiết lập Badge (số lượng) trên icon giỏ hàng
    public static void caiDatIconSoLuongGioHang(Context context, FrameLayout frameLayout, NotificationBadge badge) {

        // Xử lý sự kiện click vào giỏ hàng
        if (frameLayout != null) {
            frameLayout.setOnClickListener(v -> {
                if (Utils.userNguoiDung_Current == null) {
                    Utils.thietLapBottomSheetDialog(context, "Vui lòng đăng nhập", "Vui lòng đăng nhập để sử dụng",
                            "Đăng nhập", () -> {
                                Intent intent = new Intent(context, UserDangNhapActivity.class);
                                context.startActivity(intent);
                            });
                    return;
                }
                Intent intent = new Intent(context, UserGioHangActivity.class);
                context.startActivity(intent);
            });
        }

        // chưa đăng nhập, xóa con số trên Badge
        if (Utils.userNguoiDung_Current == null) {
            if (badge != null)
                badge.clear();
            return;
        }

        // Gọi API lấy dữ liệu giỏ hàng thực tế từ Database
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        ApiUser apiUser = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiUser.class);
        compositeDisposable.add(apiUser.xemGioHang(Utils.userNguoiDung_Current.getMaNguoiDung())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        gioHangModel -> {
                            if (gioHangModel.isSuccess()) {
                                mangGioHang = gioHangModel.getResult();
                                if (Utils.mangGioHang == null) {
                                    Utils.mangGioHang = new ArrayList<>();
                                }
                                // Cập nhật hiển thị Badge dựa trên số lượng item trong List
                                int soLuong = Utils.mangGioHang.size();
                                if (soLuong > 0) {
                                    badge.setVisibility(View.VISIBLE);
                                    badge.setText(String.valueOf(soLuong));
                                } else {
                                    badge.setVisibility(View.GONE);
                                }
                            }
                        },
                        throwable -> Log.e("LayDuLieuGioHang", throwable.getMessage())));
    }

    // xử lý điều hướng cho BottomNavigationView Thanh menu dưới cùng
    public static void xuLySuKienClickMenu(Context context, BottomNavigationView bottomNavigationView) {
        if (bottomNavigationView == null)
            return;

        // Đánh dấu Tab đang được chọn dựa trên màn hình hiện tại
        if (context instanceof UserMainActivity) {
            bottomNavigationView.setSelectedItemId(R.id.bnm_trang_chu_main);
        } else if (context instanceof UserThongTinCaNhanActivity) {
            bottomNavigationView.setSelectedItemId(R.id.bnm_toi_main);
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            Intent intent = null;

            // Logic chuyển trang
            if (id == R.id.bnm_trang_chu_main && !(context instanceof UserMainActivity)) {
                intent = new Intent(context, UserMainActivity.class);
            } else if (id == R.id.bnm_toi_main && !(context instanceof UserThongTinCaNhanActivity)) {
                intent = new Intent(context, UserThongTinCaNhanActivity.class);
            }

            if (intent != null) {
                // Tắt hiệu ứng chuyển cảnh mặc định để menu trông như cố định
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(intent);

                if (context instanceof Activity) {
                    ((Activity) context).finish();
                    ((Activity) context).overridePendingTransition(0, 0);
                }
                return true;
            }
            return true;
        });
    }

    // tạo một hộp thoại BottomSheet xác nhận tùy chỉnh
    public static void thietLapBottomSheetDialog(Context context, String tieuDe, String noiDung, String tenNutXacNhan,
            BottomSheetDialogClickListener listener) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);
        View view = LayoutInflater.from(context).inflate(R.layout.common_bottomsheetdialog_layout, null);
        bottomSheetDialog.setContentView(view);

        TextView tvTieuDe = view.findViewById(R.id.tv_tieu_de_bottom_sheet_dialog);
        TextView tvNoiDung = view.findViewById(R.id.tv_noi_dung_bottom_sheet_dialog);
        Button btnXacNhan = view.findViewById(R.id.btn_xac_nhan_bottom_sheet_dialog);
        Button btnHuy = view.findViewById(R.id.btn_huy_bottom_sheet_dialog);

        tvTieuDe.setText(tieuDe);
        tvNoiDung.setText(noiDung);
        btnXacNhan.setText(tenNutXacNhan);

        btnXacNhan.setOnClickListener(v -> {
            listener.onConfirm(); // Thực thi code truyền vào qua interface
            bottomSheetDialog.dismiss();
        });

        btnHuy.setOnClickListener(v -> bottomSheetDialog.dismiss());
        bottomSheetDialog.show();
    }

    // Hàm lấy đường dẫn thực tế từ URI (Dùng cho upload file)
    public static String layDuongDanThucTe(Context context, android.net.Uri uri) {
        String duongDan = null;
        String[] mangCot = { android.provider.MediaStore.Images.Media.DATA };
        android.database.Cursor conTro = context.getContentResolver().query(uri, mangCot, null, null, null);
        if (conTro != null) {
            if (conTro.moveToFirst()) {
                int chiSoCot = conTro.getColumnIndexOrThrow(android.provider.MediaStore.Images.Media.DATA);
                duongDan = conTro.getString(chiSoCot);
            }
            conTro.close();
        }
        if (duongDan == null) {
            duongDan = "Not found";
        }
        return duongDan;
    }

    public interface OnUploadCallback {
        void onThanhCong(String tenFileMoi);

        void onThatBai(String message);
    }

    public static void taiAnhLenServer(Context context, android.net.Uri uri, String type, CompositeDisposable compositeDisposable,
            OnUploadCallback callback) {
        String strPath = layDuongDanThucTe(context, uri);
        if (strPath.equals("Not found")) {
            callback.onThatBai("Không tìm thấy file ảnh");
            return;
        }

        File file = new File(strPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        // Create specific RequestBody for 'type'
        RequestBody typeBody = RequestBody.create(MediaType.parse("text/plain"), type);

        ApiCommon apiCommon = RetrofitClient.getInstance(BASE_URL).create(ApiCommon.class);
        compositeDisposable.add(apiCommon.taiAnhLen(part, typeBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            if (model.isSuccess()) {
                                callback.onThanhCong(model.getName());
                            } else {
                                callback.onThatBai(model.getMessage());
                            }
                        },
                        throwable -> callback.onThatBai("Lỗi đường truyền: " + throwable.getMessage())));
    }
}