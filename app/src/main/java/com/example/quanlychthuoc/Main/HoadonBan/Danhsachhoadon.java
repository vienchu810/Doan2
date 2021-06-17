package com.example.quanlychthuoc.Main.HoadonBan;

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

import com.example.quanlychthuoc.Class.GioHang;
import com.example.quanlychthuoc.DatabaseHandler;
import com.example.quanlychthuoc.R;
import com.example.quanlychthuoc.Main.Sanpham.Trangchu;

import java.util.ArrayList;

public class Danhsachhoadon extends AppCompatActivity {
   DatabaseHandler db = new DatabaseHandler( this );
    ArrayList<com.example.quanlychthuoc.Class.GioHang> giohang = null;
    ArrayAdapter<com.example.quanlychthuoc.Class.GioHang> adapter = null;
   GioHang giohangt = new GioHang();
    ListView lv;
    Button bt;
    int ps;
    EditText ma, tenkh, diachi, ngay, edtk;
    TextView togtien;
    ImageButton gh, tr, tk;
    public static final String MaB = "Masp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_danhsachhoadon );
        lv = (ListView) findViewById( R.id.lvhoadon );
        ma = (EditText) findViewById( R.id.mahd );
        tenkh = (EditText) findViewById( R.id.tvTenKH );
        diachi = (EditText) findViewById( R.id.tvDiaChi );
        ngay = (EditText) findViewById( R.id.tvngay );
        togtien = (TextView) findViewById( R.id.tvtien );
        gh = (ImageButton) findViewById( R.id.taohoadon );
        tr = (ImageButton) findViewById( R.id.trangchu );
        tk = (ImageButton) findViewById( R.id.timkiemhoadon );
        edtk = (EditText) findViewById( R.id.edtimkiemhoadon );
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setDisplayShowHomeEnabled( true );
        }
        ////////////////////////////////

        db.copyDB2SDCard();
        int sbg = db.GetCount( "SELECT * FROM hoadonban" );
        //  Toast.makeText( this, "số bản ghi:" + sbg, Toast.LENGTH_SHORT ).show();

        tr.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent( Danhsachhoadon.this, Trangchu.class );
                startActivity( in );
                finish();
            }
        } );
//        lv.setOnItemClickListener( new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
//
//                Intent intent = new Intent( Danhsachhoadon.this, Thongtinhoadon.class );
//                Bundle bundle = new Bundle(  );
//                bundle.putString( "Mahd", giohang.get( i ).getMahd() );
//                intent.putExtras( bundle );
//                startActivity( intent );
//
//            }
//        } );

        lv.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent( Danhsachhoadon.this, Thongtinhoadon.class );
                String ma = giohang.get( i ).getMahd();
                intent.putExtra( "Mahd", ma );
                startActivity( intent );
            }
        } );
        db2ListView();
        gh.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Danhsachhoadon.this, MainGioHang.class );
                startActivity( intent );
                finish();
            }
        } );


        lv.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                ps = position;
                return false;
            }
        } );
        //loadDatagiohang( "select*from hoadonban" );
        tk.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String timkiem = edtk.getText().toString();
                loadDatagiohang( "SELECT * FROM hoadonban WHERE Mahd like '%" + timkiem + "%' or Tenkh like '%" + timkiem + "%' " +
                        "or Diachi like '%" + timkiem + "%' or date like '%" + timkiem + "%' or tongtien like '%" + timkiem + "%'" );
            }
        } );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected( item );
    }

    public com.example.quanlychthuoc.Class.GioHang Getmac(String Mahd) {

        Cursor cursor = db.getCursor( "SELECT * FROM hoadonban WHERE Mahd = '" + Mahd + "'" );
        cursor.moveToFirst();
        giohangt = new GioHang( cursor.getString( 0 ), cursor.getString( 1 ), cursor.getString( 2 ),
                cursor.getString( 3 ), cursor.getInt( 4 ) );
        return giohangt;
    }

    public void loadDatagiohang(String sql) {
        giohang = new ArrayList<GioHang>();

        adapter = new ArrayAdapter<GioHang>( this, R.layout.item_hd, giohang );
        Log.d( "data", "loadData: " + sql );
        Cursor cursor = db.getCursor( sql );
        while (cursor != null && cursor.moveToNext()) {
            com.example.quanlychthuoc.Class.GioHang c = new com.example.quanlychthuoc.Class.GioHang();
            c.setMahd( cursor.getString( 0 ) );
            c.setHoten( cursor.getString( 1 ) );
            c.setDiachi( cursor.getString( 2 ) );
            c.setDate( cursor.getString( 3 ) );
            c.setTongtien( cursor.getInt( 4 ) );

            giohang.add( c );
        }

        lv.setAdapter( adapter );
    }

    //Phương thức: Load dữ liệu lên ListView
    public void db2ListView() {
        giohang = new ArrayList<com.example.quanlychthuoc.Class.GioHang>();
        com.example.quanlychthuoc.Class.GioHang row;
        Cursor cursor = db.getCursor( "SELECT * FROM hoadonban" );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            row = new com.example.quanlychthuoc.Class.GioHang();
            row.mahd = cursor.getString( 0 );
            row.hoten = cursor.getString( 1 );
            row.diachi = cursor.getString( 2 );
            row.date = cursor.getString( 3 );
            row.tongtien = cursor.getInt( 4 );

            giohang.add( row );
            cursor.moveToNext();
        }

        adapter = new ArrayAdapter<com.example.quanlychthuoc.Class.GioHang>( this, android.R.layout.simple_list_item_1, giohang );
        System.out.println( 12 );
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
        bun.putString( "Mathuoc", masp );//
        Intent getData = getIntent();
        ps = getData.getIntExtra( "id", -1 );


        return super.onContextItemSelected( item );

    }


    public void Xoa(final String Mac) {
        AlertDialog.Builder builder = new AlertDialog.Builder( Danhsachhoadon.this );
        builder.setTitle( "Xóa !" );
        builder.setNegativeButton( "Xóa", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                giohang.remove( giohangt );
                adapter.notifyDataSetChanged();
                Toast.makeText( Danhsachhoadon.this, "Dữ liệu đã bị xóa", Toast.LENGTH_LONG ).show();
                db.executeSQL( "Delete FROM hoadonban where Mahd ='" + Mac + "'" );
                db2ListView();
                //edt_search.setText("");
//                Intent load = new Intent(trangchu.this, trangchu.class);
//                startActivity(load);
            }
        } );
        builder.setPositiveButton( "Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText( Danhsachhoadon.this, "Dữ liệu không bị xóa", Toast.LENGTH_LONG ).show();
            }
        } );
        builder.create().show();

    }

}
