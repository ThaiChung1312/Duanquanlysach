package vn.edu.poly.duanquanlysach.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Viewpageadapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList=new ArrayList<>();
    List<String> titlefm=new ArrayList<>();

    public Viewpageadapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titlefm.get(position);
    }
    public void addtab(Fragment fragment, String t){
        fragmentList.add(fragment);
        titlefm.add(t);
    }
}
