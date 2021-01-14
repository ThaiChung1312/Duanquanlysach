package vn.edu.poly.duanquanlysach.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

import vn.edu.poly.duanquanlysach.R;
import vn.edu.poly.duanquanlysach.activity.fragment.Ngay;
import vn.edu.poly.duanquanlysach.activity.fragment.Thang;
import vn.edu.poly.duanquanlysach.activity.fragment.Tuan;
import vn.edu.poly.duanquanlysach.adapter.Viewpageadapter;

public class ThongkeActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke);
        setTitle("Thống kê");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabLayout=findViewById(R.id.tab1);
        viewPager=findViewById(R.id.view1);
        addtab(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }
    public void addtab(ViewPager viewPager){
        Viewpageadapter adapter=new Viewpageadapter(getSupportFragmentManager());
        adapter.addtab(new Ngay(),"Ngày");
        adapter.addtab(new Tuan(),"Tuần");
        adapter.addtab(new Thang(),"Tháng");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
                default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}
