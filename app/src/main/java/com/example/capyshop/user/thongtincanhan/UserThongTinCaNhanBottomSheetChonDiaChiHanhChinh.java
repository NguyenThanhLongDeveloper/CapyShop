package com.example.capyshop.user.thongtincanhan;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capyshop.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserThongTinCaNhanBottomSheetChonDiaChiHanhChinh extends BottomSheetDialogFragment {

    private TabLayout tlBottomSheetChonDiaChiHanhChinh;
    private RecyclerView rvBottomSheetChonDiaChiHanhChinh;
    private List<UserThongTinCaNhanTinh> mangDiaChiHanhChinh;

    private UserThongTinCaNhanTinh userThongTinCaNhanTinhDaChon;
    private UserThongTinCaNhanHuyen userThongTinCaNhanHuyenDaChon;

    public interface OnAddressResultListener {
        void onResult(String tinh, String huyen, String xa);
    }

    private OnAddressResultListener listener;

    public UserThongTinCaNhanBottomSheetChonDiaChiHanhChinh(OnAddressResultListener listener) {
        this.listener = listener;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_thongtincanhan_bottomsheet_chondiachihanhchinh_layout, container, false);
        anhXa(view);
        layDuLieuDiaChiHanhChinh();
        hienThiTinh(); // Mặc định hiển thị danh sách Tỉnh

        // Lắng nghe khi người dùng click thủ công vào các Tab để quay lại
        tlBottomSheetChonDiaChiHanhChinh.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: hienThiTinh(); break;
                    case 1: if (userThongTinCaNhanTinhDaChon != null) hienThiHuyen(); break;
                    case 2: if (userThongTinCaNhanHuyenDaChon != null) hienThiXa(); break;
                }
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        return view;
    }

    private void anhXa(View view) {

        tlBottomSheetChonDiaChiHanhChinh = view.findViewById(R.id.tl_bottom_sheet_chon_dia_chi_hanh_chinh);
        rvBottomSheetChonDiaChiHanhChinh = view.findViewById(R.id.rv_bottom_sheet_chon_dia_chi_hanh_chinh);

        rvBottomSheetChonDiaChiHanhChinh.setLayoutManager(new LinearLayoutManager(getContext()));
        rvBottomSheetChonDiaChiHanhChinh.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

    }

    public static String layDuLieuDiaChiVietNam(Context context, String fileName) {
        if (context == null) return null;

        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            Log.e("Utils_Error", "Không tìm thấy file: " + fileName + " trong assets");
            return null;
        }
        return jsonString;
    }

    private void layDuLieuDiaChiHanhChinh() {
        // Luôn lấy context từ getContext() một cách an toàn
        Context context = getContext();
        if (context == null) return;

        String json = layDuLieuDiaChiVietNam(context, "dia_chi_hanh_chinh_vietnam.json");
        Gson gson = new Gson();
        Type listType = new TypeToken<List<UserThongTinCaNhanTinh>>() {}.getType();
        mangDiaChiHanhChinh = gson.fromJson(json, listType);
    }

    private void hienThiTinh() {

        List<String> names = new ArrayList<>();
        for (UserThongTinCaNhanTinh t : mangDiaChiHanhChinh) { // Dòng 104 của bạn nằm ở đây
            names.add(t.getName());
        }
        rvBottomSheetChonDiaChiHanhChinh.setAdapter(new UserThongTinCaNhanBottomSheetChonDiaChiHanhChinhAdapter(names, name -> {
            for (UserThongTinCaNhanTinh userThongTinCaNhanTinh : mangDiaChiHanhChinh) {
                if (userThongTinCaNhanTinh.getName().equals(name)) {
                    userThongTinCaNhanTinhDaChon = userThongTinCaNhanTinh;
                    tlBottomSheetChonDiaChiHanhChinh.getTabAt(0).setText(name); // Đổi tên Tab thành tên tỉnh đã chọn
                    tlBottomSheetChonDiaChiHanhChinh.getTabAt(1).setText("Quận/Huyện"); // Reset tab tiếp theo
                    hienThiHuyen();
                    break;
                }
            }
        }));
    }

    private void hienThiHuyen() {
        if (userThongTinCaNhanTinhDaChon == null) return;

        List<String> names = new ArrayList<>();
        for (UserThongTinCaNhanHuyen userThongTinCaNhanHuyen : userThongTinCaNhanTinhDaChon.getDistricts()) names.add(userThongTinCaNhanHuyen.getName());

        rvBottomSheetChonDiaChiHanhChinh.setAdapter(new UserThongTinCaNhanBottomSheetChonDiaChiHanhChinhAdapter(names, name -> {
            for (UserThongTinCaNhanHuyen h : userThongTinCaNhanTinhDaChon.getDistricts()) {
                if (h.getName().equals(name)) {
                    userThongTinCaNhanHuyenDaChon = h;
                    tlBottomSheetChonDiaChiHanhChinh.getTabAt(1).setText(name);
                    tlBottomSheetChonDiaChiHanhChinh.getTabAt(2).setText("Phường/Xã");
                    hienThiXa();
                    break;
                }
            }
        }));
        tlBottomSheetChonDiaChiHanhChinh.getTabAt(1).select(); // Nhảy sang Tab Quận/Huyện
    }

    private void hienThiXa() {
        if (userThongTinCaNhanHuyenDaChon == null) return;

        List<String> names = new ArrayList<>();
        for (UserThongTinCaNhanXa userThongTinCaNhanXa : userThongTinCaNhanHuyenDaChon.getWards()) names.add(userThongTinCaNhanXa.getName());

        rvBottomSheetChonDiaChiHanhChinh.setAdapter(new UserThongTinCaNhanBottomSheetChonDiaChiHanhChinhAdapter(names, name -> {
            tlBottomSheetChonDiaChiHanhChinh.getTabAt(2).setText(name);
            if (listener != null) {
                listener.onResult(userThongTinCaNhanTinhDaChon.getName(), userThongTinCaNhanHuyenDaChon.getName(), name);
            }
            dismiss(); // Chọn xong Phường/Xã thì đóng Sheet
        }));
        tlBottomSheetChonDiaChiHanhChinh.getTabAt(2).select(); // Nhảy sang Tab Phường/Xã
    }
}