package vn.edu.poly.duanquanlysach.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.duanquanlysach.R;
import vn.edu.poly.duanquanlysach.adapter.NguoiDungAdapter;
import vn.edu.poly.duanquanlysach.adapter.TheLoaiAdapter;
import vn.edu.poly.duanquanlysach.model.Nguoidungclass;
import vn.edu.poly.duanquanlysach.model.Theloaiclass;
import vn.edu.poly.duanquanlysach.sqlite.Nguoidungsql;
import vn.edu.poly.duanquanlysach.sqlite.Theloaisql;

public class TheloaiActivity extends AppCompatActivity {
    RecyclerView rcv_theloai;
    List<Theloaiclass> list = new ArrayList<>();
    TheLoaiAdapter adapter;
    Theloaisql theloaisql;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theloai);
        setTitle("Thể loại sách");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rcv_theloai = findViewById(R.id.rcv_theloai);
        theloaisql = new Theloaisql(TheloaiActivity.this);
        list = theloaisql.getalltheloai();
        Log.e("so luong", String.valueOf(list.size()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcv_theloai.setLayoutManager(layoutManager);
        adapter = new TheLoaiAdapter(list, TheloaiActivity.this);
        rcv_theloai.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuuse, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
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


    public void open_add_theloai(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View alert = LayoutInflater.from(this).inflate(R.layout.dialog_add_theloai, null);
        theloaisql = new Theloaisql(TheloaiActivity.this);
        //tham chiếu


        TextView tvTitleTheloai;
        final TextInputEditText tvUpdateMatheloai;
        final TextInputEditText tvUpdateTentheloai;
        final TextInputEditText tvUpdateMota;
        final TextInputEditText tvUpdateVitri;
        Button btnAddTheloai;
        Button btnHuyaddTheloai;

        tvTitleTheloai = (TextView) alert.findViewById(R.id.tv_title_theloai);
        tvUpdateMatheloai = (TextInputEditText) alert.findViewById(R.id.tv_update_matheloai);
        tvUpdateTentheloai = (TextInputEditText) alert.findViewById(R.id.tv_update_tentheloai);
        tvUpdateMota = (TextInputEditText) alert.findViewById(R.id.tv_update_mota);
        tvUpdateVitri = (TextInputEditText) alert.findViewById(R.id.tv_update_vitri);
        btnAddTheloai = (Button) alert.findViewById(R.id.btn_add_theloai);
        btnHuyaddTheloai = (Button) alert.findViewById(R.id.btn_huyadd_theloai);
        tvTitleTheloai.setText("Thêm thể loại");


        btnAddTheloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tvUpdateMatheloai.getText().toString().equals("") || tvUpdateTentheloai.getText().toString().equals("") || tvUpdateMota.getText().toString().equals("") || tvUpdateVitri.getText().toString().equals("")) {
                    Toast.makeText(TheloaiActivity.this, "không để rỗng", Toast.LENGTH_SHORT).show();
                } else if (tvUpdateVitri.getText().toString().equals("A") || tvUpdateVitri.getText().toString().equals("B") || tvUpdateVitri.getText().toString().equals("C")) {
                    Theloaiclass theloaiclass = new Theloaiclass();
                    theloaiclass.matheloai = tvUpdateMatheloai.getText().toString();
                    theloaiclass.tentheloai = tvUpdateTentheloai.getText().toString();
                    theloaiclass.mota = tvUpdateMota.getText().toString();
                    theloaiclass.vitri = tvUpdateVitri.getText().toString();

                    long result = theloaisql.inserttheloai(theloaiclass);

                    list = theloaisql.getalltheloai();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(TheloaiActivity.this);
                    rcv_theloai.setLayoutManager(layoutManager);
                    rcv_theloai.setAdapter(adapter);
                    rcv_theloai.setHasFixedSize(true);
                    adapter = new TheLoaiAdapter(list, TheloaiActivity.this);
                    rcv_theloai.setAdapter(adapter);

                    if (result > 0) {
                        Toast.makeText(TheloaiActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(TheloaiActivity.this, "Thêm không thành công", Toast.LENGTH_SHORT).show();

                    }
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(TheloaiActivity.this, "Vui lòng nhập vị trí A, B, C (Viết hoa)", Toast.LENGTH_SHORT).show();
                }
            }


        });


        btnHuyaddTheloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        builder.setView(alert);
        builder.create();
        alertDialog = builder.show();
    }
}
