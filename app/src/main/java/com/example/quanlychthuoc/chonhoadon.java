package com.example.quanlychthuoc;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychthuoc.Main.HoadonBan.Danhsachhoadon;
import com.example.quanlychthuoc.Main.hoadonnhap.Danhsachhoadonnhap;


public class chonhoadon extends AppCompatActivity {
    Button hdn, hdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_chonhoadon );

        hoadonnhap();
        hoadonban();
    }

    private void hoadonnhap() {
        hdn = (Button) findViewById( R.id.chonhoadonnhap );
        hdn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hdn.getText().toString().equals( "" )) ;
                Intent intent = new Intent(chonhoadon.this, Danhsachhoadonnhap.class );
                startActivity( intent );
            }
        } );
    }
    private void hoadonban() {
        hdb = (Button) findViewById( R.id.chonhoadonban );
        hdb.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hdb.getText().toString().equals( "" )) ;
                Intent it = new Intent( chonhoadon.this, Danhsachhoadon.class );
                startActivity( it );
            }
        } );
    }
}

