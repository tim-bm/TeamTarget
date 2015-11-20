package bm.com.graduationproject.teamtarget;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bm.com.graduationproject.teamtarget.dbHelper.DBManager;
import bm.com.graduationproject.teamtarget.dbService.DiaryDBService;
import bm.com.graduationproject.teamtarget.model.Diary;


public class DiaryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        ListView diaryListView=(ListView)findViewById(R.id.diary_list);

        String[] keys={"content","date"};
        int[] ids={R.id.diary_item_content,R.id.diary_item_date};
        SimpleAdapter adapter=new SimpleAdapter(this,getList(),R.layout.array_diary_item,keys,ids);
        diaryListView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diary, menu);
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
    private List<HashMap<String,Object>> getList(){
        ArrayList<HashMap<String,Object>> list=new ArrayList<HashMap<String, Object>>();
        HashMap<String,Object> map=null;

        DiaryDBService diaryDBService=new DiaryDBService(DBManager.getInstance(this));
        List<Diary> diaries=diaryDBService.getDiaryDesc();

        for(int i=0;i<diaries.size();i++){
            map = new HashMap<String, Object>();
            map.put("content",diaries.get(i).getContent());
            map.put("date",diaries.get(i).getDate());
            list.add(map);
        }

        return list;
    }
}
