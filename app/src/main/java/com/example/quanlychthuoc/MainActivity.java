package com.example.quanlychthuoc;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.quanlychthuoc.Main.Nhanvien.MainNhanvien;
import com.example.quanlychthuoc.Main.Sanpham.Trangchu;


public class MainActivity extends AppCompatActivity {
    CardView caycanh, nhanvien,thongke, donhang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_main);
        caycanh();
        nhanvien();
        thongke();
        hoadon();
    }

    private void caycanh() {
        caycanh = findViewById( R.id.btnsp);
        caycanh.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  if (caycanh.getText().toString().equals( "" )) ;
                Intent intent = new Intent(MainActivity.this, Trangchu.class );
                startActivity( intent );
            }
        } );
    }

    private void nhanvien() {
        nhanvien = findViewById( R.id.btnnv );
        nhanvien.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   if (nhanvien.getText().toString().equals( "" )) ;
                Intent it = new Intent( MainActivity.this, MainNhanvien.class );
                startActivity( it );
            }
        } );
    }
    private void thongke() {
        thongke =  findViewById( R.id.btnthongke );
        thongke.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   if (thongke.getText().toString().equals( "" )) ;
                Intent it3 = new Intent(MainActivity.this,Thongke.class );
                startActivity( it3 );
            }
        } );
    }
    private void hoadon() {
        donhang =findViewById( R.id.btndonhang );
        donhang.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          //      if (donhang.getText().toString().equals( "" )) ;
                Intent it4 = new Intent(MainActivity.this,chonhoadon.class );
                startActivity( it4 );
            }
        } );
    }
}

