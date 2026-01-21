package com.example.capyshop.common.phuongthucthanhtoan;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class PhuongThucThanhToan implements Serializable {
    @SerializedName("maPhuongThucThanhToan")
    private int maPhuongThucThanhToan;
    
    @SerializedName("tenPhuongThucThanhToan")
    private String tenPhuongThucThanhToan;
    
    @SerializedName("trangThai")
    private String trangThai;

    public PhuongThucThanhToan() {
    }

    public int getMaPhuongThucThanhToan() {
        return maPhuongThucThanhToan;
    }

    public void setMaPhuongThucThanhToan(int maPhuongThucThanhToan) {
        this.maPhuongThucThanhToan = maPhuongThucThanhToan;
    }

    public String getTenPhuongThucThanhToan() {
        return tenPhuongThucThanhToan;
    }

    public void setTenPhuongThucThanhToan(String tenPhuongThucThanhToan) {
        this.tenPhuongThucThanhToan = tenPhuongThucThanhToan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
