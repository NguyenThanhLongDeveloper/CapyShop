package com.example.capyshop.common.thuonghieu;

import java.io.Serializable;

public class ThuongHieu implements Serializable {
    private int maThuongHieu;
    private String tenThuongHieu;

    public int getMaThuongHieu() {
        return maThuongHieu;
    }

    public void setMaThuongHieu(int maThuongHieu) {
        this.maThuongHieu = maThuongHieu;
    }

    public String getTenThuongHieu() {
        return tenThuongHieu;
    }

    public void setTenThuongHieu(String tenThuongHieu) {
        this.tenThuongHieu = tenThuongHieu;
    }

    @Override
    public String toString() {
        return tenThuongHieu;
    }
}
