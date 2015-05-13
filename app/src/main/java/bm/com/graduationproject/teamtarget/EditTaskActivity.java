package bm.com.graduationproject.teamtarget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import bm.com.graduationproject.teamtarget.dbHelper.DBManager;
import bm.com.graduationproject.teamtarget.dbService.TaskDBService;
import bm.com.graduationproject.teamtarget.model.Task;


public class EditTaskActivity extends Activity {

    private int taskId;
    private Task task;

    //DBService
    private TaskDBService taskDBService;

    //text view
    private EditText title;
    private EditText description;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);


        this.setTitle("编辑任务");

        Intent intent=getIntent();
        taskId=intent.getIntExtra("taskId",-1);


        //task DBService
        taskDBService=new TaskDBService(DBManager.getInstance(this));
        task=taskDBService.getTaskById(taskId);


        title=(EditText)findViewById(R.id.edit_task_name);
        description=(EditText)findViewById(R.id.edit_task_description);

        title.setText(task.getName());
        description.setText(task.getDescription());




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_task, menu);
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

        if(id==R.id.ic_tick){

            task.setName(title.getText().toString());
            task.setDescription(description.getText().toString());

            taskDBService.updateTask(task);

            backToTaskActivity();
            return true;


        }

        if(id==android.R.id.home){


            backToTaskActivity();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void backToTaskActivity(){

        Intent infoIntent=getIntent();
        int projectId=infoIntent.getIntExtra("projectId",-1);
        String projectName=infoIntent.getStringExtra("projectName");


        Intent intent=new Intent(this,TaskActivity.class);

        intent.putExtra("projectId",projectId);
        intent.putExtra("projectName",projectName);
        intent.putExtra("taskId",taskId);

        startActivity(intent);

    }
}
