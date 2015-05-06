package bm.com.graduationproject.teamtarget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import bm.com.graduationproject.teamtarget.adapter.TaskListAdapter;
import bm.com.graduationproject.teamtarget.dbHelper.DBManager;
import bm.com.graduationproject.teamtarget.dbService.TaskDBService;
import bm.com.graduationproject.teamtarget.model.Task;

/**
 * Created by bm on 2015/5/4.
 */
public class TaskListFragment extends Fragment {



    private TaskListAdapter adapter;
    private int taskListId;
    private  List<Task> tasks;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_task_list,null);

        //test
//        TextView t=(TextView)rootView.findViewById(R.id.test_aaa);
//        t.setText("This is fragment____"+taskListId);


        ListView tasksListView=(ListView)rootView.findViewById(R.id.task_list);

        TaskDBService taskDBService=new TaskDBService(DBManager.getInstance(rootView.getContext()));

        tasks=taskDBService.getTasksByTaskListId(taskListId);


        adapter=new TaskListAdapter(rootView.getContext(),tasks);
        adapter.notifyDataSetChanged();

        tasksListView.setAdapter(adapter);

        return rootView;
    }

    public int getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(int taskListId) {
        this.taskListId = taskListId;
    }
}
