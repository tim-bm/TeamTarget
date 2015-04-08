package bm.com.graduationproject.teamtarget;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import bm.com.graduationproject.teamtarget.listener.TabListener;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar=getActionBar();
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

        actionBar.addTab(tab);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
