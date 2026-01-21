package com.example.capyshop.common.donvivanchuyen;

import java.util.List;

public class DonViVanChuyenModel {
    boolean success;
    String message;
    List<DonViVanChuyen> result;

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

    public List<DonViVanChuyen> getResult() {
        return result;
    }

    public void setResult(List<DonViVanChuyen> result) {
        this.result = result;
    }
}
