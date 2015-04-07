package bm.com.graduationproject.teamtarget.listener;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * Created by bm on 2015/4/6.
 */
public class TabListener<T extends Fragment> implements ActionBar.TabListener {

    private Fragment fragment;
    private final Activity myActivity;
    private final String myTag;
    private final Class<T> myClass;

    public TabListener(Activity myActivity, String myTag, Class<T> myClass) {
        this.myActivity = myActivity;
        this.myTag = myTag;
        this.myClass = myClass;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

        if(fragment==null){

            fragment=Fragment.instantiate(myActivity,myClass.getName());
            ft.add(android.R.id.content,fragment,myTag);
        }else {

            ft.attach(fragment);
        }

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

        if (fragment!=null){
            ft.detach(fragment);
        }

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}