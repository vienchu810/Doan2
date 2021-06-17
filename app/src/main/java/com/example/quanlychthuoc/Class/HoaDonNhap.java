package com.example.quanlychthuoc.Class;

public class HoaDonNhap {
    public String mahd;
    public String nguoinhap;
    public String NCC;
    public String date;
    public int tongtien;

    public HoaDonNhap(String mahd, String nguoinhap, String NCC, String date, int tongtien) {
        this.mahd = mahd;
        this.nguoinhap = nguoinhap;
        this.NCC = NCC;
        this.date = date;
        this.tongtien = tongtien;
    }

    public HoaDonNhap() {

    }

    public String getMahd() {
        return mahd;
    }

    public void setMahd(String mahd) {
        this.mahd = mahd;
    }

    public String getNguoinhap() {
        return nguoinhap;
    }

    public void setNguoinhap(String nguoinhap) {
        this.nguoinhap = nguoinhap;
    }

    public String getNCC() {
        return NCC;
    }

    public void setNCC(String NCC) {
        this.NCC = NCC;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }

    @Override
    public String toString() {
        return "Mã hd: " + mahd + "\nThành tiền: " + tongtien + "\nNgày bán:" + date;
    }
}
