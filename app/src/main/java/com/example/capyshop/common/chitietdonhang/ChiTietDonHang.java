package com.example.capyshop.common.chitietdonhang;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class ChiTietDonHang implements Serializable {
    @SerializedName(value = "maChiTietDonHang", alternate = "ma_chi_tiet_don_hang")
    int maChiTietDonHang;

    int maDonHang;
    int maSanPham;

    @SerializedName(value = "tenSanPham", alternate = "ten_san_pham")
    String tenSanPham;

    @SerializedName(value = "hinhAnhSanPham", alternate = "hinh_anh_san_pham")
    String hinhAnhSanPham;

    @SerializedName(value = "giaSanPham", alternate = "gia_san_pham")
    int giaSanPham;

    @SerializedName(value = "soLuong", alternate = "so_luong")
    int soLuong;

    @SerializedName(value = "thuocTinh", alternate = "thuoctinh")
    List<DonHangThuocTinh> thuocTinh;

    public int getMaChiTietDonHang() { return maChiTietDonHang; }
    public void setMaChiTietDonHang(int maChiTietDonHang) { this.maChiTietDonHang = maChiTietDonHang; }
    public int getMaDonHang() { return maDonHang; }
    public void setMaDonHang(int maDonHang) { this.maDonHang = maDonHang; }
    public int getMaSanPham() { return maSanPham; }
    public void setMaSanPham(int maSanPham) { this.maSanPham = maSanPham; }
    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }
    public String getHinhAnhSanPham() { return hinhAnhSanPham; }
    public void setHinhAnhSanPham(String hinhAnhSanPham) { this.hinhAnhSanPham = hinhAnhSanPham; }
    public int getGiaSanPham() { return giaSanPham; }
    public void setGiaSanPham(int giaSanPham) { this.giaSanPham = giaSanPham; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public List<DonHangThuocTinh> getThuocTinh() { return thuocTinh; }
    public void setThuocTinh(List<DonHangThuocTinh> thuocTinh) { this.thuocTinh = thuocTinh; }
}
