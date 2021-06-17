package com.example.quanlychthuoc.Main.Sanpham;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychthuoc.DatabaseHandler;
import com.example.quanlychthuoc.R;


public class Updatesanpham extends AppCompatActivity {
   DatabaseHandler db = new DatabaseHandler( this );

    String masp;
    Button update;
    EditText mac, tenc, dd, sl, gt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_updatesanpham);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setDisplayShowHomeEnabled( true );


        }
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            masp = intent.getStringExtra( "masp" );
        }


        mac = (EditText) findViewById( R.id.edt_mac );
        tenc = (EditText) findViewById( R.id.edt_tenc );
        dd = (EditText) findViewById( R.id.edt_diem );
        sl = (EditText) findViewById( R.id.edt_sluong );
        gt = (EditText) findViewById( R.id.edt_giacay );
        update = (Button) findViewById( R.id.bt_Updatecay );

        mac.setText( masp );
        update.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Update();
            }
        } );

    }




public void Update(){
    String ma = mac.getText().toString().trim();
        String ten = tenc.getText().toString();
        String ddiem = dd.getText().toString();
        int soluong = Integer.parseInt( sl.getText().toString() );
        int gia = Integer.parseInt( gt.getText().toString() );



    if (ma.length() == 0) {
        Toast.makeText(Updatesanpham.this, " Nhập Thông Tin ! ", Toast.LENGTH_LONG).show();
    } else {
        int checkma = db.GetCount("SELECT * FROM sanpham where masp='" + ma + "'" );
        if (checkma == 1) {
//
            if (ten.length() == 0) {
                    Toast.makeText( Updatesanpham.this, "Nhập thông tin", Toast.LENGTH_LONG ).show();
                } else if (ddiem.length() == 0) {
                    Toast.makeText( Updatesanpham.this, "Nhập thông tin", Toast.LENGTH_LONG ).show();
                } else if (soluong < 0) {
                    Toast.makeText( Updatesanpham.this, "Nhập thông tin", Toast.LENGTH_LONG ).show();
                } else if (gia < 0) {
                    Toast.makeText( Updatesanpham.this, "Nhập thông tin", Toast.LENGTH_LONG ).show();

            } else {
                db.executeSQL( "Update sanpham set tensp ='" + ten + "',mota ='" + ddiem + "',soluong ='" + soluong + "',gia ='" + gia + "'where masp ='"+ma+"'" );
                    Toast.makeText( getApplicationContext(), "Đã cập nhập!", Toast.LENGTH_LONG ).show();
                    Intent back = new Intent( Updatesanpham.this, Trangchu.class );
                    startActivity( back );
                            finish();
            }

        } else if (checkma == 0) {

            AlertDialog.Builder al = new AlertDialog.Builder(this);

            al.setMessage("Mã thuốc ko tồn tại! ");
            al.create().show();

        }

    }
}
}






