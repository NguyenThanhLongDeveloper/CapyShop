package com.example.capyshop.user.dathang;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.example.capyshop.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class UserDatHangThanhCongBottomSheet extends BottomSheetDialogFragment {

    private OnActionListener listener;

    // Khai báo các biến giao diện (Views)
    TextView tvTieuDeDatHangThanhCong, tvTieuDeThongBaoThanhCong, tvNoiDungThongBaoThanhCong;
    ImageView ivHinhAnhDatHangThanhCong;
    AppCompatButton btTiepTucMuaSam, btXemChiTietDonHang;

    public interface OnActionListener {
        void onTiepTucMuaSam();
        void onXemChiTietDonHang();
    }

    public UserDatHangThanhCongBottomSheet(OnActionListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_dathang_thanhcong_bottomsheet_layout, container, false);

        anhXa(view);
        xuLySuKien();

        return view;
    }

    private void anhXa(View view) {
        tvTieuDeDatHangThanhCong = view.findViewById(R.id.tv_tieu_de_dat_hang_thanh_cong);
        tvTieuDeThongBaoThanhCong = view.findViewById(R.id.tv_tieu_de_thong_bao_thanh_cong);
        tvNoiDungThongBaoThanhCong = view.findViewById(R.id.tv_noi_dung_thong_bao_thanh_cong);
        ivHinhAnhDatHangThanhCong = view.findViewById(R.id.iv_hinh_anh_dat_hang_thanh_cong);
        btTiepTucMuaSam = view.findViewById(R.id.bt_tiep_tuc_mua_sam);
        btXemChiTietDonHang = view.findViewById(R.id.bt_xem_chi_tiet_don_hang);
    }

    private void xuLySuKien() {
        btTiepTucMuaSam.setOnClickListener(v -> {
            dismiss();
            if (listener != null) {
                listener.onTiepTucMuaSam();
            }
        });

        btXemChiTietDonHang.setOnClickListener(v -> {
            dismiss();
            if (listener != null) {
                listener.onXemChiTietDonHang();
            }
        });
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
                FrameLayout bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                if (bottomSheet != null) {
                    BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });
        return dialog;
    }
}
