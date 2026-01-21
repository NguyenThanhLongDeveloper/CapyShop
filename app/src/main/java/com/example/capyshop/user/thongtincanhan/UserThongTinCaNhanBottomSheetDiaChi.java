package com.example.capyshop.user.thongtincanhan;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.example.capyshop.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

public class UserThongTinCaNhanBottomSheetDiaChi extends BottomSheetDialogFragment {

    private TextInputEditText edtBottomSheetDiaChiThongTinCaNhan;
    private TextView tvBottomSheetDiaChiThongTinCaNhan;
    private LinearLayout llBottomSheetDiaChiThongTinCaNhan;
    private AppCompatButton btnBottomSheetDiaChiThongTinCaNhan;

    // Interface để gửi dữ liệu về màn hình trước đó
    public interface OnAddressSaveListener {
        void onSave(String diaChi);
    }
    private OnAddressSaveListener saveListener;

    public UserThongTinCaNhanBottomSheetDiaChi(OnAddressSaveListener saveListener) {
        this.saveListener = saveListener;
    }

    public UserThongTinCaNhanBottomSheetDiaChi(int contentLayoutId, OnAddressSaveListener saveListener) {
        super(contentLayoutId);
        this.saveListener = saveListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_thongtincanhan_bottomsheet_diachi_layout, container, false);

        anhXa(view);
        xuLyLuuDiaChi();

        // Mở danh sách trượt Tỉnh/Huyện/Xã
        llBottomSheetDiaChiThongTinCaNhan.setOnClickListener(v -> {
            UserThongTinCaNhanBottomSheetChonDiaChiHanhChinh chonSheet = new UserThongTinCaNhanBottomSheetChonDiaChiHanhChinh((tinh, huyen, xa) -> {
                String result = xa + ", " + huyen + ", " + tinh;
                tvBottomSheetDiaChiThongTinCaNhan.setText(result);
                tvBottomSheetDiaChiThongTinCaNhan.setTextColor(Color.BLACK);
            });
            // Quan trọng: Dùng getChildFragmentManager vì đây là sheet lồng trong sheet
            chonSheet.show(getChildFragmentManager(), "ChonHanhChinh");
        });

        return view;
    }

    private void anhXa(View view) {
        edtBottomSheetDiaChiThongTinCaNhan = view.findViewById(R.id.edt_bottom_sheet_dia_chi_thong_tin_ca_nhan);
        tvBottomSheetDiaChiThongTinCaNhan = view.findViewById(R.id.tv_bottom_sheet_dia_chi_thong_tin_ca_nhan);
        llBottomSheetDiaChiThongTinCaNhan = view.findViewById(R.id.ll_bottom_sheet_dia_chi_thong_tin_ca_nhan);
        btnBottomSheetDiaChiThongTinCaNhan = view.findViewById(R.id.btn_bottom_sheet_dia_chi_thong_tin_ca_nhan);


    }



    private void xuLyLuuDiaChi() {
        btnBottomSheetDiaChiThongTinCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String diaChiHanhChinh = tvBottomSheetDiaChiThongTinCaNhan.getText().toString();
                String diaChiChiTiet = edtBottomSheetDiaChiThongTinCaNhan.getText().toString().trim();

                if (diaChiHanhChinh.equals("Tỉnh/Thành phố, Quận/Huyện, Phường/Xã")) {
                    Toast.makeText(getContext(), "Vui lòng chọn địa chỉ tỉnh thành", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(diaChiChiTiet)) {
                    edtBottomSheetDiaChiThongTinCaNhan.setError("Vui lòng điền đủ thông tin số nhà và tên đường");
                }else {
                    if (saveListener != null) {
                        saveListener.onSave(diaChiChiTiet + ", " + diaChiHanhChinh);
                    }
                    dismiss();
                }
            }
        });




    }


}