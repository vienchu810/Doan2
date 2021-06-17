package com.example.quanlychthuoc;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Thongke extends AppCompatActivity {
    private static final String TAG = "";
    ImageButton btn_date1, btn_date2;
    TextView tv_Tongthu, tv_Tongnhap, txtdate1, txtdate2;
    String Mahd;
    int Tong = 0;
   DatabaseHandler db = new DatabaseHandler( this );
    private DatePickerDialog.OnDateSetListener mDateSetListener, mDateSetListener2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_thongke );
        btn_date1 = (ImageButton) findViewById( R.id.btn_date1 );
        btn_date2 = (ImageButton) findViewById( R.id.btn_date2 );
        tv_Tongthu = (TextView) findViewById( R.id.doanhthu );
        tv_Tongnhap = (TextView) findViewById( R.id.tvTongNhap );
        txtdate1 = (TextView) findViewById( R.id.txtdate1 );
        txtdate2 = (TextView) findViewById( R.id.txtdate2 );
chon();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menuchon, menu );
        return super.onCreateOptionsMenu( menu );
    }

      public void chon(){
        btn_date1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get( Calendar.YEAR );
                int month = cal.get( Calendar.MONTH );
                int day = cal.get( Calendar.DAY_OF_MONTH );

                DatePickerDialog dialog = new DatePickerDialog( Thongke.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day );
                dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
                dialog.show();
            }
        } );
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d( TAG, "onDateSet: đd/mm/yyy: " + day + "/" + month + "/" + year );

                String ngay = day + "/" + month + "/" + year;

                txtdate1.setText( ngay );

            }
        };

        btn_date2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get( Calendar.YEAR );
                int month = cal.get( Calendar.MONTH );
                int day = cal.get( Calendar.DAY_OF_MONTH );

                DatePickerDialog dialog = new DatePickerDialog(
                        Thongke.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener2,
                        year, month, day );
                dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
                dialog.show();
            }
        } );
        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d( TAG, "onDateSet: đd/mm/yyy: " + day + "/" + month + "/" + year );

                String ngay = day + "/" + month + "/" + year;

                txtdate2.setText( ngay );

            }
        };


    }


    public void Tongthu(View v) {
        int Tong =0;
        Cursor c = db.getCursor( "Select  * from hoadonban ");
        if(c.moveToFirst()){
            do{
                Tong += c.getInt(  4);
            } while (c.moveToNext());
        }
        tv_Tongthu.setText( Tong +"VND");

    }
    public void Tongnhap(View v) {
        int Tong =0;
        Cursor c = db.getCursor( "Select  * from hoadonnhap ");
        if(c.moveToFirst()){
            do{
                Tong += c.getInt(  4);
            } while (c.moveToNext());
        }
        tv_Tongnhap.setText( Tong +"VND");

    }
}
