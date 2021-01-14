package vn.edu.poly.duanquanlysach.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import vn.edu.poly.duanquanlysach.R;
import vn.edu.poly.duanquanlysach.base.BaseActivity;

public class DangnhapActivity extends AppCompatActivity {
    public EditText edttk;
    public EditText edtmk;
    public CheckBox chkpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
  initView();

    }

    public void dangnhap(View view) {
        String name=edttk.getText().toString();
        String mk=edttk.getText().toString();

            remember(name,mk,chkpass.isChecked());
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);



    }

    public void remember(String u, String p, boolean status) {
        SharedPreferences a = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = a.edit();

        if (!status) {
            edit.clear();
        } else {
            edit.putString("USERNAME", u);
            edit.putString("PASSWORD", p);
        }
        edit.commit();
    }

    public void initView(){
        edttk = (EditText) findViewById(R.id.edttk);
        edtmk = (EditText) findViewById(R.id.edtmk);
        chkpass = (CheckBox) findViewById(R.id.chkpass);


    }




}
