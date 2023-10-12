package com.appnew.libmanager.Model;

public class Sach {
    private String ma, name, tacgia, maloaisach;

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public String getMaloaisach() {
        return maloaisach;
    }

    public void setMaloaisach(String maloaisach) {
        this.maloaisach = maloaisach;
    }

    public Sach() {
    }

    public Sach(String ma, String name, String tacgia, String maloaisach) {
        this.ma = ma;
        this.name = name;
        this.tacgia = tacgia;
        this.maloaisach = maloaisach;
    }
}
