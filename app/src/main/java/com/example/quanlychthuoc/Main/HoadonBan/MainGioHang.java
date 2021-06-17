package com.example.quanlychthuoc.Main.HoadonBan;

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

import com.example.quanlychthuoc.Adapter.SanPhamAdapter;
import com.example.quanlychthuoc.DatabaseHandler;
import com.example.quanlychthuoc.Main.Sanpham.Trangchu;
import com.example.quanlychthuoc.R;
import com.example.quanlychthuoc.Class.SanPham;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainGioHang extends AppCompatActivity {

    EditText tvdate;

    Button btthem, huy, btTT;

    TextView sotien;
    ListView lvgianhang;
    Integer Thanhtoan = 0;

    ArrayList<SanPham> SanPhams = new ArrayList<>();
    public static ArrayList<SanPham> sp = new ArrayList<>();
    DatabaseHandler db = new DatabaseHandler(this);
    ArrayAdapter<SanPham> adap = null;

    EditText edtMaHD;
    EditText tvTenKH, tvDiaChi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoadon);
        tvdate = (EditText) findViewById(R.id.tvngay);
        btthem = (Button) findViewById(R.id.btthemSP);
        tvTenKH = (EditText) findViewById(R.id.tvTenKH);
        tvDiaChi = (EditText) findViewById(R.id.tvDiaChi);
        edtMaHD = (EditText) findViewById(R.id.mahd);
        btTT = (Button) findViewById(R.id.btThanhtoan);
        lvgianhang = (ListView) findViewById(R.id.lv);
        sotien = (TextView) findViewById(R.id.tvtien);
        huy = findViewById(R.id.huy);
        ThanhToan();
        getspItem();

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainGioHang.this, Trangchu.class);
                startActivity(intent);
            }
        });
        tvdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonngay();
            }
        });
        btthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it1 = new Intent(MainGioHang.this, item_hang.class);
                startActivity(it1);
                finish();
            }
        });
    }

    public void getData(ArrayList<SanPham> x) {
        sp.addAll(x);
        for (int i = 0; i < x.size(); i++) {
            System.out.println(x.get(i).getSluongcay());

        }
    }

    public void ThanhToan() {
        btTT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for (int i = 0; i < sp.size(); i++) {
                    System.out.println("" + sp.get(i).sluongthuoc + "/" + sp.get(i).sl);
                    int sl = sp.get(i).sl - sp.get(i).sluongthuoc;
                    db.executeSQL("update sanpham set soluong = " + (sp.get(i).sl - sp.get(i).sluongthuoc) + " Where masp ='" + sp.get(i).Masp + "'");
                    db.executeSQL("insert into hoadon values ('" + edtMaHD.getText().toString() + "','" + sp.get(i).Masp + "','" + sp.get(i).Tensp + "','" + sp.get(i).gt + "'," + sp.get(i).sl + " )");
                }

                addhoadon();
                finish();
            }
        });
    }

    public void db2ListView() {
        sp = new ArrayList<SanPham>();
        SanPham row;
        Cursor cursor = db.getCursor("SELECT * FROM sanpham");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            row = new SanPham();
            row.Masp = cursor.getString(0);
            row.Tensp = cursor.getString(1);
            row.DD = cursor.getString(2);
            row.sl = cursor.getInt(3);
            row.gt = cursor.getInt(4);

            sp.add(row);
            cursor.moveToNext();
        }
    }

    private void getspItem() {

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String cayItems = extras.getString("spItems", null);
            if (cayItems != null && cayItems.length() > 0) {
                try {
                    JSONArray giohang = new JSONArray(cayItems);
                    for (int i = 0; i < giohang.length(); i++) {
                        JSONObject jsonObject = new JSONObject(giohang.getString(i));

                        SanPham c = new SanPham(
                                jsonObject.getString("Masp"),
                                jsonObject.getString("Tensp"),
                                jsonObject.getInt("GiaBan"),
                                //  jsonObject.getInt( "sl" ),
                                jsonObject.getInt("Giatri")
                        );
                        Thanhtoan = Thanhtoan + (c.sluongthuoc * c.gt);
                        System.out.println(c.getTensp());
                        SanPhams.add(c);
                    }

                    if (SanPhams.size() > 0) {
                        DecimalFormat formatter = new DecimalFormat("###,###,###");
                        //adap = new SanPhamAdapter(this, android.R.layout.simple_list_item_1, SanPhams);
                        adap = new SanPhamAdapter( this, R.layout.item_sanpham, SanPhams );

                        lvgianhang.setAdapter(adap);
                        adap.notifyDataSetChanged();
                        sotien.setText(formatter.format(Thanhtoan) + " VND");

                    } else {
                        showMessage("Chưa có mặt hàng nào!");
                    }
                } catch (Exception e) {
                    showMessage(e.toString());
                }
            }

        }
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void chonngay() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int nam, int thang, int ngay) {
                calendar.set(nam, thang, ngay);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
                tvdate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();

    }

    public void addhoadon() {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        String mah = edtMaHD.getText().toString().trim();
        String tenkh = tvTenKH.getText().toString();
        String dc = tvDiaChi.getText().toString();
        String date = tvdate.getText().toString();

        if (mah.length() == 0) {
            Toast.makeText(this, "Bạn chưa nhập dữ liệu!", Toast.LENGTH_SHORT).show();
        } else {
            // Kiểm tra sự tồn tại của mã sản phẩm
            int checkma = db.GetCount("Select * from hoadonban where Mahd=\"" + mah + "\"");
            if (checkma == 1) {
                // Nếu thẻ đã tồn tại đưa ra thông báo
                AlertDialog.Builder al = new AlertDialog.Builder(this);
                al.setTitle(" Thông báo ");
                al.setMessage("Hóa đơn đã tồn tại ! ");
                al.create().show();
            } else if (checkma == 0) {
                if (tenkh.length() == 0) {
                    Toast.makeText(this, "Nhập dữ liệu", Toast.LENGTH_SHORT).show();
                } else if (dc.length() == 0) {
                    Toast.makeText(this, "Nhập dữ liệu", Toast.LENGTH_SHORT).show();
                } else if (date.length() == 0) {
                    Toast.makeText(this, "Nhập dữ liệu", Toast.LENGTH_SHORT).show();

                } else {
                    // Thêm dữ liệu vào database
                    db.executeSQL("insert into hoadonban values ('" + edtMaHD.getText() + "','" + tvTenKH.getText() + "','" + tvDiaChi.getText() + "','" + tvdate.getText() + "'," + Thanhtoan + " )");
                    Toast.makeText(MainGioHang.this, "Đã thêm hóa đơn", Toast.LENGTH_SHORT).show();
                    Intent back = new Intent(MainGioHang.this, Danhsachhoadon.class);
                    startActivity(back);
                    finish();
                }
            }
        }
    }
}



