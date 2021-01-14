package vn.edu.poly.duanquanlysach.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.duanquanlysach.R;
import vn.edu.poly.duanquanlysach.adapter.HoaDonChiTietAdapter;
import vn.edu.poly.duanquanlysach.adapter.TopTrendAdapter;
import vn.edu.poly.duanquanlysach.model.HoaDonChiTietClass;
import vn.edu.poly.duanquanlysach.model.Hoadonclass;
import vn.edu.poly.duanquanlysach.model.Sachclass;
import vn.edu.poly.duanquanlysach.model.Theloaiclass;
import vn.edu.poly.duanquanlysach.sqlite.Hdctsqlite;
import vn.edu.poly.duanquanlysach.sqlite.Hoadonsql;
import vn.edu.poly.duanquanlysach.sqlite.Sachsql;
import vn.edu.poly.duanquanlysach.sqlite.Theloaisql;

public class HDCTActivity extends AppCompatActivity {
    TextView tv_mahoadon, tv_tongtien;
    List<HoaDonChiTietClass> list;
    List<Sachclass> list_masach = new ArrayList<>();
    TextView tv_ngaymua;
    RecyclerView rcv_hdct;
    HoaDonChiTietAdapter adapter;
    Hdctsqlite hdctDAO;
    public String mahoadon, ngaymua;
    private AlertDialog alertDialog;
    Sachsql sachsql;
    TextView tv_count;
    List<Sachclass> list_sl = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hdct);

        Intent intent = getIntent();
        mahoadon = intent.getStringExtra("mahoadon");
        ngaymua = intent.getStringExtra("ngaymua");
        innitView();
        tv_mahoadon.setText(mahoadon);
        tv_ngaymua.setText(ngaymua);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Hóa đơn chi tiết");


        list = new ArrayList<>();
        sachsql = new Sachsql(HDCTActivity.this);
        hdctDAO = new Hdctsqlite(HDCTActivity.this);
        list = hdctDAO.getAllHoaDonChiTietByID(mahoadon);

        if (list.size() < 1) {
            Toast.makeText(this, "ds trống", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "ds có " + list.size(), Toast.LENGTH_SHORT).show();
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new HoaDonChiTietAdapter(list, HDCTActivity.this);
        rcv_hdct.setLayoutManager(layoutManager);
        rcv_hdct.setAdapter(adapter);


    }

    public void countHDCT() {

        list = new ArrayList<>();
        hdctDAO = new Hdctsqlite(HDCTActivity.this);
        list = hdctDAO.getAllHoaDonChiTietByID(mahoadon);

    }

    public void innitView() {
        rcv_hdct = findViewById(R.id.rcv_hdct);
        tv_mahoadon = findViewById(R.id.tv_tv1);
        tv_ngaymua = findViewById(R.id.tv_tv2);


    }

    public void gett2(Spinner spinner) {
        list_masach = sachsql.getallsach();
        ArrayAdapter<Sachclass> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list_masach);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    private int getIndexSpinner(Spinner spinner, String s) {
        for (int j = 0; j < spinner.getCount(); j++) {
            if (spinner.getItemAtPosition(j).toString().equalsIgnoreCase(s)) {
                return j;
            }
        }
        return 0;
    }

    public void themHdct(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(HDCTActivity.this);
        View alert = LayoutInflater.from(this).inflate(R.layout.dialog_add_hdct, null);
        builder.setView(alert);

        TextView tvNameDialog;
        final TextInputEditText tvAddMahdct;
        final TextInputEditText tvAddMasachhdct;
        final TextInputEditText tvAddSlhdct;
        final Spinner spinner;
        Button btnadd, btnhuy;
        final TextView tvslthuc;
        spinner = alert.findViewById(R.id.spiner_masach_hdct);
        gett2(spinner);
        tvNameDialog = (TextView) alert.findViewById(R.id.tv_name_dialog);
        tvAddMahdct = (TextInputEditText) alert.findViewById(R.id.tv_add_mahdct);
        tvslthuc = alert.findViewById(R.id.tv_slthuc);
        tvAddSlhdct = (TextInputEditText) alert.findViewById(R.id.tv_add_slhdct);
        btnadd = alert.findViewById(R.id.btn_add_hdct);
        btnhuy = alert.findViewById(R.id.btn_huyadd_hdct);
        final Sachsql sachsql = new Sachsql(HDCTActivity.this);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    Sachclass sach = sachsql.getSachByID(spinner.getSelectedItem().toString());

                    if (sach != null) {

                        Hoadonclass hoaDon = new Hoadonclass(mahoadon, ngaymua);
                        tvslthuc.setText(sach.getSoluong());
                        Log.e("số lượng thực",tvslthuc.getText().toString());
                        tvslthuc.setText(sach.getSoluong());
                        Log.e("số lượng thực", tvslthuc.getText().toString());
                        int slnhap = Integer.parseInt(tvAddSlhdct.getText().toString());
                        int slthuc = Integer.parseInt(tvslthuc.getText().toString());
                        if (slnhap > slthuc) {
                            Toast.makeText(HDCTActivity.this, "Trong kho chỉ còn tối đa" + slthuc + "Quyển", Toast.LENGTH_SHORT).show();

                        }else {


                            HoaDonChiTietClass hdct = new HoaDonChiTietClass(tvAddMahdct.getText().toString(), hoaDon, sach, Integer.parseInt(tvAddSlhdct.getText().toString()));

                            int pos = checkMaSach(list, spinner.getSelectedItem().toString());

                            if (pos >= 0) {
                                int soluong = list.get(pos).getSoluong();
                                hdct.setSoluong(soluong + Integer.parseInt(tvAddSlhdct.getText().toString()));

                                list.set(pos, hdct);

                                alertDialog.dismiss();
                                Toast.makeText(HDCTActivity.this, "Đã thêm số lượng", Toast.LENGTH_SHORT).show();

//                            tvHDCTNotification.setVisibility(View.GONE);
                            } else {
                                long result = hdctDAO.inserHoaDonChiTiet(hdct);
                                if (result > 0) {
                                    Toast.makeText(HDCTActivity.this, "Đã thêm thành công", Toast.LENGTH_SHORT).show();

                                    countHDCT();
                                    adapter.notifyDataSetChanged();


                                } else {
                                    Toast.makeText(HDCTActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();

                                }

                                alertDialog.dismiss();
                            }
                            list = hdctDAO.getAllHoaDonChiTietByID(hdct.getHoadonclass().getMahoadon());
                            LinearLayoutManager layoutManager = new LinearLayoutManager(HDCTActivity.this);
                            adapter = new HoaDonChiTietAdapter(list, HDCTActivity.this);
                            rcv_hdct.setLayoutManager(layoutManager);
                            rcv_hdct.setAdapter(adapter);

                        }

                    } else {

                        tvAddMahdct.setError("Mã sách không tồn tại");
                    }

                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
            }

        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        builder.create();
        alertDialog = builder.show();
    }

    public int checkMaSach(List<HoaDonChiTietClass> lsHD, String maSach) {
        int pos = -1;
        for (int i = 0; i < lsHD.size(); i++) {
            HoaDonChiTietClass hdct = lsHD.get(i);
            if (hdct.getSachclass().getMasach().equalsIgnoreCase(maSach)) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}


