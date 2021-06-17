package com.example.quanlychthuoc.Main.Sanpham;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychthuoc.Adapter.AdapterNhanVien;
import com.example.quanlychthuoc.Adapter.SanPhamAdapter;
import com.example.quanlychthuoc.Class.SanPham;
import com.example.quanlychthuoc.DatabaseHandler;
import com.example.quanlychthuoc.Main.HoadonBan.MainGioHang;
import com.example.quanlychthuoc.R;


import java.util.ArrayList;


public class Trangchu extends AppCompatActivity {

    // biến toàn cục
    ArrayList<com.example.quanlychthuoc.Class.SanPham> caycanh = null;
    ArrayAdapter<SanPham> adapter = null;
    SanPham SanPham = new SanPham();
    EditText ma, ten, dd, sl, gt, edtk;
    ListView lv;
    Button bt;
    ImageButton tk,gh;

    int ps;
   DatabaseHandler db = new DatabaseHandler( this );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sanpham );
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);}

        //anh xa
        ma = (EditText) findViewById( R.id.masp );
        ten = (EditText) findViewById( R.id.tensp );
        dd = (EditText) findViewById( R.id.dacdiem );
        sl = (EditText) findViewById( R.id.soluong );
        gt = (EditText) findViewById( R.id.giathanh );
        lv = findViewById( R.id.listviewmanhinhchinh );
        ImageButton bt = (ImageButton) findViewById( R.id.themsp );
        tk = (ImageButton) findViewById( R.id.bttimkiem );
        gh= (ImageButton) findViewById( R.id.giohangbt );
        edtk = (EditText) findViewById( R.id.edtimkiem );
        ViewFlipper vf = (ViewFlipper) findViewById( R.id.viewflipper );

        // Quang cao
        vf.setFlipInterval( 2000 );
        vf.setAutoStart( true );

        /////////////////////////////////
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setDisplayShowHomeEnabled( true );
        }
        ////////////////////////////////


        db.copyDB2SDCard();
        int sbg = db.GetCount( "SELECT * FROM sanpham" );
        Toast.makeText( this, "số bản ghi:" + sbg, Toast.LENGTH_SHORT ).show();


        db2ListView();
        bt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent( Trangchu.this, themsanpham.class );
                startActivity( in );
                finish();
            }
        } );
        lv.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                Intent intent = new Intent( Trangchu.this, ThongTinSanPham.class );
                intent.putExtra( "masp", caycanh.get( i ).getMasp().toString() );
                startActivity( intent );

            }
        } );

        lv.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                ps = position;
                return false;
            }
        } );

        loadData( "SELECT * FROM sanpham" );
        tk.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String timkiem = edtk.getText().toString();
                loadData( "SELECT * FROM sanpham WHERE masp like '%" + timkiem + "%' or tensp like '%" + timkiem + "%' " +
                        "or mota like '%" + timkiem + "%' or soluong like '%" + timkiem + "%' or gia like '%" + timkiem + "%'" );
            }
        } );

        // registerForContextMenu( lv );
gh.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent= new Intent( Trangchu.this, MainGioHang.class );
        startActivity( intent );
    }
} );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected( item );
    }


    public SanPham Getmac(String MaCay) {

        Cursor cursor = db.getCursor( "SELECT * FROM sanpham WHERE masp = '" + MaCay + "'" );
        cursor.moveToFirst();
        SanPham = new SanPham( cursor.getString( 0 ), cursor.getString( 1 ), cursor.getString( 2 ), cursor.getInt( 3 ),
                cursor.getInt( 4 ) );
        return SanPham;
    }

    public void loadData(String sql) {
        caycanh = new ArrayList<SanPham>();
        adapter = new SanPhamAdapter( this, R.layout.item_sanpham, caycanh );

        Log.d( "data", "loadData: " + sql );
        Cursor cursor = db.getCursor( sql );
        while (cursor != null && cursor.moveToNext()) {
            SanPham c = new SanPham();

            c.setMasp( cursor.getString( 0 ) );
            c.setTensp( cursor.getString( 1 ) );
            c.setDD( cursor.getString( 2 ) );
            c.setSl( cursor.getInt( 3 ) );
            c.setGt( cursor.getInt( 4 ) );

            caycanh.add( c );
        }

        lv.setAdapter( adapter );
    }

    //Phương thức: Load dữ liệu lên ListView
    public void db2ListView() {
        caycanh = new ArrayList<SanPham>();
        SanPham row;
        Cursor cursor = db.getCursor( "SELECT * FROM sanpham" );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            row = new SanPham();
            row.Masp = cursor.getString( 0 );
            row.Tensp = cursor.getString( 1 );
            row.DD = cursor.getString( 2 );
            row.sl = cursor.getInt( 3 );
            row.gt = cursor.getInt( 4 );

            caycanh.add( row );
            cursor.moveToNext();
        }
        adapter = new SanPhamAdapter( this, R.layout.item_sanpham, caycanh );
        lv.setAdapter( adapter );
        adapter.notifyDataSetChanged();
        cursor.close();
        registerForContextMenu( lv );
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu( menu, v, menuInfo );

        menu.add( Menu.NONE, 1, menu.NONE, "Xóa" );
        menu.add( Menu.NONE, 2, menu.NONE, "Sửa" );

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Intent intent = null;
        Bundle bun = new Bundle();
        String masp = adapter.getItem( ps ).getMasp();
        if (item.getItemId() == 1) {
            Xoa( masp );
        } else {
            switch (item.getItemId()) {
                case 2:
                    intent = new Intent( Trangchu.this, Updatesanpham.class );
                    intent.putExtra( "masanpham", masp );
                    Bundle bundle = new Bundle();
                    bundle.putString( "masp", masp );
                    intent.putExtras( bundle );

                    startActivity( intent );
                    finish();
                    break;
            }
            bun.putString( "masp", masp );//
            Intent getData = getIntent();
            ps = getData.getIntExtra( "id", -1 );

        }
        return super.onContextItemSelected( item );

    }


    public void Xoa(final String Mac) {

        AlertDialog.Builder builder = new AlertDialog.Builder( Trangchu.this );
        builder.setTitle( "Xóa !" );
        builder.setNegativeButton( "Xóa", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                caycanh.remove(SanPham);
                adapter.notifyDataSetChanged();
                Toast.makeText( Trangchu.this, "Dữ liệu đã bị xóa", Toast.LENGTH_LONG ).show();
                db.executeSQL( "Delete FROM sanpham where masp ='" + Mac + "'" );
                db2ListView();

            }
        } );
        builder.setPositiveButton( "Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText( Trangchu.this, "Dữ liệu không bị xóa", Toast.LENGTH_LONG ).show();
            }
        } );
        builder.create().show();

    }

}








