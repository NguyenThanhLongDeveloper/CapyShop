package com.example.capyshop.admin.tinnhan;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capyshop.R;
import com.example.capyshop.common.activity.BaseActivity;
import com.example.capyshop.common.nguoidung.NguoiDung;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminTinNhanDanhSachActivity extends BaseActivity {
    Toolbar tbAdminTinNhan;
    TextView tvAdminSoLuongTinNhan;
    RecyclerView rvAdminTinNhan;

    AdminTinNhanDanhSachAdapter adminTinNhanDanhSachAdapter;
    List<NguoiDung> mangTinNhan;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.admin_tin_nhan_danh_sach_activity);
        super.onCreate(savedInstanceState);
        anhXa();
        caiDatToolbar();
        layNguoiDungGuiTinNhan();
    }

    private void anhXa() {
        tbAdminTinNhan = findViewById(R.id.tb_admin_tin_nhan);
        tvAdminSoLuongTinNhan = findViewById(R.id.tv_admin_tin_nhan_so_luong);
        rvAdminTinNhan = findViewById(R.id.rv_admin_tin_nhan);
        // Thiết lập layout dạng danh sách
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvAdminTinNhan.setLayoutManager(layoutManager);
        rvAdminTinNhan.setHasFixedSize(true);
        // Khởi tạo Firebase Firestore
        db = FirebaseFirestore.getInstance();
        //
        mangTinNhan = new ArrayList<>();

    }

    private void caiDatToolbar() {
        setSupportActionBar(tbAdminTinNhan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbAdminTinNhan.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void layNguoiDungGuiTinNhan() {
        db.collection("nguoidungguitinnhan")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NguoiDung nguoiDung = new NguoiDung();
                                nguoiDung.setMaNguoiDung(Integer.parseInt(document.getString("manguoidungguitinnhan")));
                                nguoiDung.setHoTenNguoiDung(document.getString("tennguoidungguitinnhan"));
                                mangTinNhan.add(nguoiDung);
                                //
                                adminTinNhanDanhSachAdapter = new AdminTinNhanDanhSachAdapter(getApplicationContext(), mangTinNhan);
                            }

                            if (mangTinNhan.size() > 0) {
                                rvAdminTinNhan.setAdapter(adminTinNhanDanhSachAdapter);

                            }
                        }
                    }
                });

    }
}