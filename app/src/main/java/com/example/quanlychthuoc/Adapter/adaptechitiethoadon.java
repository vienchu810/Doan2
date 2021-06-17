package com.example.quanlychthuoc.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.quanlychthuoc.R;
import com.example.quanlychthuoc.Class.SanPham;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class adaptechitiethoadon extends ArrayAdapter<SanPham> {



    ArrayList<SanPham> sanPhams = new ArrayList<SanPham>();
        Activity context;
        int layout;

        public adaptechitiethoadon(Context context, int resource, ArrayList<SanPham> objects) {
            super(context, resource, objects);
            this.context = (Activity) context;
            sanPhams = objects;
            layout = resource;
        }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater =(LayoutInflater) LayoutInflater.from( getContext() );
        view=inflater.inflate(R.layout.item_hd,null);
         DecimalFormat formatPrice = new DecimalFormat("###,###,###");
        ListView listView= (ListView) view.findViewById( R.id.lvcthd );
        TextView textView = (TextView)view.findViewById( R.id.tvmasp );
        textView.setText(sanPhams.get(i).Masp.trim());
        TextView textView1=(TextView)view.findViewById( R.id.txtsoluong );
        textView1.setText( sanPhams.get( i ).sluongthuoc +"");
        TextView textView2= (TextView)view.findViewById( R.id.txtgiatien );
       textView2.setText( sanPhams.get( i ).gt+" VNĐ");

        return view;
    }

}
