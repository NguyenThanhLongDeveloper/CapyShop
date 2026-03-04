package com.example.capyshop.admin.tinnhan;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capyshop.R;
import com.example.capyshop.common.activity.BaseActivity;
import com.example.capyshop.common.utils.Utils;
import com.example.capyshop.common.tinnhan.TinNhan;
import com.example.capyshop.common.tinnhan.TinNhanAdapter;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminTinNhanActivity extends BaseActivity {
    Toolbar tbTinNhan;
    CircleImageView civHinhAnhNguoiDungTinNhan;
    TextView tvHoTenNguoiDungTinNhan;
    RecyclerView rvTinNhan;
    EditText edtNhapTinNhan;
    FloatingActionButton fabGuiTinNhan;
    FirebaseFirestore db;

    //
    TinNhanAdapter tinNhanAdapter;
    List<TinNhan> mangTinNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.admin_tin_nhan_activity);
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        anhXa();
        caiDatToolbar();
        xuLySuKienClickGuiTinNhan();
        hienThiTinNhan();
    }

    private void anhXa() {
        tbTinNhan = findViewById(R.id.tb_admin_tin_nhan);
        civHinhAnhNguoiDungTinNhan = findViewById(R.id.civ_admin_hinh_anh_nguoi_dung_tin_nhan);
        tvHoTenNguoiDungTinNhan = findViewById(R.id.tv_admin_ho_ten_nguoi_dung_tin_nhan);
        rvTinNhan = findViewById(R.id.rv_admin_noi_dung_tin_nhan);
        edtNhapTinNhan = findViewById(R.id.edt_admin_nhap_tin_nhan);
        fabGuiTinNhan = findViewById(R.id.fab_admin_gui_tin_nhan);
        // Thiết lập layout dạng danh sách
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvTinNhan.setLayoutManager(layoutManager);
        rvTinNhan.setHasFixedSize(true);
        // Khởi tạo Firebase Firestore
        db = FirebaseFirestore.getInstance();
        //
        mangTinNhan = new ArrayList<>();
        tinNhanAdapter = new TinNhanAdapter(getApplicationContext(), mangTinNhan, String.valueOf(Utils.userNguoiDung_Current.getMaNguoiDung()));
        rvTinNhan.setAdapter(tinNhanAdapter);
    }

    private void caiDatToolbar() {
        setSupportActionBar(tbTinNhan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        tbTinNhan.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.maNguoiNhanTinNhan = null;
                finish();
            }
        });


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