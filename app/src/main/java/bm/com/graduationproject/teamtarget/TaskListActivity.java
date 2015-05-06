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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        Intent intent=getIntent();
        int projectId=intent.getIntExtra("projectId",-1);
        String projectName=intent.getStringExtra("projectName");

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

        return super.onOptionsItemSelected(item);
    }
}
