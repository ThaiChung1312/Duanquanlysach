package vn.edu.poly.duanquanlysach.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import vn.edu.poly.duanquanlysach.R;

public class FirstActivity extends AppCompatActivity {

    private ImageView imgFrist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first2);
        imgFrist=findViewById(R.id.imgFirst);

        imgFrist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FirstActivity.this, DangnhapActivity.class);
                startActivity(intent);
            }
        });
    }
}
