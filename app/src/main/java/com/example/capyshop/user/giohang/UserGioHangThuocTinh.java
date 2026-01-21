package com.example.capyshop.user.giohang;

import java.util.List;

public class UserGioHangThuocTinh {
    int maThuocTinh;
    String tenThuocTinh;
    List<UserGioHangGiaTriThuocTinh> giaTri;

    public int getMaThuocTinh() {
        return maThuocTinh;
    }

    public String getTenThuocTinh() {
        return tenThuocTinh;
    }

    public List<UserGioHangGiaTriThuocTinh> getGiaTri() {
        return giaTri;
    }
}
