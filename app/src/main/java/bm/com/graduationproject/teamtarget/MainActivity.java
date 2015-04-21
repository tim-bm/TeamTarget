package bm.com.graduationproject.teamtarget;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import bm.com.graduationproject.teamtarget.adapter.MainFragmentPageAdapter;
import bm.com.graduationproject.teamtarget.listener.TabChangeListener;
import bm.com.graduationproject.teamtarget.listener.TabListener;
import bm.com.graduationproject.teamtarget.listener.TabSwipeListener;


public class MainActivity extends FragmentActivity {

    private ActionBar actionBar;
    private ViewPager viewPager;
    private String[] tabTitles;
    private ArrayList<Fragment> mainFragmentList;
    private Tab tab1,tab2,tab3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        actionBar=getActionBar();
        mainFragmentList=new ArrayList<Fragment>(3);
        mainFragmentList.add(new WorktableFragment());
        mainFragmentList.add(new ProjectFragment());
        mainFragmentList.add(new MessageFragment());

        viewPager=(ViewPager)findViewById(R.id.main_view_page);

        viewPager.setAdapter(new MainFragmentPageAdapter(getSupportFragmentManager(),mainFragmentList));

        viewPager.setOnPageChangeListener(new TabChangeListener(this));

        viewPager.setCurrentItem(0);

      //  tabTitles =new String[] {"工作台","项目","消息"};

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        tab1=actionBar.newTab().setText(R.string.tab1).setContentDescription("tab1")
                .setTabListener(new TabSwipeListener(viewPager));
        actionBar.addTab(tab1);

        tab2=actionBar.newTab().setText(R.string.tab2).setContentDescription("tab2")
                .setTabListener(new TabSwipeListener(viewPager));
        actionBar.addTab(tab2);

        tab3=actionBar.newTab().setText(R.string.tab3).setContentDescription("tab3")
                .setTabListener(new TabSwipeListener(viewPager));
        actionBar.addTab(tab3);

        /*ActionBar actionBar=getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);

        Tab tab=actionBar.newTab()
                .setText(R.string.tab1)
                .setTabListener(new TabListener<WorktableFragment>(this,"worktable",WorktableFragment.class));

        actionBar.addTab(tab);

        tab=actionBar.newTab()
                .setText(R.string.tab2)
                .setTabListener(new TabListener<ProjectFragment>(this,"project",ProjectFragment.class));

        actionBar.addTab(tab);


        tab=actionBar.newTab()
                .setText(R.string.tab3)
                .setTabListener(new TabListener<MessageFragment>(this,"message",MessageFragment.class));

        actionBar.addTab(tab);*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){

            case(R.id.action_new):

           displayPopupWindow(id);

            default:
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayPopupWindow(int selectedItem){

        View menuItemView =findViewById(selectedItem);

        PopupMenu popupMenu=new PopupMenu(this,menuItemView);

        popupMenu.setOnMenuItemClickListener(new innerMenuItemListener());
        popupMenu.getMenuInflater()
                .inflate(R.menu.main_activity_actions_new_item, popupMenu.getMenu());

        // use reflect to show popupMenu item

        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        popupMenu.show();
    }


    // inner class for popup menu item when clicked
    class innerMenuItemListener implements PopupMenu.OnMenuItemClickListener{


        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){

                case R.id.new_project:

                    newProject();

                    return true;

                case R.id.new_task:

                    return  true;

                case R.id.new_schedule:
                    return true;

                default:
                    return false;
            }
        }
    }


    private void newProject(){

        startActivity(new Intent(this,NewProjectActivity.class));
       // animation
      overridePendingTransition(R.anim.push_up_in,R.anim.stand_still);
       // overridePendingTransition(android.R.anim.slide_out_right,android.R.anim.slide_in_left);
    }
}
