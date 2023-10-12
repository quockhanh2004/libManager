package com.appnew.libmanager.Model;

public class ThanhVien {
    private String ma, name;
    private int namsinh, sdt;

    public ThanhVien() {
    }

    public ThanhVien(String ma, String name, int namsinh, int sdt) {
        this.ma = ma;
        this.name = name;
        this.namsinh = namsinh;
        this.sdt = sdt;
    }

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

    public int getNamsinh() {
        return namsinh;
    }

    public void setNamsinh(int namsinh) {
        this.namsinh = namsinh;
    }

    public int getSdt() {
        return sdt;
    }

    public void setSdt(int sdt) {
        this.sdt = sdt;
    }
}
