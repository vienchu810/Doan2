package com.example.quanlychthuoc.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.quanlychthuoc.Class.NhanVien;
import com.example.quanlychthuoc.R;

import java.util.ArrayList;

public class AdapterNhanVien extends ArrayAdapter<NhanVien> {
    ArrayList<NhanVien> arrnv = new ArrayList<NhanVien>();
    Activity context;
    int layout;

    public AdapterNhanVien(Context context, int resource, ArrayList<NhanVien> objects) {
        super(context, resource, objects);
        this.context = (Activity) context;
        arrnv = objects;
        layout = resource;
    }
    TextView ten,sdt,qq;
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view= inflater.inflate(layout , null);
        ten =(TextView) view.findViewById(R.id.txtTennv_it);
        sdt=(TextView) view.findViewById(R.id.txtsdt_it);
        qq=(TextView) view.findViewById(R.id.txtQuequan_it);

        NhanVien nv= arrnv.get(i);
        ten.setText(nv.tennv);
        sdt.setText(nv.sdt);
       qq.setText(nv.diachi);

       // ImageView hinhanh=(ImageView)view.findViewById(R.id.hinh);
//        byte[] anh=arrnv.get(i).anh;
//        Bitmap bitmap= BitmapFactory.decodeByteArray(anh,0,anh.length);
//        hinhanh.setImageBitmap(bitmap);
        return view;
    }
}
