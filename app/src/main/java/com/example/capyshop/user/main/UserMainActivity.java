package com.example.capyshop.user.main;

// Import các thư viện cần thiết cho Activity

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capyshop.R;
import com.example.capyshop.common.activity.BaseActivity;
import com.example.capyshop.common.danhmuc.DanhMuc;
import com.example.capyshop.common.quangcao.QuangCao;
import com.example.capyshop.common.sanpham.SanPham;
import com.example.capyshop.common.retrofit.ApiUser;
import com.example.capyshop.common.retrofit.RetrofitClient;
import com.example.capyshop.common.utils.Utils;
import com.example.capyshop.user.tìmkiemsanpham.UserTimKiemSanPhamActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserMainActivity extends BaseActivity {
    // Khai báo các thành phần UI
    DrawerLayout drawerLayoutMain;
    Toolbar toolbarMain;
    ViewFlipper viewFlipperMain;
    RecyclerView recyclerViewMain;
    RecyclerView recyclerViewDanhMucMain;
    NotificationBadge notificationBadgeGioHangMain;
    FrameLayout frameLayoutGioHangMain;
    BottomNavigationView bottomNavigationViewMenuMain;
    View viewToolbarBackgroudMain;
    CardView cardViewTimKiemToolbarMain, cardViewTimKiemMain;

    NestedScrollView nestedScrollViewThongTinCaNhan;


    // Khai báo Adapter và các đối tượng dữ liệu
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiUser apiUser;
    UserMainSanPhamMoiAdapter userMainSanPhamMoiAdapter;
    UserMainDanhMucAdapter userMainDanhMucAdapter;
    List<QuangCao> mangUserMainQuangCao;
    List<SanPham> mangUserSanPhamMoi;
    List<DanhMuc> mangUserMainDanhMuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.user_main_activity);
        super.onCreate(savedInstanceState);
        // Khởi tạo Retrofit để gọi API
        apiUser = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiUser.class);
        anhXa(); // Ánh xạ UI
        caiDatToolbar(); // Cấu hình Toolbar
        // Kiểm tra kết nối mạng trước khi tải dữ liệu
        if (Utils.kiemTraKetNoi(this)) {
            hienThiQuangCao(); // Hiển thị quảng cáo
            hienThiDanhMuc(); // Lấy danh sách danh mục
            hienThiSanPhamMoi(); // Lấy danh sách sản phẩm mới
            xuLySuKienClick(); // Xử lý sự kiện click trên menu
        } else {
            Toast.makeText(getApplicationContext(), "Không có kết nối internet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Cập nhật lại số lượng sản phẩm trong giỏ hàng khi quay lại màn hình
        Utils.caiDatIconSoLuongGioHang(this, frameLayoutGioHangMain, notificationBadgeGioHangMain);
        // Xử lý sự kiện click trên các item trong menu
        Utils.xuLySuKienClickMenu(this, bottomNavigationViewMenuMain);
    }

    @Override
    protected void onDestroy() {
        // Hủy bỏ các yêu cầu mạng để tránh rò rỉ bộ nhớ
        compositeDisposable.clear();
        super.onDestroy();
    }

    // Ánh xạ các View từ file XML
    private void anhXa() {
        drawerLayoutMain = findViewById(R.id.dl_main);
        toolbarMain = findViewById(R.id.tb_main);
        viewFlipperMain = findViewById(R.id.vf_main);
        recyclerViewMain = findViewById(R.id.rv_main);
        recyclerViewDanhMucMain = findViewById(R.id.rv_danh_muc_main);
        frameLayoutGioHangMain = findViewById(R.id.fl_gio_hang_main);
        notificationBadgeGioHangMain = findViewById(R.id.nb_gio_hang_main);
        cardViewTimKiemToolbarMain = findViewById(R.id.cv_tim_kiem_toolbar_main);
        cardViewTimKiemMain = findViewById(R.id.cv_tim_kiem_main);
        bottomNavigationViewMenuMain = findViewById(R.id.bn_menu_main);
        viewToolbarBackgroudMain = findViewById(R.id.v_toolbar_backgroud_main);
        nestedScrollViewThongTinCaNhan = findViewById(R.id.nsv_main);


        // Khởi tạo danh sách và adapter
        mangUserMainQuangCao = new ArrayList<>();
        mangUserMainDanhMuc = new ArrayList<>();
        mangUserSanPhamMoi = new ArrayList<>();

        // Thiết lập layout dạng lưới cho RecyclerView
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerViewMain.setLayoutManager(layoutManager);
        recyclerViewMain.setHasFixedSize(true);

        // Hiển thị danh mục nằm ngang
        LinearLayoutManager layoutManagerDM = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewDanhMucMain.setLayoutManager(layoutManagerDM);
        recyclerViewDanhMucMain.setHasFixedSize(true);



    }

    // Cấu hình Toolbar và Navigation Drawer
    private void caiDatToolbar() {
        setSupportActionBar(toolbarMain);
        getSupportActionBar().setTitle(null);
        // Toolbar ban đầu nền trong suốt (alpha = 0)
        viewToolbarBackgroudMain.setAlpha(0);
        // Tiêu đề ẩn đi ban đầu
        cardViewTimKiemToolbarMain.setAlpha(0);
        // Khoảng cách cuộn tối đa để Toolbar hiện lên hoàn toàn (Ví dụ: 200px)
        final int maxScroll = 800;
        // Xử lý sự kiện cuộn NestedScrollView
        nestedScrollViewThongTinCaNhan.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // Tính toán tỷ lệ alpha dựa trên vị trí cuộn (từ 0.0 đến 1.0)
                float alpha = Math.min(1f, (float) scrollY / maxScroll);
                // Cập nhật độ mờ của nền trắng Toolbar
                viewToolbarBackgroudMain.setAlpha(alpha);
                // Cập nhật độ mờ của Text Title
                cardViewTimKiemToolbarMain.setAlpha(alpha);

            }
        });

    }

    // Thiết lập ViewFlipper cho banner quảng cáo
    private void hienThiQuangCao() {
        compositeDisposable.add(apiUser.layQuangCao()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        quangCaoModel -> {
                            if (quangCaoModel.isSuccess() && quangCaoModel.getResult() != null) {
                                mangUserMainQuangCao = quangCaoModel.getResult();
                                viewFlipperMain.removeAllViews(); // Xóa các view cũ để tránh trùng lặp

                                for (int i = 0; i < mangUserMainQuangCao.size(); i++) {
                                    ImageView imageView = new ImageView(UserMainActivity.this);
                                    Glide.with(UserMainActivity.this)
                                            .load(mangUserMainQuangCao.get(i).getHinhAnhQuangCao())
                                            .into(imageView);
                                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                                    viewFlipperMain.addView(imageView); // Quan trọng: Thêm từng ảnh vào Flipper
                                }

                                // Kích hoạt tự động chuyển cảnh nếu có nhiều hơn 1 ảnh
                                if (mangUserMainQuangCao.size() > 1) {
                                    viewFlipperMain.setFlipInterval(3000); // 3 giây chuyển 1 lần
                                    viewFlipperMain.setAutoStart(true);
                                    viewFlipperMain.startFlipping(); // Lệnh bắt đầu chạy

                                    // Thêm hiệu ứng chuyển động
                                    viewFlipperMain.setInAnimation(this, R.anim.quang_cao_slide_in_right);
                                    viewFlipperMain.setOutAnimation(this, R.anim.quang_cao_slide_out_right);
                                }
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Lỗi: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    // Lấy dữ liệu danh mục từ API
    private void hienThiDanhMuc() {
        compositeDisposable.add(apiUser.layDanhMuc()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        DanhMucModel -> {
                            if (DanhMucModel.isSuccess()) {
                                mangUserMainDanhMuc = DanhMucModel.getResult();
                                userMainDanhMucAdapter = new UserMainDanhMucAdapter(getApplicationContext(), mangUserMainDanhMuc);
                                recyclerViewDanhMucMain.setAdapter(userMainDanhMucAdapter);
                            } else {
                                Toast.makeText(getApplicationContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> Toast.makeText(getApplicationContext(), "Lỗi kết nối server", Toast.LENGTH_LONG).show()
                )
        );
    }

    // Lấy dữ liệu sản phẩm mới từ API
    private void hienThiSanPhamMoi() {
        compositeDisposable.add(apiUser.laySanPhamMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        SanPhamMoiModel -> {
                            if (SanPhamMoiModel.isSuccess()) {
                                mangUserSanPhamMoi = SanPhamMoiModel.getResult();
                                userMainSanPhamMoiAdapter = new UserMainSanPhamMoiAdapter(getApplicationContext(), mangUserSanPhamMoi);
                                recyclerViewMain.setAdapter(userMainSanPhamMoiAdapter);
                            } else {
                                Toast.makeText(getApplicationContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> Toast.makeText(getApplicationContext(), "Lỗi kết nối server", Toast.LENGTH_LONG).show()
                )
        );
    }

    // Xử lý sự kiện click
    public void xuLySuKienClick() {
        View.OnClickListener timKiemClickListenner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getAlpha() > 0) {
                    Intent intent = new Intent(getApplicationContext(), UserTimKiemSanPhamActivity.class);
                    startActivity(intent);
                }
            }
        };
        cardViewTimKiemToolbarMain.setOnClickListener(timKiemClickListenner);
        cardViewTimKiemMain.setOnClickListener(timKiemClickListenner);
    }

}