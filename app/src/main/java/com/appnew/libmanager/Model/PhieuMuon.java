package com.appnew.libmanager.Model;

public class PhieuMuon {
    private String ngayMuon;
    private String nguoiMuon, tenSach, maSach, maPhieuMuon;

    public String getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(String ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public String getNguoiMuon() {
        return nguoiMuon;
    }

    public void setNguoiMuon(String nguoiMuon) {
        this.nguoiMuon = nguoiMuon;
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

    public PhieuMuon() {
    }

    public PhieuMuon(String maPhieuMuon, String ngayMuon, String nguoiMuon, String tenSach, String maSach) {
        this.maPhieuMuon = maPhieuMuon;
        this.ngayMuon = ngayMuon;
        this.nguoiMuon = nguoiMuon;
        this.tenSach = tenSach;
        this.maSach = maSach;
    }

    public String getMaPhieuMuon() {
        return maPhieuMuon;
    }

    public void setMaPhieuMuon(String maPhieuMuon) {
        this.maPhieuMuon = maPhieuMuon;
    }
}
