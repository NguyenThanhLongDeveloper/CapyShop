package com.example.capyshop.common.thuonghieu;

import java.util.List;

public class ThuongHieuModel {
    private boolean success;
    private String message;
    private List<ThuongHieu> result;

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

    public List<ThuongHieu> getResult() {
        return result;
    }

    public void setResult(List<ThuongHieu> result) {
        this.result = result;
    }


}
