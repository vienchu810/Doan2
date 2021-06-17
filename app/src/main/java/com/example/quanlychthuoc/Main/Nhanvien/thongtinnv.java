package com.example.quanlychthuoc.Main.Nhanvien;

import android.database.Cursor;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychthuoc.Class.NhanVien;
import com.example.quanlychthuoc.DatabaseHandler;
import com.example.quanlychthuoc.R;


public class thongtinnv extends AppCompatActivity {
    TextView mNV,tNV,dcnv,sdt,cm;

String maNV;
DatabaseHandler db = new DatabaseHandler( this );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_thongtinnv );
        mNV = (TextView) findViewById( R.id.txtmanv_tt );
        tNV = (TextView) findViewById( R.id.txttenv_tt );
        dcnv = (TextView) findViewById( R.id.txtdiachi_tt );
        sdt = (TextView) findViewById( R.id.txtsodt_tt );
        cm = (TextView) findViewById( R.id.txtcmnd_tt );

        Bundle bundle = getIntent().getExtras();
        maNV = bundle.getString( "manv" );
        Cursor cursor = db.getCursor( "SELECT * FROM nhanvien where manv='" + maNV + "'" );

        cursor.moveToFirst();
        NhanVien c = new NhanVien();
        if (cursor != null && cursor.getCount() > 0) {
            c.setManv( cursor.getString( 0 ) );
            c.setTennv( cursor.getString( 1 ) );
            c.setSdt( cursor.getString( 2 ) );
            c.setSocmnd( cursor.getString( 3 ) );
            c.setDiachi( cursor.getString( 4 ) );


            mNV.setText( c.getManv() );
            tNV.setText( c.getTennv() );
            sdt.setText( c.getSdt() );
            cm.setText( c.getSocmnd() );
            dcnv.setText( c.getDiachi() );
        }

    }


    //back tren ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected( item );
    }

}




