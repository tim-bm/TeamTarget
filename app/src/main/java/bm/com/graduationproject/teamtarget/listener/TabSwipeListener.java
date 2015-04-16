package bm.com.graduationproject.teamtarget.listener;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

/**
 * Created by bm on 2015/4/16.
 */
public class TabSwipeListener implements ActionBar.TabListener {

    private ViewPager viewPager;

    public TabSwipeListener(ViewPager viewPager){

        this.viewPager=viewPager;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        if(viewPager!=null){
            viewPager.setCurrentItem(tab.getPosition());
        }

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
