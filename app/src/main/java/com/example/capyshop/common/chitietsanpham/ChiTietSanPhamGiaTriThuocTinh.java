package com.example.capyshop.common.chitietsanpham;

import java.io.Serializable;

public class ChiTietSanPhamGiaTriThuocTinh implements Serializable {
    private int maSanPhamThuocTinh;
    private String giaTri;
    private boolean isSelected = false;

    public ChiTietSanPhamGiaTriThuocTinh() {
    }

    public int getMaSanPhamThuocTinh() {
        return maSanPhamThuocTinh;
    }

    public void setMaSanPhamThuocTinh(int maSanPhamThuocTinh) {
        this.maSanPhamThuocTinh = maSanPhamThuocTinh;
    }

    public String getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(String giaTri) {
        this.giaTri = giaTri;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
