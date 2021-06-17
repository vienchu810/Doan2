package com.example.quanlychthuoc.Main.Nhanvien;

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



public class Updatenhanvien extends AppCompatActivity {
    String manv;
    Button update;
    EditText maNV, tenNV, SDT, CMDN, dc;
   DatabaseHandler db = new DatabaseHandler( this );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_updatenhanvien );

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setDisplayShowHomeEnabled( true );


        }
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            manv = intent.getStringExtra( "manv" );
        }


        maNV = (EditText) findViewById( R.id.upmanv);
        tenNV = (EditText) findViewById( R.id.uptenv );
        SDT= (EditText) findViewById( R.id.upsodt );
        CMDN = (EditText) findViewById( R.id.upcmnd );
        dc= (EditText) findViewById( R.id.updiachi );
        update = (Button) findViewById( R.id.updatenhanvien);

        maNV.setText( manv );
        update.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update();
            }
        } );

    }




    public void Update(){
        String maN = maNV.getText().toString().trim();
        String tenN = tenNV.getText().toString();
        String sdt = SDT.getText().toString();
        String CM = CMDN.getText().toString();
        String diac = dc.getText().toString();



        if (maN.length() == 0) {
            Toast.makeText( Updatenhanvien.this, " Nhập Thông Tin ! ", Toast.LENGTH_LONG).show();
        } else {
            int checkma = db.GetCount("SELECT * FROM nhanvien where manv ='" + maN + "'" );
            if (checkma == 1) {
//
                if (tenN.length() == 0) {
                    Toast.makeText( Updatenhanvien.this, " Nhập Thông Tin ! ", Toast.LENGTH_LONG).show();
                } else if (sdt.length() == 0) {
                    Toast.makeText( Updatenhanvien.this, " Nhập Thông Tin ! ", Toast.LENGTH_LONG).show();
                } else if (CM.length()==0) {
                    Toast.makeText( Updatenhanvien.this, " Nhập Thông Tin ! ", Toast.LENGTH_LONG).show();
                } else if (diac.length()==0) {
                    Toast.makeText( Updatenhanvien.this, " Nhập Thông Tin ! ", Toast.LENGTH_LONG).show();

                } else {
                    db.executeSQL( "Update nhanvien set tennv='" + tenNV+ "',sdt='" + sdt + "',scmnd='" + CM + "',diachi='" + diac + "'where manv='"+maN+"'" );
                    Toast.makeText( getApplicationContext(), "Đã cập nhập!", Toast.LENGTH_LONG ).show();
                    Intent back = new Intent( Updatenhanvien.this, MainNhanvien.class );
                    startActivity( back );
                    finish();
                }

            } else if (checkma == 0) {

                AlertDialog.Builder al = new AlertDialog.Builder(this);

                al.setMessage("Mã nhân viên ko tồn tại! ");
                al.create().show();

            }

        }
    }
}



