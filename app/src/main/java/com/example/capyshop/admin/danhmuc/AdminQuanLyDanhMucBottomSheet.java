package com.example.capyshop.admin.danhmuc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.capyshop.R;
import com.example.capyshop.common.danhmuc.DanhMuc;
import com.example.capyshop.common.danhmuc.DanhMucModel;
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

public class AdminQuanLyDanhMucBottomSheet extends BottomSheetDialogFragment {

    private DanhMuc danhMucSua;
    private OnActionThanhCong listenerThanhCong;

    private TextInputEditText etAdminDanhMucBieuMauTen;
    private ImageView ivAdminDanhMucBieuMauAnhXemTruoc;
    private LinearLayout llAdminDanhMucBieuMauAnhGiuCho;
    private Uri uriAnhChon;

    // New Fields
    private TextInputEditText etAdminDanhMucBieuMauThemThuongHieu, etAdminDanhMucBieuMauThemThuocTinh;
    private AppCompatButton btAdminDanhMucBieuMauThemThuongHieu, btAdminDanhMucBieuMauThemThuocTinh;
    private com.google.android.material.chip.ChipGroup cgAdminDanhMucBieuMauThuongHieu, cgAdminDanhMucBieuMauThuocTinh;

    private ApiAdmin apiAdmin;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public interface OnActionThanhCong {
        void onSuccess();
    }

    public AdminQuanLyDanhMucBottomSheet(DanhMuc danhMucSua, OnActionThanhCong listenerThanhCong) {
        this.danhMucSua = danhMucSua;
        this.listenerThanhCong = listenerThanhCong;
    }

    private final ActivityResultLauncher<Intent> chonAnhLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    uriAnhChon = result.getData().getData();
                    if (ivAdminDanhMucBieuMauAnhXemTruoc != null && llAdminDanhMucBieuMauAnhGiuCho != null) {
                        ivAdminDanhMucBieuMauAnhXemTruoc.setVisibility(View.VISIBLE);
                        llAdminDanhMucBieuMauAnhGiuCho.setVisibility(View.GONE);
                        Glide.with(this).load(uriAnhChon).into(ivAdminDanhMucBieuMauAnhXemTruoc);
                    }
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_quanlydanhmuc_bottomsheet_layout, container, false);
        apiAdmin = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiAdmin.class);
        anhXa(view);
        doDuLieu();
        xuLySuKienClick(view);
        return view;
    }

    private void anhXa(View view) {
        etAdminDanhMucBieuMauTen = view.findViewById(R.id.et_admin_danh_muc_bieu_mau_ten);
        ivAdminDanhMucBieuMauAnhXemTruoc = view.findViewById(R.id.iv_admin_danh_muc_bieu_mau_anh_xem_truoc);
        llAdminDanhMucBieuMauAnhGiuCho = view.findViewById(R.id.ll_admin_danh_muc_bieu_mau_anh_giu_cho);

        // Map Valid New Views
        etAdminDanhMucBieuMauThemThuongHieu = view.findViewById(R.id.et_admin_danh_muc_bieu_mau_them_thuong_hieu);
        etAdminDanhMucBieuMauThemThuocTinh = view.findViewById(R.id.et_admin_danh_muc_bieu_mau_them_thuoc_tinh);
        btAdminDanhMucBieuMauThemThuongHieu = view.findViewById(R.id.bt_admin_danh_muc_bieu_mau_them_thuong_hieu);
        btAdminDanhMucBieuMauThemThuocTinh = view.findViewById(R.id.bt_admin_danh_muc_bieu_mau_them_thuoc_tinh);
        cgAdminDanhMucBieuMauThuongHieu = view.findViewById(R.id.cg_admin_danh_muc_bieu_mau_thuong_hieu);
        cgAdminDanhMucBieuMauThuocTinh = view.findViewById(R.id.cg_admin_danh_muc_bieu_mau_thuoc_tinh);
    }

    private void doDuLieu() {
        if (danhMucSua != null) {
            etAdminDanhMucBieuMauTen.setText(danhMucSua.getTenDanhMuc());
            if (danhMucSua.getHinhAnhDanhMuc() != null && !danhMucSua.getHinhAnhDanhMuc().isEmpty()) {
                ivAdminDanhMucBieuMauAnhXemTruoc.setVisibility(View.VISIBLE);
                llAdminDanhMucBieuMauAnhGiuCho.setVisibility(View.GONE);
                Glide.with(this).load(danhMucSua.getHinhAnhDanhMuc()).into(ivAdminDanhMucBieuMauAnhXemTruoc);
            }

            // Gọi API lấy chi tiết để đổ dữ liệu Thương hiệu/Thuộc tính
            layChiTietDanhMuc(danhMucSua.getMaDanhMuc());
        }
    }

    private void layChiTietDanhMuc(int maDanhMuc) {
        compositeDisposable.add(apiAdmin.layChiTietDanhMuc(maDanhMuc)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            if (model.isSuccess() && model.getResult() != null && !model.getResult().isEmpty()) {
                                DanhMuc chiTietInfo = model.getResult().get(0);
                                // Load Thương Hiệu
                                if (chiTietInfo.getDanhSachThuongHieu() != null) {
                                    for (String th : chiTietInfo.getDanhSachThuongHieu()) {
                                        addChip(th, cgAdminDanhMucBieuMauThuongHieu);
                                    }
                                }
                                // Load Thuộc Tính
                                if (chiTietInfo.getDanhSachThuocTinh() != null) {
                                    for (String tt : chiTietInfo.getDanhSachThuocTinh()) {
                                        addChip(tt, cgAdminDanhMucBieuMauThuocTinh);
                                    }
                                }
                            }
                        },
                        throwable -> Toast.makeText(getContext(), "Lỗi tải chi tiết: " + throwable.getMessage(),
                                Toast.LENGTH_SHORT).show()));
    }

    // Override onCreateView fix to make sure tvTieuDe is correctly set
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (danhMucSua != null) {
            TextView tvTieuDe = view.findViewById(R.id.tv_admin_danh_muc_bieu_mau_tieu_de);
            tvTieuDe.setText("Chỉnh Sửa Danh Mục");
        }
    }

    private void xuLySuKienClick(View view) {
        ConstraintLayout clChonAnh = view.findViewById(R.id.cl_admin_danh_muc_bieu_mau_chon_anh);
        AppCompatButton btnLuu = view.findViewById(R.id.bt_admin_danh_muc_bieu_mau_luu);
        AppCompatButton btnHuy = view.findViewById(R.id.bt_admin_danh_muc_bieu_mau_huy);
        AppCompatButton btnChonFile = view.findViewById(R.id.bt_admin_danh_muc_bieu_mau_chon_tep);

        btnHuy.setOnClickListener(v -> dismiss());

        View.OnClickListener itemChonAnhListener = v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            chonAnhLauncher.launch(intent);
        };
        clChonAnh.setOnClickListener(itemChonAnhListener);
        btnChonFile.setOnClickListener(itemChonAnhListener);

        // Add Chip Events
        btAdminDanhMucBieuMauThemThuongHieu.setOnClickListener(v -> {
            String text = etAdminDanhMucBieuMauThemThuongHieu.getText().toString().trim();
            if (!text.isEmpty()) {
                addChip(text, cgAdminDanhMucBieuMauThuongHieu);
                etAdminDanhMucBieuMauThemThuongHieu.setText("");
            }
        });

        btAdminDanhMucBieuMauThemThuocTinh.setOnClickListener(v -> {
            String text = etAdminDanhMucBieuMauThemThuocTinh.getText().toString().trim();
            if (!text.isEmpty()) {
                addChip(text, cgAdminDanhMucBieuMauThuocTinh);
                etAdminDanhMucBieuMauThemThuocTinh.setText("");
            }
        });

        btnLuu.setOnClickListener(v -> {
            String ten = etAdminDanhMucBieuMauTen.getText().toString().trim();
            if (ten.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập tên danh mục", Toast.LENGTH_SHORT).show();
                return;
            }

            if (uriAnhChon != null) {
                // Có chọn ảnh mới -> Upload trước
                taiAnhLen(uriAnhChon, ten);
            } else {
                // Không chọn ảnh mới -> Lưu luôn (dùng ảnh cũ nếu có)
                String hinh = (danhMucSua != null) ? danhMucSua.getHinhAnhDanhMuc() : "";
                luuDanhMuc(ten, hinh);
            }
        });
    }

    private void addChip(String text, com.google.android.material.chip.ChipGroup group) {
        com.google.android.material.chip.Chip chip = new com.google.android.material.chip.Chip(getContext());
        chip.setText(text);
        chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(v -> group.removeView(chip));
        group.addView(chip);
    }

    private java.util.List<String> getChipValues(com.google.android.material.chip.ChipGroup group) {
        java.util.List<String> values = new java.util.ArrayList<>();
        for (int i = 0; i < group.getChildCount(); i++) {
            com.google.android.material.chip.Chip chip = (com.google.android.material.chip.Chip) group.getChildAt(i);
            values.add(chip.getText().toString());
        }
        return values;
    }

    private void taiAnhLen(Uri uri, String tenDanhMuc) {
        Utils.taiAnhLenServer(getContext(), uri, "danhmuc", compositeDisposable, new Utils.OnUploadCallback() {
            @Override
            public void onThanhCong(String tenFileMoi) {
                // Upload thành công -> Lưu danh mục với tên file mới
                String hinhAnhMoi = Utils.BASE_URL + "common/images/" + tenFileMoi;
                luuDanhMuc(tenDanhMuc, hinhAnhMoi);
            }

            @Override
            public void onThatBai(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void luuDanhMuc(String ten, String hinh) {
        // Collect Data
        String danhSachThuongHieuJson = new com.google.gson.Gson().toJson(getChipValues(cgAdminDanhMucBieuMauThuongHieu));
        String danhSachThuocTinhJson = new com.google.gson.Gson().toJson(getChipValues(cgAdminDanhMucBieuMauThuocTinh));

        io.reactivex.rxjava3.core.Observable<DanhMucModel> observable;
        if (danhMucSua == null) {
            observable = apiAdmin.themDanhMuc(ten, hinh, danhSachThuongHieuJson, danhSachThuocTinhJson);
        } else {
            observable = apiAdmin.suaDanhMuc(danhMucSua.getMaDanhMuc(), ten, hinh, danhSachThuongHieuJson,
                    danhSachThuocTinhJson);
        }

        compositeDisposable.add(observable
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
