package vn.edu.poly.duanquanlysach.base;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    public void openActivity(Class target){
        Intent intent=new Intent(getApplicationContext(),target);
        startActivity(intent);
    }
}
