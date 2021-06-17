package com.example.quanlychthuoc.Main.HoadonBan;

import android.database.Cursor;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychthuoc.Class.GioHang;
import com.example.quanlychthuoc.DatabaseHandler;
import com.example.quanlychthuoc.Adapter.adaptechitiethoadon;
import com.example.quanlychthuoc.R;
import com.example.quanlychthuoc.Class.SanPham;

import java.util.ArrayList;

public class Thongtinhoadon extends AppCompatActivity {
    String maHD;
    ArrayList<SanPham> SanPhams = new ArrayList<>();
    ArrayList<String> spItems = new ArrayList<String>();
    adaptechitiethoadon adapter = null;
    TextView mah, TT ,kh,dc,date;
    ListView lv;
    int ps;
  DatabaseHandler db = new DatabaseHandler( this );
    ArrayList<SanPham> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_thongtinhoadon );
        mah = (TextView) findViewById( R.id.tvmahdxuat );
        lv = (ListView) findViewById( R.id.lvcthd );
        kh=(TextView) findViewById( R.id.khachhang );
        dc= (TextView) findViewById( R.id.dcgiaohhang );
        date= (TextView) findViewById( R.id.ngayban );
        TT = (TextView) findViewById( R.id.ThanhTien );
TT();

        lv.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                ps = position;
                return false;
            }
        } );

        Bundle bundle = getIntent().getExtras();
        maHD = bundle.getString( "Mahd" );
        Cursor cursor = db.getCursor( "SELECT * FROM hoadon where Mahd ='" + maHD + "'" );
        if(cursor.moveToFirst())
            do
        {
            String Masp = cursor.getString( 1 );
            String Tensp = cursor.getString( 2 );
            int sluongcay = cursor.getInt( 4);
            int gt = cursor.getInt( 3 );
            SanPham c = new SanPham( Masp, Tensp, gt, sluongcay );
            list.add( c );


        }while (cursor.moveToNext());

        //  adapter = new adaptechitiethoadon( this, R.layout.item_hd, giohang );
        adapter = new adaptechitiethoadon( this, android.R.layout.simple_list_item_1, list );
        lv.setAdapter( adapter );
        adapter.notifyDataSetChanged();
        cursor.close();



    }
    public void TT(){
        Bundle bundle = getIntent().getExtras();
        maHD = bundle.getString( "Mahd" );
        Cursor cursor = db.getCursor( "SELECT * FROM hoadonban where Mahd ='" + maHD + "'" );

        if(cursor.moveToFirst()) {

            GioHang c = new GioHang();
            c.setMahd( cursor.getString( 0 ) );
            c.setHoten( cursor.getString( 1 ) );
            c.setDiachi( cursor.getString( 2 ) );
            c.setDate( cursor.getString( 3 ) );
            c.setTongtien( cursor.getInt( 4 ) );
            mah.setText( c.getMahd() );
            kh.setText( c.getHoten() );
            dc.setText( c.getDiachi() );
            date.setText( c.getDate() );
            TT.setText( c.getTongtien() + "VND" );
        }


//    public float Thanhtien(ArrayList<cay> giohang) {
//        int s = 0;
//        for (int i = 0; i < giohang.size(); i++) {
//        int tt = giohang.get( i ).getGt() * giohang.get( i ).getSluongcay();
//        s = s + tt;
//
//        TT.setText( s + "" );
//    }
//        return s;
//    }

//        // Nhận Intent từ activity chuyển qua
//        Intent it = getIntent();
//        // Lấy ra dữ liệu từ Intent
//        String MaHD = it.getStringExtra(Danhsachhoadon.MaB);
//        mah.setText(MaHD);
//        try {
//            HienThiCTHDN(MaHD);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//
//    //   Đọc dữ liệu cho arrayList CTHDN
//
//    public ArrayList<cay> caycanh(String maHD) throws IOException {
//        db.copyDB2SDCard();
//        Cursor c = db.getCursor("SELECT * FROM hoadon WHERE Mahd='" + maHD + "' ");
//        c.moveToFirst();
//        do {
//            String maSP = c.getString(1);
//            int solg = c.getInt(2);
//            int giaB = c.getInt(3);
//            String ten= LayTen(maSP);
//            giohang.add(new cay(maSP,ten, giaB,solg));
//        } while (c.moveToNext());
//        return giohang;
//    }
//
//    // Ghi dữ liệu lên ListView
//    public void HienThiCTHDN(String mahd) throws IOException {
//        giohang =caycanh(mahd);
//        adapter = new adaptechitiethoadon(this, R.layout.item_hd, giohang);
//
//        lv.setAdapter(adapter);
//    }
//    public String LayTen(String ma){
//        String ten=" ";
//        db.copyDB2SDCard();
//
//        Cursor c= db.getCursor("SELECT Tencay from caycanh where Macay='"+ma+"'");
//        c.moveToFirst();
//        ten=c.getString(0);
//        return ten;
   }

        //back tren ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)

            finish();
        return super.onOptionsItemSelected( item );
    }

}


