package bm.com.graduationproject.teamtarget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import bm.com.graduationproject.teamtarget.adapter.ProjectListAdapter;
import bm.com.graduationproject.teamtarget.dbHelper.DBManager;
import bm.com.graduationproject.teamtarget.dbService.ProjectDBService;
import bm.com.graduationproject.teamtarget.model.Project;


public class AddTaskActivity extends Activity {


    //project
    private Dialog projectDialog;
    private TextView projectTextView;
    //project  chosen id
    private int projectId=-1;
    private ProjectListAdapter projectListAdapter;
    private List<Project> projects;


    //deadline
    private View dateView;
    private Dialog dateDialog;
    private TextView deadlineText;
    private String chosenDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


        setTitle("新建任务");

        //set project
        RelativeLayout projectClick=(RelativeLayout)findViewById(R.id.project_click_add_task);
        projectTextView=(TextView)findViewById(R.id.add_task_project_content);

        //get project which meet condition --- now is all projects

        ProjectDBService projectDBService=new ProjectDBService(DBManager.getInstance(this));
        projects=projectDBService.getAllProject();

        projectListAdapter=new ProjectListAdapter(this,projects);

        if(projects.size()!=0){
            projectListAdapter.setSelected(0);
            projectId=0;
            projectTextView.setText(projects.get(0).getName());
        }


        projectClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showProjectDialog();
            }
        });


        //set deadline click listener
        RelativeLayout deadline=(RelativeLayout)findViewById(R.id.deadline_click_add_task);

        //set deadline text
        deadlineText=(TextView)findViewById(R.id.add_task_deadline_content);

        deadline.setOnClickListener(new DeadlineClickListener());



    }

    private void showProjectDialog(){


        LinearLayout projectDialogLayout=(LinearLayout)getLayoutInflater()
                .inflate(R.layout.dialog_common, null);

        ListView projectListView=(ListView)projectDialogLayout.findViewById(R.id.dialog_common_list);
        projectListView.setAdapter(projectListAdapter);


        //set list item
        projectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                projectListAdapter.setSelected(i);
                projectListAdapter.notifyDataSetChanged();

                projectTextView.setText(projects.get(i).getName());
                projectId=i;

                projectDialog.dismiss();
            }
        });


        //set cancel text view
        TextView cancel=(TextView)projectDialogLayout.findViewById(R.id.dialog_common_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                projectDialog.dismiss();
            }
        });

        AlertDialog.Builder builder=new AlertDialog.Builder(AddTaskActivity.this);
        builder.setView(projectDialogLayout);
        projectDialog=builder.create();
        projectDialog.show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_task, menu);
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

    class DeadlineClickListener implements View.OnClickListener{


        @Override
        public void onClick(View view) {

            AlertDialog.Builder builder=new AlertDialog.Builder(AddTaskActivity.this);
            dateView=View.inflate(AddTaskActivity.this,R.layout.dialog_data_picker,null);
            DatePicker datePicker=(DatePicker)dateView.findViewById(R.id.dialog_date_picker);


            TextView current=(TextView)dateView.findViewById(R.id.dialog_current_dialog);
            builder.setView(dateView);


            //date picker

            Calendar cal = Calendar.getInstance();

            datePicker.init(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),
                    new mDateChangeListener());

            int month=cal.get(Calendar.MONTH)+1;
            current.setText(""+cal.get(Calendar.YEAR)+"-"+month+"-"+
                    cal.get(Calendar.DAY_OF_MONTH));

            //buttons

            Button cancelButton=(Button)dateView.findViewById(R.id.dialog_date_cancel);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dateDialog.dismiss();
                }
            });

            Button okButton=(Button)dateView.findViewById(R.id.dialog_date_ok);
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    deadlineText.setText(chosenDate);

                    //DBService

                    dateDialog.dismiss();

                }
            });

            Button cleanButton=(Button)dateView.findViewById(R.id.dialog_date_clean);
            cleanButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deadlineText.setText("");

                    dateDialog.dismiss();
                }
            });

            dateDialog=builder.create();
            dateDialog.show();

        }
    }

    class mDateChangeListener implements DatePicker.OnDateChangedListener{

        @Override
        public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
            chosenDate="";
            month=month+1;
            chosenDate=""+year+"-"+month+"-"+dayOfMonth;
            TextView current=(TextView)dateView.findViewById(R.id.dialog_current_dialog);
            current.setText(chosenDate);

        }
    }

}
