package com.example.quanlychthuoc.Main.Sanpham;

import android.database.Cursor;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychthuoc.Class.SanPham;
import com.example.quanlychthuoc.DatabaseHandler;
import com.example.quanlychthuoc.Adapter.Adaptergiohang;
import com.example.quanlychthuoc.R;

import java.util.ArrayList;

public class ThongTinSanPham extends AppCompatActivity {
    TextView ma, ten, dd, sl, gt;
    String maSP;
    Button gh;

    ArrayList<SanPham> list;
    ArrayList<SanPham> SanPhams = new ArrayList<>();
    ArrayList<String> spItems = new ArrayList<String>();
    Adaptergiohang adapter;
   DatabaseHandler db = new DatabaseHandler( this );
    ListView lvgh;

    /////
    TextView tma, tten, tgia, tsl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_thongtinsanpham);
        lvgh = (ListView) findViewById( R.id.lv );
        ma = (TextView) findViewById( R.id.masp );
        ten = (TextView) findViewById( R.id.tensp );
        dd = (TextView) findViewById( R.id.dacdiem );
        sl = (TextView) findViewById( R.id.soluong );
        gt = (TextView) findViewById( R.id.giathanh );

        //////////////////////
        tma = (TextView) findViewById( R.id.txmasp );
        tten = (TextView) findViewById( R.id.txten );

        //back tren ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setDisplayShowHomeEnabled( true );
        }

        Bundle bundle = getIntent().getExtras();
        maSP = bundle.getString( "masp" );
        Cursor cursor = db.getCursor( "SELECT * FROM sanpham where masp ='" + maSP + "'" );

        cursor.moveToFirst();
        SanPham c = new SanPham();

        c.setMasp( cursor.getString( 0 ) );
        c.setTensp( cursor.getString( 1 ) );
        c.setDD( cursor.getString( 2 ) );
        c.setSl( cursor.getInt( 3 ) );
        c.setGt( cursor.getInt( 4 ) );

        ma.setText( "" + c.getMasp() );
        ten.setText( "" + c.getTensp() );
        dd.setText( "" + c.getDD() );
        sl.setText( "" + c.getSl() );
        gt.setText(  c.getGt() + "VND" );

    }

    ////////////////////////////////////////////////////////////////////////////////////////
    //back tren ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected( item );
    }

}





