package com.example.quanlychthuoc.Main.hoadonnhap;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychthuoc.Class.HoaDonNhap;
import com.example.quanlychthuoc.DatabaseHandler;
import com.example.quanlychthuoc.Main.HoadonBan.Thongtinhoadon;
import com.example.quanlychthuoc.Main.Sanpham.Trangchu;
import com.example.quanlychthuoc.R;
import com.example.quanlychthuoc.Class.GioHang;

import java.util.ArrayList;

public class Danhsachhoadonnhap extends AppCompatActivity {

 DatabaseHandler db = new DatabaseHandler( this );
    ArrayList<HoaDonNhap> giohang = null;
    ArrayAdapter<HoaDonNhap> adapter = null;
    GioHang giohangt = new GioHang();
    ListView lv;
    Button bt;
    int ps;
    EditText ma, tenkh, diachi, ngay;
    TextView togtien;
    ImageButton gh,tr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_danhsachhoadonnhap );
        lv = (ListView) findViewById( R.id.lvdanhsachhoadonnhap );
        ma = (EditText) findViewById( R.id.mahd );
        tenkh = (EditText) findViewById( R.id.tvTenKH );
        diachi = (EditText) findViewById( R.id.tvDiaChi );
        ngay = (EditText) findViewById( R.id.tvngay );
        togtien = (TextView) findViewById( R.id.tvtien );
        gh = (ImageButton) findViewById( R.id.taohoadonnhap );
        tr=(ImageButton)findViewById( R.id.trangchu );

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setDisplayShowHomeEnabled( true );
        }
        ///////////////////////////////

//loadDatagiohang( "Select*from hoadonban" );
        db.copyDB2SDCard();
        int sbg = db.GetCount( "SELECT * FROM hoadonnhap" );
        //  Toast.makeText( this, "số bản ghi:" + sbg, Toast.LENGTH_SHORT ).show();

        tr.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent( Danhsachhoadonnhap.this, Trangchu.class );
                startActivity( in );
            }
        } );
        lv.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                Intent intent = new Intent( Danhsachhoadonnhap.this, Thongtinhoadon.class );
                intent.putExtra( "Mahd", giohang.get( i ).getMahd() );
                startActivity( intent );

            }
        } );


        db2ListView();
        gh.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Danhsachhoadonnhap.this,Themhoadon.class );
                startActivity( intent );
                finish();
            }
        } );
        //loadDatagiohang( "select*from hoadonban" );
        lv.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                ps = position;
                return false;
            }
        } );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected( item );
    }

    public GioHang Getmac(String Mahd) {

        Cursor cursor = db.getCursor( "SELECT * FROM hoadonnhap WHERE Mahd = '" + Mahd + "'" );
        cursor.moveToFirst();
        giohangt = new GioHang( cursor.getString( 0 ), cursor.getString( 1 ), cursor.getString( 2 ),
                cursor.getString( 3 ), cursor.getInt( 4 ) );
        return giohangt;
    }

    public void loadDatagiohang(String sql) {
        giohang = new ArrayList<HoaDonNhap>();

        adapter = new ArrayAdapter<HoaDonNhap>(this,R.layout.item_hd, giohang);
        Log.d( "data", "loadData: " + sql );
        Cursor cursor = db.getCursor( sql );
        while (cursor != null && cursor.moveToNext()) {
            HoaDonNhap c = new HoaDonNhap(  );
            c.setMahd( cursor.getString( 0 ) );
            c.setNguoinhap( cursor.getString( 1 ) );
            c.setNCC(cursor.getString( 2 ) );
            c.setDate( cursor.getString( 3 ) );
            c.setTongtien( cursor.getInt( 4 ) );

            giohang.add( c );
        }

        lv.setAdapter( adapter );
    }

    //Phương thức: Load dữ liệu lên ListView
    public void db2ListView() {
        giohang = new ArrayList<HoaDonNhap>();
        HoaDonNhap row;
        Cursor cursor = db.getCursor( "SELECT * FROM hoadonnhap" );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            row = new HoaDonNhap();
            row.mahd = cursor.getString( 0 );
            row.nguoinhap = cursor.getString( 1 );
            row.NCC= cursor.getString( 2 );
            row.date = cursor.getString( 3 );
            row.tongtien = cursor.getInt( 4 );

            giohang.add( row );
            cursor.moveToNext();
        }

        adapter = new ArrayAdapter<HoaDonNhap>( this, android.R.layout.simple_list_item_1, giohang );

        lv.setAdapter( adapter );
        adapter.notifyDataSetChanged();
        cursor.close();
        registerForContextMenu( lv );
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu( menu, v, menuInfo );

        menu.add( Menu.NONE, 1, menu.NONE, "Xóa" );


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Intent intent = null;
        Bundle bun = new Bundle();
        String masp = adapter.getItem( ps ).getMahd();
        if (item.getItemId() == 1) {
            Xoa( masp );
        }
        bun.putString( "Masp", masp );//
        Intent getData = getIntent();
        ps = getData.getIntExtra( "id", -1 );


        return super.onContextItemSelected( item );

    }


    public void Xoa(final String Mac) {
        AlertDialog.Builder builder = new AlertDialog.Builder( Danhsachhoadonnhap.this );
        builder.setTitle( "Xóa !" );
        builder.setNegativeButton( "Xóa", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                giohang.remove( giohangt );
                adapter.notifyDataSetChanged();
                Toast.makeText( Danhsachhoadonnhap.this, "Dữ liệu đã bị xóa", Toast.LENGTH_LONG ).show();
                db.executeSQL( "Delete FROM hoadonnhap where Mahd ='" + Mac + "'" );
                db2ListView();
            }
        } );
        builder.setPositiveButton( "Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText( Danhsachhoadonnhap.this, "Dữ liệu không bị xóa", Toast.LENGTH_LONG ).show();
            }
        } );
        builder.create().show();

    }

}
