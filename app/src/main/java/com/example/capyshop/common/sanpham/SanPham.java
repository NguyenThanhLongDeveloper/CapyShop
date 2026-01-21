package com.example.capyshop.common.sanpham;

import com.example.capyshop.common.chitietsanpham.ChiTietSanPhamAlbum;
import com.example.capyshop.common.chitietsanpham.ChiTietSanPhamThuocTinh;

import java.io.Serializable;
import java.util.List;

public class SanPham implements Serializable {
    private int maSanPham;
    private String tenSanPham;
    private String hinhAnhSanPham;
    private long giaSanPham;
    private String moTaSanPham;
    private int soLuongTon;
    private int maDanhMuc;
    private int maThuongHieu;
    private String trangThai;
    private String tenThuongHieu;

    private List<ChiTietSanPhamAlbum> album;
    private List<ChiTietSanPhamThuocTinh> thuocTinh;

    public SanPham() {
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

    public long getGiaSanPham() {
        return giaSanPham;
    }

    public void setGiaSanPham(long giaSanPham) {
        this.giaSanPham = giaSanPham;
    }

    public String getMoTaSanPham() {
        return moTaSanPham;
    }

    public void setMoTaSanPham(String moTaSanPham) {
        this.moTaSanPham = moTaSanPham;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public int getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(int maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public int getMaThuongHieu() {
        return maThuongHieu;
    }

    public void setMaThuongHieu(int maThuongHieu) {
        this.maThuongHieu = maThuongHieu;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getTenThuongHieu() {
        return tenThuongHieu;
    }

    public void setTenThuongHieu(String tenThuongHieu) {
        this.tenThuongHieu = tenThuongHieu;
    }

    public List<ChiTietSanPhamAlbum> getAlbum() {
        return album;
    }

    public void setAlbum(List<ChiTietSanPhamAlbum> album) {
        this.album = album;
    }

    public List<ChiTietSanPhamThuocTinh> getThuocTinh() {
        return thuocTinh;
    }

    public void setThuocTinh(List<ChiTietSanPhamThuocTinh> thuocTinh) {
        this.thuocTinh = thuocTinh;
    }
}
