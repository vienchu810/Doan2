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

import java.io.ByteArrayOutputStream;

public class Themnv extends AppCompatActivity {
    Button bt;
    EditText mav, ten, dc, sdt,cm;
  DatabaseHandler db = new DatabaseHandler( this );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_themnv );

        mav = (EditText) findViewById( R.id.manv );
        ten = (EditText) findViewById( R.id.tenv );
        dc = (EditText) findViewById( R.id.diachi );
        sdt = (EditText) findViewById( R.id.sodt );
        bt = (Button) findViewById( R.id.themnhanvien );
        cm= (EditText) findViewById( R.id.cmnd );
        //

//        forde.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent( Intent.ACTION_PICK );
//                intent.setType( "image/*" );
//                startActivityForResult( intent, REQUEST_CODE_FOLDER );
//            }
//        } );
        bt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addnv();
            }
        } );
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
//            Uri uri = data.getData();
//            try {
//                InputStream inputStream = getContentResolver().openInputStream( uri );
//                Bitmap bitmap = BitmapFactory.decodeStream( inputStream );
//                anhnv.setImageBitmap( bitmap );
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//        super.onActivityResult( requestCode, resultCode, data );
   }


    public void addnv() {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        String ma = mav.getText().toString().trim();
        String tennv = ten.getText().toString();
        String sodt = sdt.getText().toString();
        String scm = cm.getText().toString();
        String dcnc = dc.getText().toString();
        if (ma.length() == 0) {
            Toast.makeText( this, "Thêm nv", Toast.LENGTH_SHORT ).show();
        } else {
            // Kiểm tra sự tồn tại của mã nhân viên
            int checkma = db.GetCount( "Select * from nhanvien where manv=\"" + mav + "\"" );
            if (checkma == 1) {
                // Nếu Mã nv đã tồn tại đưa ra thông báo
                AlertDialog.Builder al = new AlertDialog.Builder( this );
                al.setTitle( " Thông báo " );
                al.setMessage( "Nhân viên đã tồn tại ! " );
                al.create().show();
            }

            else if (checkma == 0) {
                if (tennv.length() == 0) {
                    Toast.makeText( Themnv.this, "Nhập dữ liệu", Toast.LENGTH_SHORT ).show();
                } else if (sodt.length() == 0) {
                    Toast.makeText( Themnv.this, "Nhập dữ liệu", Toast.LENGTH_SHORT ).show();
                } else if (cm.length() == 0) {
                    Toast.makeText( Themnv.this, "Nhập dữ liệu", Toast.LENGTH_SHORT ).show();
                } else if (dcnc.length() == 0) {
                    Toast.makeText( Themnv.this, "Nhập dữ liệu", Toast.LENGTH_SHORT ).show();
                } else {
                    // Thêm dữ liệu vào database
                    db.executeSQL( "insert into nhanvien values('" + ma + "','" + tennv + "','" + sodt + "','" + scm + "','" + dcnc + "')" );
                    Toast.makeText( getApplicationContext(), "Thêm thành công!!!", Toast.LENGTH_LONG ).show();
                    Intent back = new Intent( Themnv.this, MainNhanvien.class );
                    startActivity( back );
                    finish();
                }
            }
        }
    }
}
