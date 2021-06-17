package com.example.quanlychthuoc.Class;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;

public class SanPham implements Serializable, Parcelable {

    public int gt;
    public int sl;
    public String Masp;
    public String Tensp;
    public String DD;
    public int sluongthuoc;


    protected SanPham(Parcel in) {
        gt = in.readInt();
        sl = in.readInt();
        Masp = in.readString();
        Tensp = in.readString();
        DD = in.readString();
        sluongthuoc = in.readInt();
    }

    public static final Creator<SanPham> CREATOR = new Creator<SanPham>() {
        @Override
        public SanPham createFromParcel(Parcel in) {
            return new SanPham( in );
        }

        @Override
        public SanPham[] newArray(int size) {
            return new SanPham[size];
        }
    };

    public SanPham(String masp, int sl, int gia) {

    }

    public int getGt() {
        return gt;
    }

    public void setGt(int gt) {
        this.gt = gt;
    }

    public String getMasp() {
        return Masp;
    }

    public void setMasp(String masp) {
        Masp = masp;
    }

    public String getTensp() {
        return Tensp;
    }

    public void setTensp(String tensp) {
        Tensp = tensp;
    }

    public String getDD() {
        return DD;
    }

    public void setDD(String DD) {
        this.DD = DD;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public int getSluongcay() {
        return sluongthuoc;
    }

    public void setSluongcay(int sluongcay) {
        this.sluongthuoc = sluongcay;
    }

    public SanPham(String masp, String tenSP, int Giaban, int soluong) {
        Masp = masp;
        Tensp = tenSP;
        gt = Giaban;
        sluongthuoc = soluong;

    }
    DecimalFormat formatter = new DecimalFormat( "###,###,###" );
    @Override
    public String toString() {
        return "Mã cây: " + Masp + "\nTên cây: " + Tensp + "\nGía bán: " + formatter.format(gt) + "\nSố lượng:" + sluongthuoc;
    }

    public String getJsonObject() {

        JSONObject giohang = new JSONObject();//trao đổi dữ liệu
        try {
            giohang.put( "Masp", Masp );
            giohang.put( "Tensp", Tensp );
            giohang.put( "GiaBan", gt );
            giohang.put( "Giatri", sluongthuoc );
        } catch (Exception e) {
        }
        return giohang.toString();
    }


    public SanPham(String masp, String tensp, String DD, Integer sl, Integer gt) {
        this.Masp = masp;
        this.Tensp = tensp;
        this.DD = DD;
        this.sl = sl;
        this.gt = gt;

    }

    public SanPham() {
    }

    public void setActive(boolean b) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt( gt );
        parcel.writeInt( sl );
        parcel.writeString( Masp );
        parcel.writeString( Tensp );
        parcel.writeString( DD );
        parcel.writeInt( sluongthuoc );
    }
}
