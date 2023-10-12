package com.appnew.libmanager.Model;

public class LoaiSach {
    private String maLoaiSach, tenLoaiSach;

    public LoaiSach() {
    }

    public String getMaLoaiSach() {
        return maLoaiSach;
    }

    public void setMaLoaiSach(String maLoaiSach) {
        this.maLoaiSach = maLoaiSach;
    }

    public String getTenLoaiSach() {
        return tenLoaiSach;
    }

    public void setTenLoaiSach(String tenLoaiSach) {
        this.tenLoaiSach = tenLoaiSach;
    }

    public LoaiSach(String maLoaiSach, String tenLoaiSach) {
        this.maLoaiSach = maLoaiSach;
        this.tenLoaiSach = tenLoaiSach;
    }
}
