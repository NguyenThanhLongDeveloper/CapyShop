package com.example.capyshop.user.giohang;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capyshop.R;
import com.example.capyshop.common.activity.BaseActivity;
import com.example.capyshop.user.dathang.UserDatHangActivity;
import com.example.capyshop.common.retrofit.ApiUser;
import com.example.capyshop.common.retrofit.RetrofitClient;
import com.example.capyshop.common.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserGioHangActivity extends BaseActivity {
    // Khai báo các thành phần UI
    Toolbar tbGioHang;
    RecyclerView rvGioHang;
    TextView tvThongBaoGioHang, tvTongTienGioHang;
    CheckBox cbChonTatCaGioHang;
    AppCompatButton btMuaHangGioHang;
    UserGioHangAdapter userGioHangAdapter;

    // API và quản lý kết nối
    ApiUser apiUser;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    //bien toan cuc cho ham tinhtongtien
    long tongTien = 0;

    boolean isTuCapNhatCheckAll = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.user_giohang_activity);
        super.onCreate(savedInstanceState);

        // Khởi tạo API
        apiUser = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiUser.class);

        anhXa(); // Ánh xạ UI
        caiDatToolbar(); // Cấu hình Toolbar và RecyclerView
        hienThiGioHang(); // Gọi API lấy dữ liệu giỏ hàng
        xuLySuKienMuaHang(); // Xử lý sự kiện đặt hàng
        xuLyCheckBoxChonTatCa(); //
    }

    @Override
    protected void onResume() {
        hienThiGioHang(); // Gọi API lấy dữ liệu giỏ hàng
        super.onResume();
    }

    // Ánh xạ các View từ file XML
    private void anhXa() {
        tbGioHang = findViewById(R.id.tb_gio_hang);
        rvGioHang = findViewById(R.id.rv_gio_hang);
        tvThongBaoGioHang = findViewById(R.id.tv_thong_bao_gio_hang);
        tvTongTienGioHang = findViewById(R.id.tv_tong_tien_gio_hang);
        cbChonTatCaGioHang = findViewById(R.id.cb_gio_hang);
        btMuaHangGioHang = findViewById(R.id.bt_mua_hang_gio_hang);
    }

    // Cấu hình Toolbar và RecyclerView
    private void caiDatToolbar() {
        setSupportActionBar(tbGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        tbGioHang.setNavigationOnClickListener(v -> finish());

        rvGioHang.setHasFixedSize(true);
        rvGioHang.setLayoutManager(new LinearLayoutManager(this));
    }

    // Hàm gọi API lấy dữ liệu giỏ hàng
    private void hienThiGioHang() {
        // Giả sử API nhận vào ID người dùng để lấy giỏ hàng tương ứng
        compositeDisposable.add(apiUser.xemGioHang(Utils.userNguoiDung_Current.getMaNguoiDung())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        gioHangModel -> {
                            if (!gioHangModel.isSuccess()) return;

                            Utils.mangGioHang = gioHangModel.getResult();

                            if (Utils.mangGioHang.isEmpty()) {
                                tvThongBaoGioHang.setVisibility(View.VISIBLE);
                                return;
                            }
                            tvThongBaoGioHang.setVisibility(View.GONE);
                            userGioHangAdapter = new UserGioHangAdapter(this, Utils.mangGioHang, this::tinhTongTien, this::capNhatTrangThaiCheckBoxChonTatCa);
                            rvGioHang.setAdapter(userGioHangAdapter);
                            tinhTongTien();
                        },
                        throwable -> {
                            Log.e("LayDuLieuGioHang", throwable.getMessage());
                            Toast.makeText(getApplicationContext(), "Lỗi kết nối giỏ hàng", Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    // Xử lý sự kiện chọn tất cả sản phẩm trong giỏ hàng
    private void xuLyCheckBoxChonTatCa() {

        cbChonTatCaGioHang.setOnCheckedChangeListener((buttonView, isChecked) -> {

            // 👉 Nếu là do code tự set thì bỏ qua
            if (isTuCapNhatCheckAll) return;

            if (userGioHangAdapter == null) return;

            Utils.mangMuaHang.clear();

            for (int i = 0; i < Utils.mangGioHang.size(); i++) {
                Utils.mangGioHang.get(i).setTrangThaiCheckBox(isChecked);
                if (isChecked) {
                    Utils.mangMuaHang.add(Utils.mangGioHang.get(i));
                }
            }

            userGioHangAdapter.notifyDataSetChanged();
            tinhTongTien();
        });
    }

    private void capNhatTrangThaiCheckBoxChonTatCa() {

        boolean allChecked = true;

        for (int i = 0; i < Utils.mangGioHang.size(); i++) {
            if (!Utils.mangGioHang.get(i).isTrangThaiCheckBox()) {
                allChecked = false;
                break;
            }
        }

        isTuCapNhatCheckAll = true;
        cbChonTatCaGioHang.setChecked(allChecked);
        isTuCapNhatCheckAll = false;
    }


    //xu ly su kien dat hang
    private void xuLySuKienMuaHang() {
        btMuaHangGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.mangMuaHang.size() > 0) {
                    Intent intent = new Intent(getApplicationContext(), UserDatHangActivity.class);
                    intent.putExtra("tongtien", tongTien);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Vui lòng chọn sản phẩm để mua", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Tính tổng tiền của các sản phẩm được chọn
    private void tinhTongTien() {
        tongTien = 0;
        // Duyệt qua danh sách những sản phẩm đã check trong Adapter (mangMuaHang)
        for (int i = 0; i < Utils.mangMuaHang.size(); i++) {
            tongTien += (long) Utils.mangMuaHang.get(i).getGiaSanPham() * Utils.mangMuaHang.get(i).getSoLuong();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvTongTienGioHang.setText(decimalFormat.format(tongTien) + " đ");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Utils.mangMuaHang.clear(); // Xóa danh sách chọn mua
        compositeDisposable.clear(); // Hủy các kết nối API
        EventBus.getDefault().unregister(this);
        if (userGioHangAdapter != null) {
            userGioHangAdapter.cleanUp(); // Hủy RxJava bên trong adapter (nếu có)
        }
        super.onDestroy();
    }


}