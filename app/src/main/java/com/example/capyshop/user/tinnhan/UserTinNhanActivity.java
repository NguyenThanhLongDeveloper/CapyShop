package com.example.capyshop.user.tinnhan;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capyshop.R;
import com.example.capyshop.common.activity.BaseActivity;
import com.example.capyshop.common.tinnhan.TinNhan;
import com.example.capyshop.common.tinnhan.TinNhanAdapter;
import com.example.capyshop.common.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class UserTinNhanActivity extends BaseActivity {
    Toolbar tbTinNhan;
    RecyclerView rvTinNhan;
    EditText edtNhapTinNhan;
    FloatingActionButton fabGuiTinNhan;
    BottomNavigationView bnTinNhan;
    FirebaseFirestore db;

    //
    TinNhanAdapter tinNhanAdapter;
    List<TinNhan> mangTinNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.user_tin_nhan_activity);
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        anhXa();
        caiDatToolbar();
        xuLySuKienClickGuiTinNhan();
        hienThiTinNhan();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Xử lý sự kiện click trên các item trong menu
        Utils.xuLySuKienClickMenu(this, bnTinNhan);
    }

    private void anhXa() {
        tbTinNhan = findViewById(R.id.tb_tin_nhan);
        rvTinNhan = findViewById(R.id.rv_noi_dung_tin_nhan);
        edtNhapTinNhan = findViewById(R.id.edt_nhap_tin_nhan);
        fabGuiTinNhan = findViewById(R.id.fab_gui_tin_nhan);
        bnTinNhan = findViewById(R.id.bn_menu_tin_nhan);
        // Thiết lập layout dạng danh sách
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvTinNhan.setLayoutManager(layoutManager);
        rvTinNhan.setHasFixedSize(true);
        // Khởi tạo Firebase Firestore
        db = FirebaseFirestore.getInstance();
        //
        mangTinNhan = new ArrayList<>();
        tinNhanAdapter = new TinNhanAdapter(this, mangTinNhan, String.valueOf(Utils.userNguoiDung_Current.getMaNguoiDung()));
        rvTinNhan.setAdapter(tinNhanAdapter);
    }

    private void caiDatToolbar() {
        setSupportActionBar(tbTinNhan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(null);

    }

    private void xuLySuKienClickGuiTinNhan() {
        fabGuiTinNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noiDungTinNhan = edtNhapTinNhan.getText().toString().trim();
                // Kiểm tra xem tin nhắn có rỗng hay không
                if (TextUtils.isEmpty(noiDungTinNhan)) {
                    edtNhapTinNhan.setError("Vui lòng nhập tin nhắn");
                } else {
                    // Gửi tin nhắn đến firebase
                    HashMap<String, Object> tinnhan = new HashMap<>();
                    tinnhan.put(Utils.maNguoiDungGuiTinNhan, String.valueOf(Utils.userNguoiDung_Current.getMaNguoiDung()));
                    tinnhan.put(Utils.maNguoiDungNhanTinNhan, Utils.maNguoiNhanTinNhan);
                    tinnhan.put(Utils.noiDungTinNhan, noiDungTinNhan);
                    tinnhan.put(Utils.ngayGuiTinNhan, new Date());

                    db.collection(Utils.collection).add(tinnhan);
                    edtNhapTinNhan.setText("");

                    // thêm collection thông tin người gửi tin nhắn
                    HashMap<String, Object> nguoiDungGuiTinNhan = new HashMap<>();
                    nguoiDungGuiTinNhan.put("manguoidungguitinnhan", String.valueOf(Utils.userNguoiDung_Current.getMaNguoiDung()));
                    nguoiDungGuiTinNhan.put("tennguoidungguitinnhan", Utils.userNguoiDung_Current.getHoTenNguoiDung());
                    db.collection("nguoidungguitinnhan").document(String.valueOf(Utils.userNguoiDung_Current.getMaNguoiDung())).set(nguoiDungGuiTinNhan);



                }
            }
        });
    }

    private void hienThiTinNhan() {

        db.collection(Utils.collection)
                .whereEqualTo(Utils.maNguoiDungGuiTinNhan, String.valueOf(Utils.userNguoiDung_Current.getMaNguoiDung()))
                .whereEqualTo(Utils.maNguoiDungNhanTinNhan, String.valueOf(Utils.maNguoiNhanTinNhan))
                .addSnapshotListener(eventListener);

        db.collection(Utils.collection)
                .whereEqualTo(Utils.maNguoiDungGuiTinNhan, String.valueOf(Utils.maNguoiNhanTinNhan))
                .whereEqualTo(Utils.maNguoiDungNhanTinNhan, String.valueOf(Utils.userNguoiDung_Current.getMaNguoiDung()))
                .addSnapshotListener(eventListener);
    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {

            return;
        }
        if (value != null) {
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    TinNhan tinNhan = new TinNhan();
                    tinNhan.maNguoiDungGuiTinNhan = documentChange.getDocument().getString(Utils.maNguoiDungGuiTinNhan);
                    tinNhan.maNguoiDungNhanTinNhan = documentChange.getDocument().getString(Utils.maNguoiDungNhanTinNhan);
                    tinNhan.noiDungTinNhan = documentChange.getDocument().getString(Utils.noiDungTinNhan);
                    tinNhan.date = documentChange.getDocument().getDate(Utils.ngayGuiTinNhan);
                    tinNhan.ngayGuiTinNhan = dinhDangThoiGian(documentChange.getDocument().getDate(Utils.ngayGuiTinNhan));
                    mangTinNhan.add(tinNhan);

                    // sort lại tin nhắn theo thứ tự
                    Collections.sort(mangTinNhan, (o1, o2) -> o1.date.compareTo(o2.date));

                    tinNhanAdapter.notifyItemInserted(mangTinNhan.size() - 1);
                    rvTinNhan.smoothScrollToPosition(mangTinNhan.size() - 1);
                }
            }

        }
    };

    private String dinhDangThoiGian(Date date) {
        return new SimpleDateFormat("MMM dd, yyyy- hh:mm a", Locale.getDefault()).format(date);
    }
}