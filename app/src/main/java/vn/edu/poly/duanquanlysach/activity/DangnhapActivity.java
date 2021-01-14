package vn.edu.poly.duanquanlysach.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import vn.edu.poly.duanquanlysach.R;
import vn.edu.poly.duanquanlysach.base.BaseActivity;
import vn.edu.poly.duanquanlysach.sqlite.Nguoidungsql;

public class DangnhapActivity extends AppCompatActivity  implements TextWatcher , CompoundButton.OnCheckedChangeListener{
    public EditText edttk;
    public EditText edtmk;
    public CheckBox chkpass;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";
    Nguoidungsql nguoidungsql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);


        initView();
        nguoidungsql = new Nguoidungsql(DangnhapActivity.this);
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        chkpass = (CheckBox) findViewById(R.id.chkpass);

        if (sharedPreferences.getBoolean(KEY_REMEMBER, false))
            chkpass.setChecked(true);
        else
            chkpass.setChecked(false);

        edttk.setText(sharedPreferences.getString(KEY_USERNAME, ""));
        edtmk.setText(sharedPreferences.getString(KEY_PASS, ""));

        edttk.addTextChangedListener((TextWatcher) DangnhapActivity.this);
        edtmk.addTextChangedListener((TextWatcher) this);
        chkpass.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);

    }

    public void dangnhap(View view) {
        String tk = edttk.getText().toString().trim();
        String mk = edtmk.getText().toString().trim();
        if (tk.equals("") || mk.equals("")) {
            Toast.makeText(DangnhapActivity.this, "Không nhập rỗng", Toast.LENGTH_SHORT).show();
        } else if (!tk.equals("admin") || !mk.equals("admin")) {
            Toast.makeText(DangnhapActivity.this, "Nhập tài khoản và mật khẩu là admin để đăng nhập", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);
        }


    }


    public void initView() {
        edttk = (EditText) findViewById(R.id.edttk);
        edtmk = (EditText) findViewById(R.id.edtmk);
        chkpass = (CheckBox) findViewById(R.id.chkpass);


    }


    private void managePrefs() {
        if (chkpass.isChecked()) {
            editor.putString(KEY_USERNAME, edttk.getText().toString().trim());
            editor.putString(KEY_PASS, edtmk.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply();
        } else {
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_PASS);//editor.putString(KEY_PASS,"");
            editor.remove(KEY_USERNAME);//editor.putString(KEY_USERNAME, "");
            editor.apply();
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        managePrefs();

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        managePrefs();
    }
}

