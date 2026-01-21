package com.example.capyshop.common.chitietdonhang;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class DonHangGiaTriThuocTinh implements Serializable {
    @SerializedName(value = "value", alternate = "gia_tri")
    String value;

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}
