package com.example.quanlychthuoc.Class;



import java.io.Serializable;


public class NhanVien  implements Serializable {
    public String manv;
    public String tennv;
    public String sdt;
    public String socmnd;
    public String diachi;

    public NhanVien() {

    }


    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getSocmnd() {
        return socmnd;
    }

    public void setSocmnd(String socmnd) {
        this.socmnd = socmnd;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public NhanVien(String manv, String tennv, String sdt, String socmnd, String diachi) {
        this.manv = manv;
        this.tennv = tennv;
        this.sdt = sdt;
        this.socmnd = socmnd;
        this.diachi = diachi;
    }

    public String toString1() {
        return  "Mã nhân viên: "+manv+"\nTên nhân viên: "+tennv+"\nSDT: "+sdt+"\nCMND : "+socmnd+"\nDia chi: "+diachi+"";
    }


    @Override
    public String toString() {
        return "Mã nhân viên: " + manv + "\nTên nhân viên: " + tennv + "\nĐịa chỉ: " + diachi + "";
    }
}
