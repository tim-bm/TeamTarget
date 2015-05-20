package bm.com.graduationproject.teamtarget;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import bm.com.graduationproject.teamtarget.adapter.MainFragmentPageAdapter;
import bm.com.graduationproject.teamtarget.dbHelper.DBManager;
import bm.com.graduationproject.teamtarget.dbService.TaskListDBService;
import bm.com.graduationproject.teamtarget.listener.TabChangeListener;
import bm.com.graduationproject.teamtarget.listener.TabSwipeListener;
import bm.com.graduationproject.teamtarget.model.TaskList;


public class TaskListActivity extends FragmentActivity {

    private ActionBar actionBar;
    private ViewPager viewPager;

    private int projectId;
    private String projectName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        Intent intent=getIntent();
        projectId=intent.getIntExtra("projectId",-1);
        projectName=intent.getStringExtra("projectName");

        //set title
        actionBar=getActionBar();
        actionBar.setTitle(projectName);

        //get taskList from database

        TaskListDBService taskListDBService=new TaskListDBService(DBManager.getInstance(this));
        List<TaskList> taskLists=taskListDBService.getTaskListByProjectId(projectId);


        //create fragment for task list

        ArrayList<Fragment> taskListFragments=new ArrayList<Fragment>(taskLists.size());
        TaskListFragment fragment;
        for(int i=0;i<taskLists.size();i++){
            fragment=new TaskListFragment();
            fragment.setTaskListId(taskLists.get(i).getId());

            fragment.setProjectId(projectId);
            fragment.setProjectName(projectName);


            taskListFragments.add(fragment);
        }



        //test


        //set swipe animation
        viewPager=(ViewPager)findViewById(R.id.task_list_view_page);

        viewPager.setAdapter(new MainFragmentPageAdapter(getSupportFragmentManager(),taskListFragments));

        viewPager.setOnPageChangeListener(new TabChangeListener(this));

        viewPager.setCurrentItem(0);

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        //add tabs

        ActionBar.Tab tempTab;


        for(int i=0;i<taskLists.size();i++){

            tempTab=actionBar.newTab().setText(taskLists.get(i).getName()).setContentDescription("TaskList_tab"+i)
                    .setTabListener(new TabSwipeListener(viewPager));

            actionBar.addTab(tempTab);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==R.id.task_list_new){

            displayPopupWindow(id);
        }



        return super.onOptionsItemSelected(item);
    }
    private void displayPopupWindow(int id){
        View menuItemView =findViewById(id);

        PopupMenu popupMenu=new PopupMenu(this,menuItemView);

        popupMenu.setOnMenuItemClickListener(new TaskListMenuItemListener());
        popupMenu.getMenuInflater().inflate(R.menu.task_list_actions,popupMenu.getMenu());


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

    class TaskListMenuItemListener implements PopupMenu.OnMenuItemClickListener{

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            switch (item.getItemId()){

                case R.id.task_list_new_task_list:

                    //start add task list activity
                    Intent intent=new Intent(TaskListActivity.this,AddTaskListActivity.class);
                    intent.putExtra("projectId",projectId);
                    intent.putExtra("projectName",projectName);

                    startActivity(intent);

                    return true;


                default:
                return false;
            }


        }
    }
}
