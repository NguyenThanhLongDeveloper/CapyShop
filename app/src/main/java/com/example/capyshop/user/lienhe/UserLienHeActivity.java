package com.example.capyshop.user.lienhe;

import android.os.Bundle;

import com.example.capyshop.R;
import com.example.capyshop.common.activity.BaseActivity;
import com.example.capyshop.common.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserLienHeActivity extends BaseActivity {
    BottomNavigationView bnLienHe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.user_lienhe_activity);
        super.onCreate(savedInstanceState);
        anhXa();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Xử lý sự kiện click trên các item trong menu
        Utils.xuLySuKienClickMenu(this, bnLienHe);
    }

    private void anhXa() {
        bnLienHe = findViewById(R.id.bn_menu_lien_he);
    }

}