package com.example.capyshop.user.thongtincanhan;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.example.capyshop.R;
import com.example.capyshop.common.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

public class UserThongTinCaNhanBottomSheetCaiDatTaiKhoan extends BottomSheetDialogFragment {

    private TextInputEditText edtBottomSheetHoVaTenThongTinCaNhan, edtBottomSheetSoDienThoaiThongTinCaNhan;
    private TextView tvBottomSheetQuanLyDiaChiThongTinCaNhan;
    private LinearLayout llBottomSheetQuanLyDiaChiThongTinCaNhan;
    private AppCompatButton btnBottomSheetCaiDatTaiKhoanThongTinCaNhan;

    // Interface để gửi dữ liệu về màn hình trước đó
    public interface OnAddressSaveListener {
        void onSave(String hoVaTen, String soDienThoai, String diaChi);
    }
    private OnAddressSaveListener saveListener;

    public UserThongTinCaNhanBottomSheetCaiDatTaiKhoan(OnAddressSaveListener listener) {
        this.saveListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_thongtincanhan_bottomsheet_caidattaikhoan_layout, container, false);
        anhXa(view);
        hienThiThongTin();
        xuLyLuuCaiDatTaiKhoan();

        // Mở danh sách trượt Tỉnh/Huyện/Xã
        llBottomSheetQuanLyDiaChiThongTinCaNhan.setOnClickListener(v -> {
            // Khởi tạo BottomSheet với Interface nhận 3 tham số
            UserThongTinCaNhanBottomSheetDiaChi bottomSheet = new UserThongTinCaNhanBottomSheetDiaChi(new UserThongTinCaNhanBottomSheetDiaChi.OnAddressSaveListener() {
                @Override
                public void onSave(String diaChi) {
                    tvBottomSheetQuanLyDiaChiThongTinCaNhan.setText(diaChi);

                }
            });
            // Quan trọng: Dùng getChildFragmentManager vì đây là sheet lồng trong sheet
            bottomSheet.show(getChildFragmentManager(), "ChonHanhChinh");
        });


        return view;
    }

    private void anhXa(View view) {
        edtBottomSheetHoVaTenThongTinCaNhan = view.findViewById(R.id.edt_bottom_sheet_ho_va_ten_thong_tin_ca_nhan);
        edtBottomSheetSoDienThoaiThongTinCaNhan = view.findViewById(R.id.edt_bottom_sheet_so_dien_thoai_thong_tin_ca_nhan);
        tvBottomSheetQuanLyDiaChiThongTinCaNhan = view.findViewById(R.id.tv_bottom_sheet_quan_ly_dia_chi_thong_tin_ca_nhan);
        llBottomSheetQuanLyDiaChiThongTinCaNhan = view.findViewById(R.id.ll_bottom_sheet_quan_ly_dia_chi_thong_tin_ca_nhan);
        btnBottomSheetCaiDatTaiKhoanThongTinCaNhan = view.findViewById(R.id.btn_bottom_sheet_dia_chi_thong_tin_ca_nhan);
    }

    private void hienThiThongTin() {
        edtBottomSheetHoVaTenThongTinCaNhan.setText(Utils.userNguoiDung_Current.getHoTenNguoiDung());
        edtBottomSheetSoDienThoaiThongTinCaNhan.setText(Utils.userNguoiDung_Current.getSoDienThoai());
        tvBottomSheetQuanLyDiaChiThongTinCaNhan.setText(Utils.userNguoiDung_Current.getDiaChi());

    }

    private void xuLyLuuCaiDatTaiKhoan() {
        btnBottomSheetCaiDatTaiKhoanThongTinCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoVaTen = edtBottomSheetHoVaTenThongTinCaNhan.getText().toString();
                String soDienThoai = edtBottomSheetSoDienThoaiThongTinCaNhan.getText().toString();
                String diaChi = tvBottomSheetQuanLyDiaChiThongTinCaNhan.getText().toString();

                if (TextUtils.isEmpty(hoVaTen)) {
                    edtBottomSheetHoVaTenThongTinCaNhan.setError("Vui lòng nhập họ và tên");
                }else if (TextUtils.isEmpty(soDienThoai)) {
                    edtBottomSheetSoDienThoaiThongTinCaNhan.setError("Vui lòng nhập số điện thoại");
                } else if (soDienThoai.length() != 10) {
                    edtBottomSheetSoDienThoaiThongTinCaNhan.setError("Số điện thoại phải có 10 chữ số.");
                } else {
                    if (saveListener != null) {
                        saveListener.onSave(hoVaTen, soDienThoai, diaChi);
                    }
                    dismiss();
                }
            }
        });





    }


}