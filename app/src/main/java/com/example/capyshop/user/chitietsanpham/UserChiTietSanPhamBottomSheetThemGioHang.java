package com.example.capyshop.user.chitietsanpham;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capyshop.R;
import com.example.capyshop.user.dangnhap.UserDangNhapActivity;
import com.example.capyshop.common.chitietsanpham.ChiTietSanPhamGiaTriThuocTinh;
import com.example.capyshop.common.chitietsanpham.ChiTietSanPhamThuocTinh;
import com.example.capyshop.common.sanpham.SanPham;
import com.example.capyshop.common.retrofit.ApiUser;
import com.example.capyshop.common.retrofit.RetrofitClient;
import com.example.capyshop.common.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserChiTietSanPhamBottomSheetThemGioHang extends BottomSheetDialogFragment {

    // Giữ nguyên ID của bạn
    ImageView ivBottomSheetHinhAnhChiTietSanPham, ivBottomSheetGiamChiTietSanPham, ivBottomSheetTangChiTietSanPham;
    TextView tvBottomSheetGiaChiTietSanPham, tvBottomSheetSoLuongChiTietSanPham;
    RecyclerView rvBottomSheetThuocTinhChiTietSanPham;
    AppCompatButton btBottomSheetThemGioHangChiTietSanPham;

    private SanPham userSanPham;
    private ApiUser apiUser;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private UserChiTietSanPhamThuocTinhAdapter userChiTietSanPhamThuocTinhAdapter;

    // Interface để báo về Activity cập nhật Badge
    public interface OnAddToCartListener {
        void onAdded();
    }
    private OnAddToCartListener mListener;

    public UserChiTietSanPhamBottomSheetThemGioHang(SanPham userSanPham, OnAddToCartListener listener) {
        this.userSanPham = userSanPham;
        this.mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_chitietsanpham_bottomsheet_layout, container, false);
        apiUser = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiUser.class);
        anhXa(view);
        hienThiThuocTinhVaGia();
        xuLyTangGiamSoLuong();
        xuLyThemGioHang();
        return view;
    }

    private void anhXa(View view) {
        ivBottomSheetHinhAnhChiTietSanPham = view.findViewById(R.id.iv_bottom_sheet_hinh_anh_chi_tiet_san_pham);
        ivBottomSheetGiamChiTietSanPham = view.findViewById(R.id.iv_bottom_sheet_giam_chi_tiet_san_pham);
        ivBottomSheetTangChiTietSanPham = view.findViewById(R.id.iv_bottom_sheet_tang_chi_tiet_san_pham);
        tvBottomSheetGiaChiTietSanPham = view.findViewById(R.id.tv_bottom_sheet_gia_chi_tiet_san_pham);
        tvBottomSheetSoLuongChiTietSanPham = view.findViewById(R.id.tv_bottom_sheet_so_luong_chi_tiet_san_pham);
        rvBottomSheetThuocTinhChiTietSanPham = view.findViewById(R.id.rv_bottom_sheet_thuoc_tinh_chi_tiet_san_pham);
        btBottomSheetThemGioHangChiTietSanPham = view.findViewById(R.id.bt_bottom_sheet_them_gio_hang_chi_tiet_san_pham);

        rvBottomSheetThuocTinhChiTietSanPham.setHasFixedSize(true);
        rvBottomSheetThuocTinhChiTietSanPham.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void hienThiThuocTinhVaGia() {
        if (userSanPham != null) {
            NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
            tvBottomSheetGiaChiTietSanPham.setText(numberFormat.format(userSanPham.getGiaSanPham()) + " đ");
            Glide.with(this).load(userSanPham.getHinhAnhSanPham()).into(ivBottomSheetHinhAnhChiTietSanPham);

            if (userSanPham.getThuocTinh() != null) {
                userChiTietSanPhamThuocTinhAdapter = new UserChiTietSanPhamThuocTinhAdapter(getContext(), userSanPham.getThuocTinh(), () -> {
                    // Cập nhật giao diện nếu cần khi chọn thuộc tính
                });
                rvBottomSheetThuocTinhChiTietSanPham.setAdapter(userChiTietSanPhamThuocTinhAdapter);
            }
        }
    }

    private void xuLyTangGiamSoLuong() {
        ivBottomSheetGiamChiTietSanPham.setOnClickListener(v -> {
            int soLuong = Integer.parseInt(tvBottomSheetSoLuongChiTietSanPham.getText().toString());
            if (soLuong > 1) {
                soLuong--;
                tvBottomSheetSoLuongChiTietSanPham.setText(String.valueOf(soLuong));
            }
        });

        ivBottomSheetTangChiTietSanPham.setOnClickListener(v -> {
            int soLuong = Integer.parseInt(tvBottomSheetSoLuongChiTietSanPham.getText().toString());
            if (soLuong < 10) {
                soLuong++;
                tvBottomSheetSoLuongChiTietSanPham.setText(String.valueOf(soLuong));
            } else {
                Toast.makeText(getContext(), "Số lượng đạt giới hạn", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void xuLyThemGioHang() {
        btBottomSheetThemGioHangChiTietSanPham.setOnClickListener(v -> {
            if (Utils.userNguoiDung_Current == null) {
                Utils.thietLapBottomSheetDialog(getContext(), "Vui lòng đăng nhập", "Vui lòng đăng nhập để sử dụng", "Đăng nhập", () -> {
                    dismiss();
                    startActivity(new Intent(getContext(), UserDangNhapActivity.class));
                });
                return;
            }
            List<Integer> mangThuocTinh = layDanhSachIdThuocTinhDaChon(true);
            if (mangThuocTinh != null) {
                int maNguoiDung = Utils.userNguoiDung_Current.getMaNguoiDung();
                int maSanPham = userSanPham.getMaSanPham();
                int soLuong = Integer.parseInt(tvBottomSheetSoLuongChiTietSanPham.getText().toString());
                String thuocTinhDaChonJson = new Gson().toJson(mangThuocTinh);

                compositeDisposable.add(apiUser.themGioHang(maNguoiDung, maSanPham, soLuong, thuocTinhDaChonJson)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                gioHangModel -> {
                                    if (gioHangModel.isSuccess()) {
                                        Toast.makeText(getContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                                        if (mListener != null) mListener.onAdded();
                                        dismiss();
                                    } else {
                                        Toast.makeText(getContext(), gioHangModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                },
                                throwable -> Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show()
                        ));
            }

        });
    }

    private List<Integer> layDanhSachIdThuocTinhDaChon(boolean hienLoi) {
        List<Integer> mangThuocTinh = new ArrayList<>();
        if (userSanPham.getThuocTinh() != null) {
            for (ChiTietSanPhamThuocTinh tt : userSanPham.getThuocTinh()) {
                int idChon = -1;
                for (ChiTietSanPhamGiaTriThuocTinh gt : tt.getGiaTri()) {
                    if (gt.isSelected()) {
                        idChon = gt.getMaSanPhamThuocTinh();
                        break;
                    }
                }
                if (idChon == -1) {
                    if (hienLoi) Toast.makeText(getContext(), "Vui lòng chọn: " + tt.getTenThuocTinh(), Toast.LENGTH_SHORT).show();
                    return null;
                }
                mangThuocTinh.add(idChon);
            }
        }
        return mangThuocTinh;
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}