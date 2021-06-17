package com.example.quanlychthuoc.Main.Nhanvien;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychthuoc.Adapter.AdapterNhanVien;
import com.example.quanlychthuoc.Class.NhanVien;
import com.example.quanlychthuoc.DatabaseHandler;
import com.example.quanlychthuoc.Main.Sanpham.Trangchu;
import com.example.quanlychthuoc.Main.Sanpham.Updatesanpham;
import com.example.quanlychthuoc.R;

import java.util.ArrayList;

public class MainNhanvien extends AppCompatActivity {
  DatabaseHandler db = new DatabaseHandler( this );
    ListView lvnv;
    ImageButton bt, tk;
    ArrayList<NhanVien> arrnhanvien=null;
    ArrayAdapter<NhanVien> adapter=null;
    NhanVien nv = new NhanVien();
    int ps;
    EditText edtk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_nhanvien );
        tk = (ImageButton) findViewById( R.id.timkiemnhanvien );
        edtk = (EditText) findViewById( R.id.edtimkiemnhanvien );


        db.copyDB2SDCard();
        int sbg = db.GetCount( "select * from nhanvien" );
       // Toast.makeText( this, "Số nhân viên: " + sbg, Toast.LENGTH_LONG ).show();
        lvnv = (ListView) findViewById( R.id.listnhanvien );
        bt = (ImageButton) findViewById( R.id.themnv );
/////////////////////////


        registerForContextMenu( lvnv );
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setDisplayShowHomeEnabled( true );
        }
        //
        bt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainNhanvien.this, Themnv.class );
                startActivity( intent );
                finish();
            }
        } );
        db2ListView();
        lvnv.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent( MainNhanvien.this, thongtinnv.class );
                intent.putExtra( "manv", arrnhanvien.get( position ).getManv() );
                startActivity( intent );
            }
        } );

        lvnv.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                ps = position;
                return false;
            }
        } );
        loadData( "SELECT * FROM nhanvien" );
        tk.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String timkiem = edtk.getText().toString();
                loadData( "SELECT * FROM nhanvien WHERE manv like '%" + timkiem + "%' or tennv like '%" + timkiem + "%' " +
                        "or sdt like '%" + timkiem + "%' or scmnd like '%" + timkiem + "%' or diachi like '%" + timkiem + "%'" );
            }
        } );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected( item );
    }

    public void loadData(String sql) {
        arrnhanvien = new ArrayList<NhanVien>();
        adapter = new AdapterNhanVien( this, R.layout.item_nhanvien, arrnhanvien );
        Log.d( "data", "loadData: " + sql );
        Cursor cursor = db.getCursor( "select * from nhanvien" );
        while (cursor != null && cursor.moveToNext()) {
            NhanVien sp = new NhanVien();
            sp.setManv( cursor.getString( 0 ) );
            sp.setTennv( cursor.getString( 1 ) );
            sp.setSdt( cursor.getString( 2 ) );
            sp.setSocmnd( cursor.getString( 3 ) );
            sp.setDiachi( cursor.getString( 4 ) );
            arrnhanvien.add( sp );
        }
        lvnv.setAdapter( adapter );
    }


    //Phương thức: Load dữ liệu lên ListView
    public void db2ListView() {
        arrnhanvien = new ArrayList<NhanVien>();
        NhanVien row;
        Cursor c = db.getCursor( "select * from nhanvien" );
        c.moveToFirst();
        while (!c.isAfterLast()) {
            row = new NhanVien();
            row.manv = c.getString( 0 );
            row.tennv = c.getString( 1 );
            row.sdt = c.getString( 2 );
            row.socmnd = c.getString( 3 );
            row.diachi = c.getString( 4 );
            arrnhanvien.add( row );
            c.moveToNext();
        }

        adapter = new AdapterNhanVien( this, R.layout.item_nhanvien, arrnhanvien );
        lvnv.setAdapter( adapter );
        adapter.notifyDataSetChanged();
        c.close();
        registerForContextMenu( lvnv );

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
        Bundle bundle = new Bundle();
        String manv = adapter.getItem( ps ).getManv();
        if (item.getItemId() == 1) {
            Xoa( manv );
        } else {
            switch (item.getItemId()) {
                case 2:

                    intent = new Intent( MainNhanvien.this, Updatenhanvien.class );
                    intent.putExtra( "manv", manv );

                    bundle.putString( "manv", manv );
                    intent.putExtras( bundle );

                    startActivity( intent );
                    finish();
                    break;
            }
            bundle.putString( "manv", manv );//
            Intent getData = getIntent();
            ps = getData.getIntExtra( "id", -1 );

        }
        return super.onContextItemSelected( item );
    }

    public NhanVien Getma(String Ma) {
        Cursor cursor = db.getCursor( "SELECT * FROM nhanvien WHERE manv = '" + Ma + "'" );
        cursor.moveToFirst();
        nv = new NhanVien( cursor.getString( 0 ), cursor.getString( 1 ), cursor.getString( 2 ), cursor.getString( 3 ), cursor.getString( 4 ) );
        return nv;
    }

    public void Xoa(final String Manv) {
        Toast.makeText( this, "Bạn vừa chọn xóa", Toast.LENGTH_SHORT ).show();
        AlertDialog.Builder al = new AlertDialog.Builder( this );
        al.setTitle( "Xóa !" );

        al.setNegativeButton( "Xóa", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                arrnhanvien.remove( nv );
                adapter.notifyDataSetChanged();
                Toast.makeText( MainNhanvien.this, "Dữ liệu đã bị xóa", Toast.LENGTH_LONG ).show();
                db.executeSQL( "Delete FROM nhanvien where manv ='" + Manv + "'" );
                Log.e( "đã xóa", "aaaaaaaaaaaaaaaaa: " + Manv );
                db2ListView();
            }
        } );
        al.setPositiveButton( "Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText( MainNhanvien.this, "Dữ liệu không bị xóa", Toast.LENGTH_LONG ).show();
            }
        } );
        al.create().show();

    }

}


