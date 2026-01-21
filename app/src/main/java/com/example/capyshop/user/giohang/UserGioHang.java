package com.example.capyshop.user.giohang;

import java.util.List;

public class UserGioHang {

    int maGioHang;
    int maSanPham;
    String tenSanPham;
    String hinhAnhSanPham;
    int giaSanPham;
    int soLuong;

    // 🔥 MỚI – danh sách thuộc tính
    List<UserGioHangThuocTinh> thuocTinh;

    boolean trangThaiCheckBox = false;

    public int getMaGioHang() {
        return maGioHang;
    }

    public void setMaGioHang(int maGioHang) {
        this.maGioHang = maGioHang;
    }

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getHinhAnhSanPham() {
        return hinhAnhSanPham;
    }

    public void setHinhAnhSanPham(String hinhAnhSanPham) {
        this.hinhAnhSanPham = hinhAnhSanPham;
    }

    public int getGiaSanPham() {
        return giaSanPham;
    }

    public void setGiaSanPham(int giaSanPham) {
        this.giaSanPham = giaSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public List<UserGioHangThuocTinh> getThuocTinh() {
        return thuocTinh;
    }

    public void setThuocTinh(List<UserGioHangThuocTinh> thuocTinh) {
        this.thuocTinh = thuocTinh;
    }

    public boolean isTrangThaiCheckBox() {
        return trangThaiCheckBox;
    }

    public void setTrangThaiCheckBox(boolean trangThaiCheckBox) {
        this.trangThaiCheckBox = trangThaiCheckBox;
    }
}
