package com.example.capyshop.user.giohang;

import java.util.List;

public class UserGioHangModel {
    // Biến lưu trạng thái thành công/thất bại của yêu cầu API
    boolean success;
    // Thông báo từ server
    String message;
    // Danh sách các đối tượng DonHang được trả về
    List<UserGioHang> result;

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

    public List<UserGioHang> getResult() {
        return result;
    }

    public void setResult(List<UserGioHang> result) {
        this.result = result;
    }
}
