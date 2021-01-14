package vn.edu.poly.duanquanlysach.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.edu.poly.duanquanlysach.R;
import vn.edu.poly.duanquanlysach.adapter.HoaDonAdapter;
import vn.edu.poly.duanquanlysach.model.Hoadonclass;
import vn.edu.poly.duanquanlysach.model.ItemClickSupport;
import vn.edu.poly.duanquanlysach.model.Nguoidungclass;
import vn.edu.poly.duanquanlysach.sqlite.Hoadonsql;

public class HoadonActivity extends AppCompatActivity {
    RecyclerView rcv_hoadon;
    List<Hoadonclass> list;
    HoaDonAdapter adapter;
    Hoadonsql hoadonsql;
    private AlertDialog alertDialog;
    ItemClickSupport itemClickSupport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoadon);
        setTitle("Hóa đơn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rcv_hoadon = findViewById(R.id.rcv_hoadon);
        list = new ArrayList<>();
        hoadonsql = new Hoadonsql(HoadonActivity.this);
        list = hoadonsql.getallHoaDon();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcv_hoadon.setLayoutManager(layoutManager);
        adapter = new HoaDonAdapter(list, getApplicationContext());
        rcv_hoadon.setAdapter(adapter);
        itemClickSupport.addTo(rcv_hoadon).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, final int position, View v) {
                Intent intent = new Intent(HoadonActivity.this, HDCTActivity.class);
                Hoadonclass hoadonclass=list.get(position);
                intent.putExtra("mahoadon",hoadonclass.getMahoadon());
                intent.putExtra("ngaymua",hoadonclass.getNgaymuahang());
                startActivity(intent);


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

    public void open_add_hoadon(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View alert = LayoutInflater.from(this).inflate(R.layout.dialog_vd, null);
        TextView tvNameDialog;
        final TextInputEditText edtMahoadon;

        TextView imgDate;
        final TextView tvDate;
        Button btnthemHoadon;
        Button btnhuyHoadon;
        tvNameDialog = (TextView) alert.findViewById(R.id.tv_name_dialog);

        edtMahoadon = (TextInputEditText) alert.findViewById(R.id.edt_mahoadon);
        imgDate = alert.findViewById(R.id.img_date);
        tvDate = (TextView) alert.findViewById(R.id.tv_date);
        btnthemHoadon = (Button) alert.findViewById(R.id.btnthem_hoadon);
        btnhuyHoadon = (Button) alert.findViewById(R.id.btnhuy_hoadon);
        imgDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HoadonActivity.this, "chọn ngày", Toast.LENGTH_SHORT).show();
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(HoadonActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Toast.makeText(HoadonActivity.this, year + ":" + (month + 1) + ":" + dayOfMonth, Toast.LENGTH_SHORT).show();
                        tvDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                dialog.show();
            }
        });
        btnthemHoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mhd = edtMahoadon.getText().toString();
                String date = tvDate.getText().toString();
                Hoadonclass hoadonclass = new Hoadonclass();
                hoadonclass.mahoadon = mhd;
                hoadonclass.ngaymuahang = date;
                long result = hoadonsql.insertHoaDon(hoadonclass);
                if (result > 0) {
                    Toast.makeText(getApplicationContext(), "thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "không thành công", Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
                list = hoadonsql.getallHoaDon();
                LinearLayoutManager layoutManager = new LinearLayoutManager(HoadonActivity.this);
                rcv_hoadon.setLayoutManager(layoutManager);
                adapter = new HoaDonAdapter(list, getApplicationContext());
                rcv_hoadon.setAdapter(adapter);
                alertDialog.dismiss();
            }
        });
        btnhuyHoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "hủy thành công", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
        builder.setView(alert);
        builder.create();
        alertDialog = builder.show();
    }
}
