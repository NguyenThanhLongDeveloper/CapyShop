package com.example.capyshop.admin.donvivanchuyen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.example.capyshop.R;
import com.example.capyshop.common.donvivanchuyen.DonViVanChuyen;
import com.example.capyshop.common.retrofit.ApiAdmin;
import com.example.capyshop.common.retrofit.RetrofitClient;
import com.example.capyshop.common.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdminQuanLyDonViVanChuyenBottomSheet extends BottomSheetDialogFragment {

    private OnActionThanhCong listenerThanhCong;

    private TextInputEditText etAdminDonViVanChuyenBieuMauTen, etAdminDonViVanChuyenBieuMauGia;
    private ApiAdmin apiAdmin;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public interface OnActionThanhCong {
        void onSuccess();
    }

    private DonViVanChuyen donViEdit;
    private androidx.appcompat.widget.SwitchCompat swAdminDonViVanChuyenBieuMauTrangThai;
    private TextView tvAdminDonViVanChuyenBieuMauTieuDe;

    public AdminQuanLyDonViVanChuyenBottomSheet(OnActionThanhCong listenerThanhCong) {
        this.listenerThanhCong = listenerThanhCong;
    }

    public AdminQuanLyDonViVanChuyenBottomSheet(DonViVanChuyen donVi, OnActionThanhCong listenerThanhCong) {
        this.donViEdit = donVi;
        this.listenerThanhCong = listenerThanhCong;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_quanlydonvivanchuyen_bottomsheet_layout, container, false);
        apiAdmin = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiAdmin.class);
        anhXa(view);
        checkEditMode();
        xuLySuKienClick(view);
        return view;
    }

    private void anhXa(View view) {
        etAdminDonViVanChuyenBieuMauTen = view.findViewById(R.id.et_admin_don_vi_van_chuyen_bieu_mau_ten);
        etAdminDonViVanChuyenBieuMauGia = view.findViewById(R.id.et_admin_don_vi_van_chuyen_bieu_mau_gia);
        swAdminDonViVanChuyenBieuMauTrangThai = view.findViewById(R.id.sw_admin_don_vi_van_chuyen_bieu_mau_trang_thai);
        tvAdminDonViVanChuyenBieuMauTieuDe = view.findViewById(R.id.tv_admin_don_vi_van_chuyen_bieu_mau_tieu_de);
    }

    private void checkEditMode() {
        if (donViEdit != null) {
            tvAdminDonViVanChuyenBieuMauTieuDe.setText("Chỉnh Sửa Đơn Vị");
            etAdminDonViVanChuyenBieuMauTen.setText(donViEdit.getTenDonViVanChuyen());
            etAdminDonViVanChuyenBieuMauGia.setText(String.valueOf(donViEdit.getGiaDonViVanChuyen()));
            swAdminDonViVanChuyenBieuMauTrangThai.setChecked("HOAT_DONG".equals(donViEdit.getTrangThai()));
        }
    }

    private void xuLySuKienClick(View view) {
        AppCompatButton btnLuu = view.findViewById(R.id.bt_admin_don_vi_van_chuyen_bieu_mau_luu);
        AppCompatButton btnHuy = view.findViewById(R.id.bt_admin_don_vi_van_chuyen_bieu_mau_huy);

        btnHuy.setOnClickListener(v -> dismiss());

        btnLuu.setOnClickListener(v -> {
            String ten = etAdminDonViVanChuyenBieuMauTen.getText().toString().trim();
            String giaStr = etAdminDonViVanChuyenBieuMauGia.getText().toString().trim();
            String trangThai = swAdminDonViVanChuyenBieuMauTrangThai.isChecked() ? "HOAT_DONG" : "TAM_TAT";

            if (ten.isEmpty() || giaStr.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                long gia = Long.parseLong(giaStr);
                if (donViEdit != null) {
                    capNhatDonViVanChuyen(donViEdit.getMaDonViVanChuyen(), ten, gia, trangThai);
                } else {
                    themDonViVanChuyen(ten, gia, trangThai);
                }
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Giá không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void themDonViVanChuyen(String ten, long gia, String trangThai) {
        compositeDisposable.add(apiAdmin.themDonViVanChuyen(ten, gia, trangThai)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            if (model.isSuccess()) {
                                // Default status is HOAT_DONG in DB usually, but let's assume we want to sync if needed.
                                // If the API for themDonViVanChuyen doesn't accept trangThai, we might need to update it too.
                                Toast.makeText(getContext(), model.getMessage(), Toast.LENGTH_SHORT).show();
                                if (listenerThanhCong != null)
                                    listenerThanhCong.onSuccess();
                                dismiss();
                            } else {
                                Toast.makeText(getContext(), model.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> Toast.makeText(getContext(), "Lỗi kết nối Server", Toast.LENGTH_SHORT).show()));
    }

    private void capNhatDonViVanChuyen(int ma, String ten, long gia, String trangThai) {
        compositeDisposable.add(apiAdmin.capNhatDonViVanChuyen(ma, ten, gia, trangThai)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            if (model.isSuccess()) {
                                Toast.makeText(getContext(), model.getMessage(), Toast.LENGTH_SHORT).show();
                                if (listenerThanhCong != null)
                                    listenerThanhCong.onSuccess();
                                dismiss();
                            } else {
                                Toast.makeText(getContext(), model.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> Toast.makeText(getContext(), "Lỗi kết nối Server", Toast.LENGTH_SHORT).show()));
    }

    @Override
    public void onStart() {
        super.onStart();
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        if (dialog != null) {
            FrameLayout bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        }
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
