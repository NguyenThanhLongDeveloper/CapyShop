package com.example.capyshop.admin.main;

import java.util.List;

public class AdminMainTongDoanhThuModel {
    boolean success;
    String message;
    List<AdminMainTongDoanhThu> result;


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

    public List<AdminMainTongDoanhThu> getResult() {
        return result;
    }

    public void setResult(List<AdminMainTongDoanhThu> result) {
        this.result = result;
    }
}
