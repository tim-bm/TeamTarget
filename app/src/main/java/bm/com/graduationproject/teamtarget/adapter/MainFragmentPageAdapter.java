package bm.com.graduationproject.teamtarget.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by bm on 2015/4/16.
 */
public class MainFragmentPageAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentList;

    public MainFragmentPageAdapter(FragmentManager fm,ArrayList<Fragment> fragmentList) {
        super(fm);
        this.fragmentList=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}

