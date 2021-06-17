package com.example.quanlychthuoc.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.quanlychthuoc.Class.SanPham;
import com.example.quanlychthuoc.R;

import java.util.ArrayList;

public class SanPhamAdapter extends ArrayAdapter<SanPham> {

    ArrayList<SanPham> arrayList = new ArrayList<SanPham>();
    Activity context;
    int layout;

    public SanPhamAdapter(Context context, int resource, ArrayList<SanPham> objects) {
        super(context, resource, objects);
        this.context = (Activity) context;
        arrayList = objects;
        layout = resource;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup parent) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= inflater.inflate(layout , null);
        ListView listView =(ListView)view.findViewById(R.id.listviewmanhinhchinh);
        TextView tma =(TextView)view.findViewById(R.id.txmasp);
        TextView tten=(TextView)view.findViewById(R.id.txtensp);
        TextView gc=(TextView)view.findViewById(R.id.txt_giacay);
        SanPham tt= arrayList.get(i);
        tma.setText(tt.Masp);
        tten.setText(tt.Tensp);
        gc.setText( tt.gt+" VNĐ");

        return view;
    }

}
