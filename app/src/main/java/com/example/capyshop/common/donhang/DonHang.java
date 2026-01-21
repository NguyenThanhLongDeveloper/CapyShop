package com.example.capyshop.common.donhang;

import com.example.capyshop.common.chitietdonhang.ChiTietDonHang;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class DonHang implements Serializable {
    int maDonHang;
    int maNguoiDung;
    int maPhuongThucThanhToan;
    int maDonViVanChuyen;
    String hoTenNguoiDung;
    String soDienThoai;
    String diaChi;
    int tongTien;
    int tongSoLuong;
    String trangThai;
    String thoiGianTao;
    String hinhAnhNguoiDung;
    String tenNguoiDung;
    String tenPhuongThucThanhToan;
    String tenDonViVanChuyen;
    int phiVanChuyen;

    // Handled with SerializedName to support both User and Admin JSON responses
    @SerializedName(value = "chitietdonhang", alternate = {"adminChitietdonhang"})
    List<ChiTietDonHang> chitietdonhang;

    public int getMaDonHang() { return maDonHang; }
    public void setMaDonHang(int maDonHang) { this.maDonHang = maDonHang; }
    public int getMaNguoiDung() { return maNguoiDung; }
    public void setMaNguoiDung(int maNguoiDung) { this.maNguoiDung = maNguoiDung; }
    public int getMaPhuongThucThanhToan() { return maPhuongThucThanhToan; }
    public void setMaPhuongThucThanhToan(int maPhuongThucThanhToan) { this.maPhuongThucThanhToan = maPhuongThucThanhToan; }
    public int getMaDonViVanChuyen() { return maDonViVanChuyen; }
    public void setMaDonViVanChuyen(int maDonViVanChuyen) { this.maDonViVanChuyen = maDonViVanChuyen; }
    public String getHoTenNguoiDung() { return hoTenNguoiDung; }
    public void setHoTenNguoiDung(String hoTenNguoiDung) { this.hoTenNguoiDung = hoTenNguoiDung; }
    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public int getTongTien() { return tongTien; }
    public void setTongTien(int tongTien) { this.tongTien = tongTien; }
    public int getTongSoLuong() { return tongSoLuong; }
    public void setTongSoLuong(int tongSoLuong) { this.tongSoLuong = tongSoLuong; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public String getThoiGianTao() { return thoiGianTao; }
    public void setThoiGianTao(String thoiGianTao) { this.thoiGianTao = thoiGianTao; }
    public String getHinhAnhNguoiDung() { return hinhAnhNguoiDung; }
    public void setHinhAnhNguoiDung(String hinhAnhNguoiDung) { this.hinhAnhNguoiDung = hinhAnhNguoiDung; }
    public String getTenNguoiDung() { return tenNguoiDung; }
    public void setTenNguoiDung(String tenNguoiDung) { this.tenNguoiDung = tenNguoiDung; }
    public String getTenPhuongThucThanhToan() { return tenPhuongThucThanhToan; }
    public void setTenPhuongThucThanhToan(String tenPhuongThucThanhToan) { this.tenPhuongThucThanhToan = tenPhuongThucThanhToan; }
    public String getTenDonViVanChuyen() { return tenDonViVanChuyen; }
    public void setTenDonViVanChuyen(String tenDonViVanChuyen) { this.tenDonViVanChuyen = tenDonViVanChuyen; }
    public int getPhiVanChuyen() { return phiVanChuyen; }
    public void setPhiVanChuyen(int phiVanChuyen) { this.phiVanChuyen = phiVanChuyen; }
    public List<ChiTietDonHang> getChitietdonhang() { return chitietdonhang; }
    public void setChitietdonhang(List<ChiTietDonHang> chitietdonhang) { this.chitietdonhang = chitietdonhang; }
}
