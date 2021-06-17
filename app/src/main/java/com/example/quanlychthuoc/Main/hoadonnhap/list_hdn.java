package com.example.quanlychthuoc.Main.hoadonnhap;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychthuoc.DatabaseHandler;
import com.example.quanlychthuoc.Adapter.Adaptergiohang;
import com.example.quanlychthuoc.R;
import com.example.quanlychthuoc.Class.SanPham;

import org.json.JSONArray;

import java.util.ArrayList;

public class list_hdn extends AppCompatActivity {
    public static ArrayList<SanPham> listnhap;
    ArrayList<SanPham> SanPhams = new ArrayList<>();
    ArrayList<String> spItems = new ArrayList<String>();
    Adaptergiohang adapter = null;
    DatabaseHandler db = new DatabaseHandler( this );
    ListView lvhdn;
    Button btTT;
    int ps;
    EditText slcaychon;
    public static Themhoadon HDN = new Themhoadon();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_list_hdn );
        lvhdn = (ListView) findViewById( R.id.listviewhdn );
        slcaychon= (EditText)findViewById( R.id.edtgiatri ) ;
        btTT = (Button) findViewById( R.id.buttonthem );
        Hienthi();
        lvhdn.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                ps = position;
                return false;
            }
        } );
        Hienthi();
        btTT.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nhaphang();

            }
        } );

    }

    private void Nhaphang() {
        HDN.getData(listnhap);
        spItems.clear();
        SanPhams.clear();
        for (int i = 0; i < adapter.sanPhams.size(); i++) {
            if (adapter.sanPhams.get( i ).sluongthuoc > 0) {
                SanPham c = new SanPham(
                        adapter.sanPhams.get( i ).getMasp(),
                        adapter.sanPhams.get( i ).getTensp(),
                        adapter.sanPhams.get( i ).getGt(),
                        adapter.sanPhams.get( i ).getSluongcay()
                );
                SanPhams.add( c );
                spItems.add( c.getJsonObject().toString() );
            }
        }
        JSONArray jsonArray = new JSONArray( spItems );

        dulieu( jsonArray.toString() );
        tongtiencay( "Thành tiền:" + SanPhams.size() );
    }

    public void tongtiencay(String tt) {
        Toast.makeText( this, tt, Toast.LENGTH_SHORT ).show();
    }

    public void dulieu(String orderItems) {
        Intent Intent = new Intent( this, Themhoadon.class );
        Intent.putExtra( "spItems", orderItems );
        startActivity( Intent );
    }

    public void Hienthi() {
        listnhap = new ArrayList<>();
        adapter = new Adaptergiohang( this, R.layout.item_giohang, listnhap );
        Cursor cursor = db.getCursor( "select * from sanpham" );
        listnhap.clear();
        while (cursor.moveToNext()) {
            String ma = cursor.getString( 0 );
            String ten = cursor.getString( 1 );
            String dd = cursor.getString( 2 );
            Integer giaban = cursor.getInt( 3 );
            Integer sl = cursor.getInt( 4 );

            listnhap.add( new SanPham( ma, ten, dd, giaban, sl ) );
        }
        lvhdn.setAdapter( adapter );
        adapter.notifyDataSetChanged();

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu( menu, v, menuInfo );
        menu.add( 0,v.getId(),0, "Xóa" );
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle()=="Xóa"){
            Toast.makeText( this, "Xóa được cho thêm tiền", Toast.LENGTH_SHORT ).show();
        }
        return true;
    }
}
