package vn.edu.poly.duanquanlysach.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.duanquanlysach.R;
import vn.edu.poly.duanquanlysach.adapter.SachAdapter;
import vn.edu.poly.duanquanlysach.model.ItemClickSupport;
import vn.edu.poly.duanquanlysach.model.Sachclass;
import vn.edu.poly.duanquanlysach.model.Theloaiclass;
import vn.edu.poly.duanquanlysach.sqlite.Sachsql;
import vn.edu.poly.duanquanlysach.sqlite.Theloaisql;

public class SachActivity extends AppCompatActivity {
    RecyclerView rcv_sach;
    List<Sachclass> list = new ArrayList<>();
    List<Theloaiclass> list_theloai = new ArrayList<>();
    SachAdapter adapter;
    public AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sach);
        setTitle("Sách");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rcv_sach = findViewById(R.id.rcv_sach);

        // gọi sql getall

        final Sachsql sachsql = new Sachsql(SachActivity.this);
        list = sachsql.getallsach();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        adapter = new SachAdapter(list, getApplicationContext());
        rcv_sach.setLayoutManager(layoutManager);
        rcv_sach.setAdapter(adapter);

        ItemClickSupport.addTo(rcv_sach).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, final int position, View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SachActivity.this);
                View alert = LayoutInflater.from(SachActivity.this).inflate(R.layout.dialog_update_book, null);
                builder.setView(alert);
                final Sachclass sachclass = list.get(position);
                // spinner


                final Spinner spinner = alert.findViewById(R.id.spinner);
                gettl(spinner);
                spinner.setSelection(getIndexSpinner(spinner, sachclass.getMatheloai()));

                //tham chiếu
                TextView tvTitle;
                final TextInputEditText tvupMasach;
                final TextInputEditText tvupTensach;
                final TextInputEditText tvupNxb;
                final TextInputEditText tvupSoluong;
                final Button btnhuy, btnsua;
                final TextInputEditText tvupGiabia;
                btnhuy = alert.findViewById(R.id.btnhuy_update_sach);
                btnsua = alert.findViewById(R.id.btnsua_update_sach);
                tvTitle = (TextView) alert.findViewById(R.id.tv_title);
                tvupMasach = (TextInputEditText) alert.findViewById(R.id.tv_add_masach);
                tvupTensach = (TextInputEditText) alert.findViewById(R.id.tv_add_tensach);
                tvupNxb = (TextInputEditText) alert.findViewById(R.id.tv_add_nxb);
                tvupSoluong = (TextInputEditText) alert.findViewById(R.id.tv_add_soluong);
                tvupGiabia = (TextInputEditText) alert.findViewById(R.id.tv_add_giabia);
                //lấy lại du liệu
                tvTitle.setText("Sửa sách");
                tvupMasach.setEnabled(false);
                tvupTensach.setEnabled(false);
                tvupNxb.setEnabled(false);
                tvupSoluong.setEnabled(false);
                tvupGiabia.setEnabled(false);


                tvupMasach.setText(sachclass.getMasach());
                tvupTensach.setText(sachclass.getTensach());
                tvupNxb.setText(sachclass.getNxb());
                tvupSoluong.setText(sachclass.getSoluong());
                tvupGiabia.setText(sachclass.getGiabia());

                btnsua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnsua.setText("Sửa xong");
                        tvupTensach.setEnabled(true);
                        tvupNxb.setEnabled(true);
                        tvupSoluong.setEnabled(true);
                        tvupGiabia.setEnabled(true);
              btnsua.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if (tvupMasach.getText().toString().equals("") || tvupTensach.getText().toString().equals("") || tvupNxb.getText().toString().equals("") || tvupSoluong.getText().toString().equals("") || tvupGiabia.getText().toString().equals("")) {
                          Toast.makeText(SachActivity.this, "Không để rỗng", Toast.LENGTH_SHORT).show();
                          tvupMasach.requestFocus();
                      } else {

                          Sachclass sachclass1 = new Sachclass();
                          String masach = tvupMasach.getText().toString();
                          String tensach = tvupTensach.getText().toString();
                          String matheloai = spinner.getSelectedItem().toString();
                          String nxb = tvupNxb.getText().toString();
                          String soluong = tvupSoluong.getText().toString();
                          String giabia = tvupGiabia.getText().toString();
                          sachclass1.setMasach(masach);
                          sachclass1.setTensach(tensach);
                          sachclass1.setMatheloai(matheloai);
                          sachclass1.setNxb(nxb);
                          sachclass1.setSoluong(soluong);
                          sachclass1.setGiabia(giabia);
                          long result = sachsql.updatesach(sachclass1);
                          if (result > 0) {
                              Toast.makeText(SachActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                          } else {
                              Toast.makeText(SachActivity.this, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                          }
                          list = sachsql.getallsach();
                          GridLayoutManager layoutManager = new GridLayoutManager(SachActivity.this, 3);
                          adapter = new SachAdapter(list, getApplicationContext());
                          rcv_sach.setLayoutManager(layoutManager);
                          rcv_sach.setAdapter(adapter);
                          alertDialog.dismiss();
                      }

                  }
              });

                    }
                });


                btnhuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(SachActivity.this, "Hủy sửa", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                });
                ;
                builder.create();
                alertDialog = builder.show();

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

    public void open_add_sach(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View alert = LayoutInflater.from(this).inflate(R.layout.dialog_add_sach, null);
        builder.setView(alert);

        TextView tvTitle;
        final TextInputEditText tvAddMasach;
        final TextInputEditText tvAddTensach;
        final TextInputEditText tvAddNxb;
        final TextInputEditText tvAddSoluong;
        final TextInputEditText tvAddGiabia;
        tvTitle = (TextView) alert.findViewById(R.id.tv_title);
        tvAddMasach = (TextInputEditText) alert.findViewById(R.id.tv_add_masach);
        tvAddTensach = (TextInputEditText) alert.findViewById(R.id.tv_add_tensach);
        tvAddNxb = (TextInputEditText) alert.findViewById(R.id.tv_add_nxb);
        tvAddSoluong = (TextInputEditText) alert.findViewById(R.id.tv_add_soluong);
        tvAddGiabia = (TextInputEditText) alert.findViewById(R.id.tv_add_giabia);
        Button btn_add_sach = alert.findViewById(R.id.btn_add_sach);
        Button btn_huy_addsach = alert.findViewById(R.id.btn_huy_add_sach);


        // spinner
        final Spinner spinner1 = alert.findViewById(R.id.spinner1);
        //chuyền dữ liệu spiner
        gettl(spinner1);

        btn_add_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tvAddMasach.getText().toString().equals("") || tvAddTensach.getText().toString().equals("") || tvAddNxb.getText().toString().equals("") || tvAddSoluong.getText().toString().equals("") || tvAddGiabia.getText().toString().equals("")) {
                    Toast.makeText(SachActivity.this, "Không để rỗng", Toast.LENGTH_SHORT).show();
                } else {

                    String masach = tvAddMasach.getText().toString();
                    String matheloai = spinner1.getSelectedItem().toString();
                    String tensach = tvAddTensach.getText().toString();
                    String nxb = tvAddNxb.getText().toString();
                    String soluong = tvAddSoluong.getText().toString();
                    String giabia = tvAddGiabia.getText().toString();
                    Sachsql sachsql = new Sachsql(SachActivity.this);
                    Sachclass s = new Sachclass();
                    s.setMasach(masach);
                    s.setMatheloai(matheloai);
                    s.setTensach(tensach);
                    s.setNxb(nxb);
                    s.setSoluong(soluong);
                    s.setGiabia(giabia);
                    list.add(s);
                    long result = sachsql.insertsach(s);
                    if (result > 0) {
                        Toast.makeText(SachActivity.this, "Thành công", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(SachActivity.this, "Hủy thành công", Toast.LENGTH_SHORT).show();
                    }

                    list = sachsql.getallsach();
                    GridLayoutManager layoutManager = new GridLayoutManager(SachActivity.this, 3);
                    adapter = new SachAdapter(list, getApplicationContext());
                    rcv_sach.setLayoutManager(layoutManager);
                    rcv_sach.setAdapter(adapter);

                    alertDialog.dismiss();

                }
            }
        });
        btn_huy_addsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SachActivity.this, "Hủy thêm", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });


        // tạo buider
        builder.create();
        alertDialog = builder.show();

    }

    public void gettl(Spinner spinner) {
        Theloaisql theloaisql = new Theloaisql(SachActivity.this);
        list_theloai = theloaisql.getalltheloai();

        ArrayAdapter<Theloaiclass> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list_theloai);
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

}


