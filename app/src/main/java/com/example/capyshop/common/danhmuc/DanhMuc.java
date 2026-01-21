package com.example.capyshop.common.danhmuc;

import java.io.Serializable;
import java.util.List;

public class DanhMuc implements Serializable {
    private int maDanhMuc;
    private String tenDanhMuc;
    private String hinhAnhDanhMuc;
    private List<String> danhSachThuongHieu;
    private List<String> danhSachThuocTinh;

    public DanhMuc() {
    }

    public int getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(int maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getHinhAnhDanhMuc() {
        return hinhAnhDanhMuc;
    }

    public void setHinhAnhDanhMuc(String hinhAnhDanhMuc) {
        this.hinhAnhDanhMuc = hinhAnhDanhMuc;
    }

    public List<String> getDanhSachThuongHieu() {
        return danhSachThuongHieu;
    }

    public void setDanhSachThuongHieu(List<String> danhSachThuongHieu) {
        this.danhSachThuongHieu = danhSachThuongHieu;
    }

    public List<String> getDanhSachThuocTinh() {
        return danhSachThuocTinh;
    }

    public void setDanhSachThuocTinh(List<String> danhSachThuocTinh) {
        this.danhSachThuocTinh = danhSachThuocTinh;
    }
}
