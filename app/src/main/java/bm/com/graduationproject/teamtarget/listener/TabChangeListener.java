package bm.com.graduationproject.teamtarget.listener;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v4.view.ViewPager;

/**
 * Created by bm on 2015/4/16.
 */
public class TabChangeListener implements ViewPager.OnPageChangeListener {
    private ActionBar actionBar;


    public TabChangeListener(Activity activity) {

        this.actionBar = activity.getActionBar();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    @Override
    public void onPageSelected(int position) {

        actionBar.selectTab(actionBar.getTabAt(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
