package com.appnew.libmanager.Model;

public class User {
    private String name, userName, pass;
    private int ma, admin, namsinh, sdt;

    public User(int ma, String name, int namsinh, int sdt, int admin) {
        this.name = name;
        this.ma = ma;
        this.admin = admin;
        this.namsinh = namsinh;
        this.sdt = sdt;
    }

    public User(int ma, String name, int sdt, int namsinh, int admin, String userName, String pass){
        this.name = name;
        this.ma = ma;
        this.admin = admin;
        this.namsinh = namsinh;
        this.sdt = sdt;
        this.userName = userName;
        this.pass = pass;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
