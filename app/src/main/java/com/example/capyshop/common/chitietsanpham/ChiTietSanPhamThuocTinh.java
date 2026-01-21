package com.example.capyshop.common.chitietsanpham;

import java.io.Serializable;
import java.util.List;

public class ChiTietSanPhamThuocTinh implements Serializable {
    private String tenThuocTinh;
    private List<ChiTietSanPhamGiaTriThuocTinh> giaTri;

    public ChiTietSanPhamThuocTinh() {
    }

    public String getTenThuocTinh() {
        return tenThuocTinh;
    }

    public void setTenThuocTinh(String tenThuocTinh) {
        this.tenThuocTinh = tenThuocTinh;
    }

    public List<ChiTietSanPhamGiaTriThuocTinh> getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(List<ChiTietSanPhamGiaTriThuocTinh> giaTri) {
        this.giaTri = giaTri;
    }
}
