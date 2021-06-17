package com.example.quanlychthuoc.Main.hoadonnhap;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychthuoc.DatabaseHandler;
import com.example.quanlychthuoc.Main.Sanpham.themsanpham;
import com.example.quanlychthuoc.R;
import com.example.quanlychthuoc.Class.SanPham;


import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Themhoadon extends AppCompatActivity {

    EditText tvdate;

    Button btnhap, huy, btnhaphang, moi;

    TextView sotien;
    ListView lvnhaphang;
    Integer NhapHang= 0;

    ArrayList<SanPham> SanPhams = new ArrayList<>();
    ArrayAdapter<SanPham> adapter = null;
    public static ArrayList<SanPham> sp = new ArrayList<>();
    DatabaseHandler db = new DatabaseHandler( this );
    ArrayAdapter<SanPham> adap = null;

    EditText edtMaHD;
    EditText tvNV, tvDiaChi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_hoadonnhap );
        tvdate = (EditText) findViewById( R.id.tvngay );
        btnhap = (Button) findViewById( R.id.update );
        tvNV = (EditText) findViewById( R.id.tvNgNhap );
        tvDiaChi = (EditText) findViewById( R.id.tvdichi );
        edtMaHD = (EditText) findViewById( R.id.mahdn);
        btnhaphang = (Button) findViewById( R.id.tvnhap );
        lvnhaphang = (ListView) findViewById( R.id.lvhoadonnhap );
        sotien = (TextView) findViewById( R.id. thanhtoan);
        moi = (Button) findViewById( R.id.sanphammoi );
        huy=findViewById(R.id.huyhdn);
        ThanhToan();
        getspItem();
huy.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Themhoadon.this,Danhsachhoadonnhap.class);
        startActivity(intent);
    }
});

        tvdate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonngay();
            }
        } );
        db2ListView();
        moi.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it1 = new Intent( Themhoadon.this, themsanpham.class );
                startActivity( it1 );
                finish();
            }
        } );
        db2ListView();
        btnhap.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it1 = new Intent( Themhoadon.this, list_hdn.class );
                startActivity( it1 );
                finish();
            }
        } );
    }

    public void getData(ArrayList<SanPham> tt) {
        sp.addAll( tt );
        for (int i = 0; i < tt.size(); i++) {
            System.out.println( tt.get( i ).getSluongcay() );

        }
    }

    public void ThanhToan() {
        btnhaphang.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for (int i = 0; i < sp.size(); i++) {
                    System.out.println( "" + sp.get( i ).sluongthuoc + "" + sp.get( i ).sl );
                    int sl = sp.get( i ).sluongthuoc + sp.get( i ).sl ;
                    db.executeSQL( "update sanpham set soluong = " + ( sp.get( i ).sluongthuoc + sp.get( i ).sl ) + " Where masp='" + sp.get( i ).Masp + "'" );
                    db.executeSQL( "insert into hoadon values ('" + edtMaHD.getText().toString() + "','" + sp.get( i ).Masp + "','" + sp.get( i ).Tensp + "','" + sp.get( i ).gt + "'," + sp.get( i ).sl + " )" );
                }

                addhoadon();

            }
        } );
    }

    public void db2ListView() {
        sp = new ArrayList<SanPham>();
        sp.clear();
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

            sp.add( row );
            cursor.moveToNext();
        }
//        adapter = new ArrayAdapter<cay>( this, android.R.layout.simple_list_item_1, caycanh );
//        lvnhaphang.setAdapter( adapter );
//        adapter.notifyDataSetChanged();
//        cursor.close();
    }

    private void getspItem() {

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String spItems = extras.getString( "spItems", null );
            if (spItems != null && spItems.length() > 0) {
                try {
                    JSONArray giohang = new JSONArray( spItems );
                    for (int i = 0; i < giohang.length(); i++) {
                        JSONObject jsonObject = new JSONObject( giohang.getString( i ) );

                        SanPham c = new SanPham(
                                jsonObject.getString( "Masp" ),
                                jsonObject.getString( "Tensp" ),
                                jsonObject.getInt( "GiaBan" ),

                                jsonObject.getInt( "Giatri" )
                        );

                        NhapHang = NhapHang + (c.sluongthuoc * c.gt);

                        System.out.println( c.getTensp() );
                        SanPhams.add( c );

                    }

                    if (SanPhams.size() > 0) {
                        DecimalFormat formatter = new DecimalFormat( "###,###,###" );// định dạng thập phân
                        adap = new ArrayAdapter<SanPham>( this, android.R.layout.simple_list_item_1, SanPhams);
                        lvnhaphang.setAdapter( adap );
                        adap.notifyDataSetChanged();
                        sotien.setText( formatter.format( NhapHang ) + " VND" );

                    } else {
                        showMessage( "Chưa có mặt hàng nào!" );
                    }
                } catch (Exception e) {
                    showMessage( e.toString() );
                }
            }

        }
    }

    public void showMessage(String message) {
        Toast.makeText( this, message, Toast.LENGTH_LONG ).show();
    }

    private void chonngay() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get( Calendar.DATE );
        int thang = calendar.get( Calendar.MONTH );
        int nam = calendar.get( Calendar.YEAR );

        DatePickerDialog datePickerDialog = new DatePickerDialog( this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int nam, int thang, int ngay) {
                calendar.set( nam, thang, ngay );
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "dd/MM/yy" );
                tvdate.setText( simpleDateFormat.format( calendar.getTime() ) );
            }
        }, nam, thang, ngay );
        datePickerDialog.show();

    }

    public void addhoadon() {
        String mah = edtMaHD.getText().toString().trim();
        String nv = tvNV.getText().toString();
        String dc = tvDiaChi.getText().toString();
        String date = tvdate.getText().toString();


        if (mah.length() == 0) {
            Toast.makeText( this, "Bạn chưa nhập dữ liệu!", Toast.LENGTH_SHORT ).show();
        } else {
            // Kiểm tra sự tồn tại của mã sản phẩm
            int checkma = db.GetCount( "Select * from hoadonnhap where Mahd=\"" + mah + "\"" );
            if (checkma == 1) {
                // Nếu thẻ đã tồn tại đưa ra thông báo
                AlertDialog.Builder al = new AlertDialog.Builder( this );
                al.setTitle( " Thông báo " );
                al.setMessage( "Hóa đơn đã tồn tại ! " );
                al.create().show();
            } else if (checkma == 0) {
                if (nv.length() == 0) {
                    Toast.makeText( this, "Nhập dữ liệu", Toast.LENGTH_SHORT ).show();
                } else if (dc.length() == 0) {
                    Toast.makeText( this, "Nhập dữ liệu", Toast.LENGTH_SHORT ).show();
                } else if (date.length() == 0) {
                    Toast.makeText( this, "Nhập dữ liệu", Toast.LENGTH_SHORT ).show();

                } else {
                    // Thêm dữ liệu vào database
                    db.executeSQL( "insert into hoadonnhap values ('" + edtMaHD.getText() + "','" + tvNV.getText() + "','" + tvDiaChi.getText() + "','" + tvdate.getText() + "'," + NhapHang + " )" );

                    Toast.makeText( Themhoadon.this, "Đã thêm hóa đơn", Toast.LENGTH_SHORT ).show();
                    Intent back = new Intent( Themhoadon.this, Danhsachhoadonnhap.class );
                    startActivity( back );
                    finish();
                }

            }
        }
    }// e bảo bây h e muốn là nhấn vào cây mới ý thì nó thẽ thêm 1 cây mới vào hóa đơn a ạ
}