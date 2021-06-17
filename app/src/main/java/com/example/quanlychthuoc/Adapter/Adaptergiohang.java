package com.example.quanlychthuoc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.quanlychthuoc.R;
import com.example.quanlychthuoc.Class.SanPham;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Calendar;

public class Adaptergiohang extends BaseAdapter {
    private Context context;
    private int layout;
    public ArrayList<SanPham> sanPhams;

    public Adaptergiohang(Context context, int layout, ArrayList<SanPham> sp) {
        this.context = context;
        this.layout = layout;
        this.sanPhams = sp;
    }

    public Adaptergiohang(Context context, ArrayList<SanPham> listsp) {
        this.context = context;
        this.sanPhams = listsp;
    }


    @Override
    public int getCount() {
        return sanPhams.size();
    }

    @Override
    public Object getItem(int i) {
        return sanPhams.get( i );
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public Calendar getList() {
        return null;
    }

    public class ViewHolder {
        TextView maC, tenC, G;
        Button tru, cong;
        EditText giatri;
    }

    @Override
    public View getView(final int i, View view, ViewGroup parent) {
        View row = view;
        DecimalFormat formatter = new DecimalFormat( "###,###,###" );
        ViewHolder viewHolder = new ViewHolder();
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            row = inflater.inflate( R.layout.item_giohang, null );
            viewHolder.maC = (TextView) row.findViewById( R.id.txmasp );
            viewHolder.tenC = (TextView) row.findViewById( R.id.txten );
            viewHolder.G = (TextView) row.findViewById( R.id.txgia );
            viewHolder.tru = (Button) row.findViewById( R.id.butongiam );
            viewHolder.giatri = (EditText) row.findViewById( R.id.edtgiatri );
            viewHolder.cong = (Button) row.findViewById( R.id.butontang );
            row.setTag( viewHolder );
        } else {
            viewHolder = (ViewHolder) row.getTag();
        }
        SanPham c = sanPhams.get( i );
        viewHolder.maC.setText( c.getMasp() );
        viewHolder.tenC.setText( c.getTensp() );
        viewHolder.G.setText( String.valueOf( formatter.format( c.getGt() ) + " VNĐ" ) );//dinh dang kieu du lieu
        viewHolder.giatri.setText( c.sluongthuoc + "" );
        final ViewHolder finalHolder = viewHolder;
        viewHolder.cong.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatesoluong( i, finalHolder.giatri, 1 ); //cap nhap vi tri
            }
        } );

        final ViewHolder finalHolder1 = viewHolder;
        viewHolder.tru.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatesoluong( i, finalHolder1.giatri, -1 );

            }
        } );
        return row;
    }

    private void updatesoluong(int i, EditText giatri, int value) {

        if (Integer.parseInt( giatri.getText().toString() ) == 0) {
            sanPhams.get( i ).sluongthuoc = 0;
        }
        if (value > 0) {
            sanPhams.get( i ).sluongthuoc = sanPhams.get( i ).sluongthuoc + 1;

        } else {
            if (sanPhams.get( i ).sluongthuoc > 0) {
                sanPhams.get( i ).sluongthuoc = sanPhams.get( i ).sluongthuoc - 1;

            }

        }
        giatri.setText( sanPhams.get( i ).sluongthuoc + "" );



    }
}


