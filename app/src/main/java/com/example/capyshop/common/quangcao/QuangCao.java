package com.example.capyshop.common.quangcao;

import java.io.Serializable;

public class QuangCao implements Serializable {
    private int maQuangCao;
    private String hinhAnhQuangCao;

    public QuangCao() {
    }

    public QuangCao(int maQuangCao, String hinhAnhQuangCao) {
        this.maQuangCao = maQuangCao;
        this.hinhAnhQuangCao = hinhAnhQuangCao;
    }

    public int getMaQuangCao() {
        return maQuangCao;
    }

    public void setMaQuangCao(int maQuangCao) {
        this.maQuangCao = maQuangCao;
    }

    public String getHinhAnhQuangCao() {
        return hinhAnhQuangCao;
    }

    public void setHinhAnhQuangCao(String hinhAnhQuangCao) {
        this.hinhAnhQuangCao = hinhAnhQuangCao;
    }
}
