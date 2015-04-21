package bm.com.graduationproject.teamtarget;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class NewProjectActivity extends Activity {

    private ArrayList<HashMap<String,Object>> newItemList;
    private SimpleAdapter newItemListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);
        getActionBar().setIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));


        ListView newItemListView=(ListView)findViewById(R.id.new_list_item_selected);

        String[] strKeys={"icon","hint","selected"};
        int[] ids={R.id.array_new_icon,R.id.array_hint,R.id.array_selected};
        newItemListAdapter=new SimpleAdapter(this,getList(),R.layout.array_new_item,strKeys,ids);

        newItemListView.setAdapter(newItemListAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_project, menu);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

           //animation
        if(keyCode==KeyEvent.KEYCODE_BACK){

            this.finish();
            overridePendingTransition(R.anim.stand_still, R.anim.pull_down_out);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private List<HashMap<String,Object>> getList(){

        ArrayList<HashMap<String,Object>> list=new ArrayList<HashMap<String, Object>>(2);
        HashMap<String,Object> map=null;

        for(int i=0;i<2;i++){
            map = new HashMap<String, Object>();
            map.put("icon",R.drawable.icon_bruch);
            map.put("hint","所有者");
            map.put("selected","公开项目");

            list.add(map);
        }

        return list;
    }
}
