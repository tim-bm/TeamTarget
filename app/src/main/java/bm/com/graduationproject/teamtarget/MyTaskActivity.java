package bm.com.graduationproject.teamtarget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import bm.com.graduationproject.teamtarget.adapter.MyTaskListAdapter;
import bm.com.graduationproject.teamtarget.dbHelper.DBManager;
import bm.com.graduationproject.teamtarget.dbService.CommentDBService;
import bm.com.graduationproject.teamtarget.dbService.ProjectDBService;
import bm.com.graduationproject.teamtarget.dbService.TaskDBService;
import bm.com.graduationproject.teamtarget.model.Task;
import bm.com.graduationproject.teamtarget.utils.AppContext;


public class MyTaskActivity extends Activity {

    private MyTaskListAdapter myTaskListAdapter;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task);
        setTitle(this.getResources().getString(R.string.my_task));


        //list view
        ListView myListView=(ListView)findViewById(R.id.my_task_working);

        //get user id;
        AppContext appContext=AppContext.getInstance();
        int userId =appContext.getUserId();


        //get user's tasks
        TaskDBService taskDBService=new TaskDBService(DBManager.getInstance(this));
        List<Task> userTasks=taskDBService.getTasksByDistributed(userId);


        //get number of comments
        CommentDBService commentDBService= new CommentDBService(DBManager.getInstance(this));
        ProjectDBService projectDBService= new ProjectDBService(DBManager.getInstance(this));
        int comments[]=new int[userTasks.size()];
        String projects[]=new String[userTasks.size()];
        for(int i=0;i<userTasks.size();i++){
            comments[i]=commentDBService.getCommentCountByTaskId(userTasks.get(i).getId());
            projects[i]=projectDBService.getProjectById(userTasks.get(i).getProjectId()).getName();

        }

        //set adapter
        MyTaskListAdapter adapter=new MyTaskListAdapter(this,userTasks,comments,projects);

        myListView.setAdapter(adapter);


        //set List view listener
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView idTextView=(TextView)view.findViewById(R.id.my_task_id);
                Intent intent=new Intent(MyTaskActivity.this,TaskActivity.class);
                intent.putExtra("taskId",Integer.parseInt(idTextView.getText().toString()));
                intent.putExtra("fromMyTask",1);

                MyTaskActivity.this.startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_task, menu);
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
