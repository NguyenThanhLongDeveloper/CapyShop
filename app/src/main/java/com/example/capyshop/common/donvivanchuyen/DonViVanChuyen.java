package com.example.capyshop.common.donvivanchuyen;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class DonViVanChuyen implements Serializable {
    @SerializedName("maDonViVanChuyen")
    private int maDonViVanChuyen;
    
    @SerializedName("tenDonViVanChuyen")
    private String tenDonViVanChuyen;
    
    @SerializedName("giaDonViVanChuyen")
    private long giaDonViVanChuyen;
    
    @SerializedName("trangThai")
    private String trangThai;
    
    @SerializedName("thoiGianTao")
    private String thoiGianTao;

    public DonViVanChuyen() {
    }

    public int getMaDonViVanChuyen() {
        return maDonViVanChuyen;
    }

    public void setMaDonViVanChuyen(int maDonViVanChuyen) {
        this.maDonViVanChuyen = maDonViVanChuyen;
    }

    public String getTenDonViVanChuyen() {
        return tenDonViVanChuyen;
    }

    public void setTenDonViVanChuyen(String tenDonViVanChuyen) {
        this.tenDonViVanChuyen = tenDonViVanChuyen;
    }

    public long getGiaDonViVanChuyen() {
        return giaDonViVanChuyen;
    }

    public void setGiaDonViVanChuyen(long giaDonViVanChuyen) {
        this.giaDonViVanChuyen = giaDonViVanChuyen;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(String thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }
}
