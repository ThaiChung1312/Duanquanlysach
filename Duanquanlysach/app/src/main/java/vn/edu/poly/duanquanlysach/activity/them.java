package vn.edu.poly.duanquanlysach.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import vn.edu.poly.duanquanlysach.R;

public class them extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_nguoidung);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

}
