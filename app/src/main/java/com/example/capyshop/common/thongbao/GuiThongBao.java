package com.example.capyshop.common.thongbao;

public class GuiThongBao {
    Message message;

    public GuiThongBao(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
