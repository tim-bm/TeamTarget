package bm.com.graduationproject.teamtarget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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


    private View rootView;


    //projectId and name for back to activity
    private int projectId;
    private String projectName;

    private int taskId;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         rootView=inflater.inflate(R.layout.fragment_task_list,null);

        //test
//        TextView t=(TextView)rootView.findViewById(R.id.test_aaa);
//        t.setText("This is fragment____"+taskListId);


        ListView tasksListView=(ListView)rootView.findViewById(R.id.task_list);

        TaskDBService taskDBService=new TaskDBService(DBManager.getInstance(rootView.getContext()));

        tasks=taskDBService.getTasksByTaskListId(taskListId);


        adapter=new TaskListAdapter(rootView.getContext(),tasks);
        adapter.notifyDataSetChanged();

        tasksListView.setAdapter(adapter);

        //set on item click listener
        tasksListView.setOnItemClickListener(new tasksListItemClickListener());

        return rootView;
    }


    class tasksListItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            int taskId;
            TextView taskIdTextView=(TextView)view.findViewById(R.id.task_id);


            Intent intent=new Intent(rootView.getContext(),TaskActivity.class);
            intent.putExtra("projectId",projectId);
            intent.putExtra("projectName",projectName);
            intent.putExtra("taskId",Integer.parseInt(taskIdTextView.getText().toString()));

            startActivity(intent);
        }
    }

    public int getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(int taskListId) {
        this.taskListId = taskListId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
