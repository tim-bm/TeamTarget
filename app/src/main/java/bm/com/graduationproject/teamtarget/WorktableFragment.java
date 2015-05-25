package bm.com.graduationproject.teamtarget;

//import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bm.com.graduationproject.teamtarget.utils.AppContext;

/**
 * Created by bm on 2015/4/6.
 */
public class WorktableFragment extends Fragment {

    private ArrayList<HashMap<String,Object>> todayTaskList;
    private ArrayList<HashMap<String,Object>> todayScheduleList;
    private SimpleAdapter todayTaskListAdapter;
    private SimpleAdapter todayScheduleListAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        final View rootView =inflater.inflate(R.layout.worktable,null);


        ListView todayTaskListView=(ListView)rootView.findViewById(R.id.worktable_today_task);

        String[] strKeys={"title","time"};
        int[] ids={R.id.checkbox_today_task,R.id.today_task_time};

        todayTaskListAdapter=new SimpleAdapter
                (rootView.getContext(),getList(),R.layout.array_worktable_today_task,strKeys,ids);
        todayTaskListView.setAdapter(todayTaskListAdapter);


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

        for(int i=0;i<1;i++){

            map = new HashMap<String, Object>();
            map.put("title","这是今天任务");
            map.put("time","4-16");
            list.add(map);
        }

        return list;

    }
}
