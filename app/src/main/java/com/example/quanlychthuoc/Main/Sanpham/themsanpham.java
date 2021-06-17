package com.example.quanlychthuoc.Main.Sanpham;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychthuoc.DatabaseHandler;
import com.example.quanlychthuoc.R;

import java.util.ArrayList;

public class themsanpham extends AppCompatActivity {
    //biến toàn cục
    ArrayList<String> arrList = null;
    ArrayAdapter<String> adap = null;
    EditText ma, ten, dd, sl, gt;
    ListView lv;
    Button bt, hd;
   DatabaseHandler db = new DatabaseHandler( this );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        // anh xa
        setContentView( R.layout.activity_themsanpham );
        ma = (EditText) findViewById( R.id.masp );
        ten = (EditText) findViewById( R.id.tensp );
        dd = (EditText) findViewById( R.id.dacdiem );
        sl = (EditText) findViewById( R.id.soluong );
        gt = (EditText) findViewById( R.id.giathanh );
        bt = (Button) findViewById( R.id.themcay );
        lv = (ListView) findViewById( R.id.listviewmanhinhchinh );
       // hd = (Button) findViewById( R.id.caymoi );
        bt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addcay();
            }
        } );


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setDisplayShowHomeEnabled( true );
        }
    }

    public void addcay() {
        String mac = ma.getText().toString().trim();
        String tenc = ten.getText().toString();
        String ddc = dd.getText().toString();
        String slc = sl.getText().toString();
        String giac = gt.getText().toString();
        if (ma.length() == 0) {
            Toast.makeText( themsanpham.this, "Nhập dữ liệu", Toast.LENGTH_SHORT ).show();
        } else {
            // Kiểm tra sự tồn tại của mã sản phẩm
            int checkma = db.GetCount( "Select * from sanpham where masp=\"" + mac + "\"" );
            if (checkma == 1) {
                // Nếu mã cây đã tồn tại đưa ra thông báo
                AlertDialog.Builder al = new AlertDialog.Builder( this );
                al.setTitle( " Thông báo " );
                al.setMessage( "Mã thuốc đã tồn tại ! " );
                al.create().show();
            } else if (checkma == 0) {
                if (tenc.length() == 0) {
                    Toast.makeText( themsanpham.this, "Nhập dữ liệu", Toast.LENGTH_SHORT ).show();
                } else if (ddc.length() == 0) {
                    Toast.makeText( themsanpham.this, "Nhập dữ liệu", Toast.LENGTH_SHORT ).show();
                } else if (slc.length() == 0) {
                    Toast.makeText( themsanpham.this, "Nhập dữ liệu", Toast.LENGTH_SHORT ).show();

                } else if (giac.length() == 0) {
                    Toast.makeText( themsanpham.this, "Nhập dữ liệu", Toast.LENGTH_SHORT ).show();

                } else {
                    // Thêm dữ liệu vào database
                    db.executeSQL( "insert into sanpham values('" + mac + "','" + tenc + "','" + ddc + "','" + slc + "','" + giac + "')" );
                    Toast.makeText( getApplicationContext(), "Thêm thành công!!!", Toast.LENGTH_LONG ).show();
                    Intent back = new Intent( themsanpham.this, Trangchu.class );
                    startActivity( back );
                    finish();
                }
            }
        }
    }
}
