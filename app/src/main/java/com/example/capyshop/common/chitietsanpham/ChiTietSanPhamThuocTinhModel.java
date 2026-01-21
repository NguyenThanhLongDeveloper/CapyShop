package com.example.capyshop.common.chitietsanpham;

import java.util.List;

public class ChiTietSanPhamThuocTinhModel {
    boolean success;
    String message;
    List<ChiTietSanPhamThuocTinh> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ChiTietSanPhamThuocTinh> getResult() {
        return result;
    }

    public void setResult(List<ChiTietSanPhamThuocTinh> result) {
        this.result = result;
    }
}
