package com.example.capyshop.common.chitietsanpham;

import java.io.Serializable;

public class ChiTietSanPhamAlbum implements Serializable {
    private String hinhAnh;

    public ChiTietSanPhamAlbum(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
