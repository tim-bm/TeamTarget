package bm.com.graduationproject.teamtarget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import bm.com.graduationproject.teamtarget.dbHelper.DBManager;
import bm.com.graduationproject.teamtarget.dbService.TaskListDBService;
import bm.com.graduationproject.teamtarget.model.TaskList;


public class AddTaskListActivity extends Activity {

    private int projectId;
    private String projectTitle;

    private EditText editTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_list);


        setTitle(getResources().getString(R.string.new_task_list));

        Intent infoIntent=getIntent();
        projectId=infoIntent.getIntExtra("projectId",-1);
        projectTitle=infoIntent.getStringExtra("projectName");

        //set title
        TextView title=(TextView)findViewById(R.id.add_task_list_project_name);
        title.setText(projectTitle);

        //edit text
        editTaskList=(EditText)findViewById(R.id.add_task_list_edit);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_task_list, menu);
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

        if(id==android.R.id.home){

            backToTaskList();
            return true;
        }

        if(id==R.id.add_task_list_tick){

            TaskList taskList=new TaskList();
            if(editTaskList.getText()==null){
                taskList.setName("None");
            }else{
                taskList.setName(editTaskList.getText().toString());
            }

            taskList.setProjectId(projectId);
            TaskListDBService taskListDBService=new TaskListDBService(DBManager.getInstance(this));
            taskListDBService.addTaskList(taskList);

            backToTaskList();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void backToTaskList(){

        Intent intent=new Intent(this,TaskListActivity.class);

        intent.putExtra("projectId",projectId);
        intent.putExtra("projectName",projectTitle);


        startActivity(intent);


    }
}
