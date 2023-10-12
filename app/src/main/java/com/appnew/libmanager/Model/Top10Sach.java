package com.appnew.libmanager.Model;

public class Top10Sach {
    private String tenSach, maSach, loaiSach;
    private int soLuong;

    public Top10Sach(String tenSach, String maSach, String loaiSach, int soLuong) {
        this.tenSach = tenSach;
        this.maSach = maSach;
        this.loaiSach = loaiSach;
        this.soLuong = soLuong;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getLoaiSach() {
        return loaiSach;
    }

    public void setLoaiSach(String loaiSach) {
        this.loaiSach = loaiSach;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
