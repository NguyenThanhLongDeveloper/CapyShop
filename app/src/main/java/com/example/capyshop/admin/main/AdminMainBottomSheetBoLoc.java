package com.example.capyshop.admin.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.capyshop.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AdminMainBottomSheetBoLoc extends BottomSheetDialogFragment {

    public interface OnApDungBoLocListener {
        void onApDungBoLoc(Date ngayBatDau, Date ngayKetThuc);
    }

    private OnApDungBoLocListener langNghe;
    private Date ngayBatDau, ngayKetThuc;
    private boolean dangChonBatDau = true;
    private SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    private TextView tvAdminTuNgayBoLoc, tvAdminDenNgayBoLoc;
    private LinearLayout llAdminKhungTuNgayBoLoc, llAdminKhungDenNgayBoLoc;
    private CalendarView cvAdminLichBoLoc;

    public AdminMainBottomSheetBoLoc(OnApDungBoLocListener langNghe) {
        this.langNghe = langNghe;

        // Mặc định: Từ đầu tháng đến hiện tại
        Calendar cal = Calendar.getInstance();
        ngayKetThuc = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        ngayBatDau = cal.getTime();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_main_bottomsheet_boloc_layout, container, false);

        tvAdminTuNgayBoLoc = view.findViewById(R.id.tv_admin_tu_ngay_bo_loc);
        tvAdminDenNgayBoLoc = view.findViewById(R.id.tv_admin_den_ngay_bo_loc);
        llAdminKhungTuNgayBoLoc = view.findViewById(R.id.ll_admin_khung_tu_ngay_bo_loc);
        llAdminKhungDenNgayBoLoc = view.findViewById(R.id.ll_admin_khung_den_ngay_bo_loc);
        cvAdminLichBoLoc = view.findViewById(R.id.cv_admin_lich_bo_loc);

        capNhatNhanNgay();

        // Mặc định chọn ngày bắt đầu
        dangChonBatDau = true;
        toDamVungChon();

        // Xử lý chọn ô nhập
        llAdminKhungTuNgayBoLoc.setOnClickListener(v -> {
            dangChonBatDau = true;
            toDamVungChon();
            cvAdminLichBoLoc.setDate(ngayBatDau.getTime());
        });
 
        llAdminKhungDenNgayBoLoc.setOnClickListener(v -> {
            dangChonBatDau = false;
            toDamVungChon();
            cvAdminLichBoLoc.setDate(ngayKetThuc.getTime());
        });

        // Xử lý thay đổi ngày trên lịch
        cvAdminLichBoLoc.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, dayOfMonth);
            if (dangChonBatDau) {
                ngayBatDau = cal.getTime();
            } else {
                ngayKetThuc = cal.getTime();
            }
            capNhatNhanNgay();
        });

        // Nút Reset
        view.findViewById(R.id.bt_admin_dat_lai_bo_loc).setOnClickListener(v -> {
            if (langNghe != null) {
                langNghe.onApDungBoLoc(null, null);
            }
            dismiss();
        });

        // Nút Áp dụng
        view.findViewById(R.id.bt_admin_ap_dung_bo_loc).setOnClickListener(v -> {
            if (langNghe != null) {
                langNghe.onApDungBoLoc(ngayBatDau, ngayKetThuc);
            }
            dismiss();
        });

        return view;
    }

    private void capNhatNhanNgay() {
        tvAdminTuNgayBoLoc.setText(dinhDangNgay.format(ngayBatDau));
        tvAdminDenNgayBoLoc.setText(dinhDangNgay.format(ngayKetThuc));
    }

    private void toDamVungChon() {
        if (dangChonBatDau) {
            llAdminKhungTuNgayBoLoc.setBackgroundResource(R.drawable.da_chon_backgroud);
            llAdminKhungDenNgayBoLoc.setBackgroundResource(R.drawable.chua_chon_backgroud);
        } else {
            llAdminKhungTuNgayBoLoc.setBackgroundResource(R.drawable.chua_chon_backgroud);
            llAdminKhungDenNgayBoLoc.setBackgroundResource(R.drawable.da_chon_backgroud);
        }
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }
}
