package com.example.capyshop.admin.quangcao;

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
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.capyshop.R;
import com.example.capyshop.common.retrofit.ApiAdmin;
import com.example.capyshop.common.retrofit.RetrofitClient;
import com.example.capyshop.common.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdminQuanLyQuangCaoBottomSheet extends BottomSheetDialogFragment {

    private OnActionThanhCong listenerThanhCong;

    private ImageView ivAnhXemTruoc;
    private LinearLayout llAnhGiuCho;
    private Uri uriAnhChon;

    private ApiAdmin apiAdmin;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public interface OnActionThanhCong {
        void onSuccess();
    }

    public AdminQuanLyQuangCaoBottomSheet(OnActionThanhCong listenerThanhCong) {
        this.listenerThanhCong = listenerThanhCong;
    }

    private final ActivityResultLauncher<Intent> chonAnhLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    uriAnhChon = result.getData().getData();
                    if (ivAnhXemTruoc != null && llAnhGiuCho != null) {
                        ivAnhXemTruoc.setVisibility(View.VISIBLE);
                        llAnhGiuCho.setVisibility(View.GONE);
                        Glide.with(this).load(uriAnhChon).into(ivAnhXemTruoc);
                    }
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_quanlyquangcao_bottomsheet_layout, container, false);
        apiAdmin = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiAdmin.class);
        anhXa(view);
        xuLySuKienClick(view);
        return view;
    }

    private void anhXa(View view) {
        ivAnhXemTruoc = view.findViewById(R.id.iv_admin_quang_cao_bieu_mau_anh_xem_truoc);
        llAnhGiuCho = view.findViewById(R.id.ll_admin_quang_cao_bieu_mau_anh_giu_cho_khung_chua);
    }

    private void xuLySuKienClick(View view) {
        ConstraintLayout clChonAnh = view.findViewById(R.id.cl_admin_quang_cao_bieu_mau_chon_anh_khung_chua);
        AppCompatButton btnLuu = view.findViewById(R.id.bt_admin_quang_cao_bieu_mau_luu);
        AppCompatButton btnHuy = view.findViewById(R.id.bt_admin_quang_cao_bieu_mau_huy);
        AppCompatButton btnChonFile = view.findViewById(R.id.bt_admin_quang_cao_bieu_mau_chon_tep);

        btnHuy.setOnClickListener(v -> dismiss());

        View.OnClickListener itemChonAnhListener = v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            chonAnhLauncher.launch(intent);
        };
        clChonAnh.setOnClickListener(itemChonAnhListener);
        btnChonFile.setOnClickListener(itemChonAnhListener);

        btnLuu.setOnClickListener(v -> {
            if (uriAnhChon != null) {
                // Có chọn ảnh -> Upload -> Lưu
                taiAnhLenServer(uriAnhChon);
            } else {
                Toast.makeText(getContext(), "Vui lòng chọn hình ảnh", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void taiAnhLenServer(Uri uri) {
        Utils.taiAnhLenServer(getContext(), uri, "quangcao", compositeDisposable, new Utils.OnUploadCallback() {
            @Override
            public void onThanhCong(String tenFileMoi) {
                String hinhAnhMoi = Utils.BASE_URL + "common/images/" + tenFileMoi;
                luuQuangCaoVaoDatabase(hinhAnhMoi);
            }

            @Override
            public void onThatBai(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void luuQuangCaoVaoDatabase(String hinh) {
        compositeDisposable.add(apiAdmin.themQuangCao(hinh)
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
