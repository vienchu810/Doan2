package com.example.quanlychthuoc.Class;




public class GioHang extends SanPham {

    public String mahd;
    public String hoten;
    public String diachi;
    public String date;
    public int tongtien;

    public GioHang(String mahd, String hoten, String diachi, String date, int tongtien) {
        this.mahd = mahd;
        this.hoten = hoten;
        this.diachi = diachi;
        this.date = date;
        this.tongtien = tongtien;
    }

    public GioHang() {
    }

    public String getMahd() {
        return mahd;
    }

    public void setMahd(String mahd) {
        this.mahd = mahd;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
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


