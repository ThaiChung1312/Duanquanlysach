package vn.edu.poly.duanquanlysach.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.duanquanlysach.R;
import vn.edu.poly.duanquanlysach.adapter.SachAdapter;
import vn.edu.poly.duanquanlysach.adapter.TopTrendAdapter;
import vn.edu.poly.duanquanlysach.model.HoaDonChiTietClass;
import vn.edu.poly.duanquanlysach.model.Sachclass;
import vn.edu.poly.duanquanlysach.sqlite.Sachsql;

public class ToptrendActivity extends AppCompatActivity {
    RecyclerView rcv_toptrend;
    TopTrendAdapter adapter;
    List<Sachclass> list = new ArrayList<>();
    LinearLayout hien;
    Sachsql sachsql;
    Button btntim,btnthang,btnnam;
    TextView titlethang;
    EditText edtseach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toptrend);
        rcv_toptrend = findViewById(R.id.rcv_toptrend);
        hien = findViewById(R.id.hien);
        titlethang = findViewById(R.id.tv_titlethang);
        btntim = findViewById(R.id.btntim);
        btnthang=findViewById(R.id.btnthang);
        btnnam=findViewById(R.id.btnnam);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Top 10 book");
        sachsql = new Sachsql(ToptrendActivity.this);

        btntim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthYearPickerDialog pd = new MonthYearPickerDialog();
                pd.setListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        btnthang.setText("" + month);
                        btnnam.setText(year + "");
                        titlethang.setText(String.valueOf(month));
                        list = sachsql.getSachTop10(String.valueOf(month));
                        LinearLayoutManager layoutManager = new LinearLayoutManager(ToptrendActivity.this);
                        adapter = new TopTrendAdapter(list, ToptrendActivity.this);
                        rcv_toptrend.setLayoutManager(layoutManager);
                        rcv_toptrend.setAdapter(adapter);
                    }
                });
                pd.show(getSupportFragmentManager(), "MonthYearPickerDialog");
            }
        });
//        btntim.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    int thang = Integer.parseInt(edtseach.getText().toString());
//                    if (thang < 1 || thang > 12) {
//                        Toast.makeText(ToptrendActivity.this, "yêu cầu nhập từ tháng 1-12", Toast.LENGTH_SHORT).show();
//
//                    } else {
//                        titlethang.setText(String.valueOf(thang));
//                        list = sachsql.getSachTop10(String.valueOf(thang));
//                        LinearLayoutManager layoutManager = new LinearLayoutManager(ToptrendActivity.this);
//                        adapter = new TopTrendAdapter(list, ToptrendActivity.this);
//                        rcv_toptrend.setLayoutManager(layoutManager);
//                        rcv_toptrend.setAdapter(adapter);
//
//                    }
//                } catch (NumberFormatException x) {
//                    Toast.makeText(ToptrendActivity.this, "nhập đúng định dạng số từ 1-12", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });


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
