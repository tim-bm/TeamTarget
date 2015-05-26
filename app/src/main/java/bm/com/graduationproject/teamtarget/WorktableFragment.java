package bm.com.graduationproject.teamtarget;

//import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bm.com.graduationproject.teamtarget.dbHelper.DBManager;
import bm.com.graduationproject.teamtarget.dbService.TaskDBService;
import bm.com.graduationproject.teamtarget.model.Task;
import bm.com.graduationproject.teamtarget.utils.AppContext;

/**
 * Created by bm on 2015/4/6.
 */
public class WorktableFragment extends Fragment {

    private ArrayList<HashMap<String,Object>> todayTaskList;
    private ArrayList<HashMap<String,Object>> todayScheduleList;
    private SimpleAdapter todayTaskListAdapter;
    private SimpleAdapter todayScheduleListAdapter;

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        rootView =inflater.inflate(R.layout.worktable,null);


        ListView todayTaskListView=(ListView)rootView.findViewById(R.id.worktable_today_task);

        String[] strKeys={"title","time","id"};
        int[] ids={R.id.checkbox_today_task,R.id.today_task_time,R.id.today_my_task_id};

        todayTaskListAdapter=new SimpleAdapter
                (rootView.getContext(),getList(),R.layout.array_worktable_today_task,strKeys,ids);
        todayTaskListView.setAdapter(todayTaskListAdapter);

        todayTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView idTextView=(TextView)view.findViewById(R.id.today_my_task_id);
                int id=Integer.parseInt(idTextView.getText().toString());
                Intent intent=new Intent(rootView.getContext(),TaskActivity.class);
                intent.putExtra("taskId",id);
                intent.putExtra("backToMain",1);

                startActivity(intent);

            }
        });

        //my task button

        RelativeLayout myTask=(RelativeLayout)rootView.findViewById(R.id.worktable_my_task);
        myTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(rootView.getContext(),MyTaskActivity.class);
                startActivity(intent);

            }
        });

        //my schedule button
        RelativeLayout mySchedule=(RelativeLayout)rootView.findViewById(R.id.worktable_my_schedule);
        mySchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(rootView.getContext(),MyScheduleActivity.class);
                startActivity(intent);
            }
        });

        return rootView;


    }

    private List<HashMap<String,Object>> getList(){

        ArrayList<HashMap<String,Object>> list=new ArrayList<HashMap<String, Object>>(1);
        HashMap<String,Object> map=null;

        TaskDBService taskDBService=new TaskDBService(DBManager.getInstance(rootView.getContext()));
        List<Task> recentTasks=taskDBService.getLatestTwoTask();

        for(int i=0;i<2;i++){

            map = new HashMap<String, Object>();
            map.put("title",recentTasks.get(i).getName());
            map.put("time",recentTasks.get(i).getDeadline());
            map.put("id",recentTasks.get(i).getId());
            list.add(map);
        }

        return list;

    }
}
