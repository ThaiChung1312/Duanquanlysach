package vn.edu.poly.duanquanlysach.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import vn.edu.poly.duanquanlysach.R;
import vn.edu.poly.duanquanlysach.sqlite.Hdctsqlite;

public class TongKetActivity extends AppCompatActivity {
    Button btnngay;

    TextView tvTheongay;
    TextView tvTheonam, tvTheothang;
    Hdctsqlite hdctsqlite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tong_ket);
        setTitle("Thống kê");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        hdctsqlite = new Hdctsqlite(TongKetActivity.this);
        btnngay = (Button) findViewById(R.id.btnngay);
        tvTheothang = findViewById(R.id.tv_theothang);
        tvTheonam = findViewById(R.id.tv_theonam);
        tvTheongay = (TextView) findViewById(R.id.tv_theongay);
//ngay gio hiện tại
        btnngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        btnngay.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        tvTheongay.setText(String.valueOf(hdctsqlite.getDoanhThuTheoNgay(btnngay.getText().toString())));
                        tvTheothang.setText(String.valueOf(hdctsqlite.getDoanhThuTheoThang((month + 1) + "/" + year)));
                        tvTheonam.setText(String.valueOf(hdctsqlite.getDoanhThuTheoNam(String.valueOf(year))));
                    }
                }, year, month, day);


                dialog.show();
            }
        });


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
