package com.example.capyshop.common.chitietdonhang;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class DonHangThuocTinh implements Serializable {
    @SerializedName(value = "maThuocTinh", alternate = "ma_thuoc_tinh")
    int maThuocTinh;

    @SerializedName(value = "tenThuocTinh", alternate = "ten_thuoc_tinh")
    String tenThuocTinh;

    @SerializedName(value = "giaTri", alternate = "gia_tri")
    List<DonHangGiaTriThuocTinh> giaTri;

    public int getMaThuocTinh() { return maThuocTinh; }
    public void setMaThuocTinh(int maThuocTinh) { this.maThuocTinh = maThuocTinh; }
    public String getTenThuocTinh() { return tenThuocTinh; }
    public void setTenThuocTinh(String tenThuocTinh) { this.tenThuocTinh = tenThuocTinh; }
    public List<DonHangGiaTriThuocTinh> getGiaTri() { return giaTri; }
    public void setGiaTri(List<DonHangGiaTriThuocTinh> giaTri) { this.giaTri = giaTri; }
}
