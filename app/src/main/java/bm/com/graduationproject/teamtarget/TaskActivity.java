package bm.com.graduationproject.teamtarget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;

import bm.com.graduationproject.teamtarget.adapter.CommentListAdapter;
import bm.com.graduationproject.teamtarget.dbHelper.DBManager;
import bm.com.graduationproject.teamtarget.dbService.CommentDBService;
import bm.com.graduationproject.teamtarget.dbService.TaskDBService;
import bm.com.graduationproject.teamtarget.model.Comment;
import bm.com.graduationproject.teamtarget.model.Task;


public class TaskActivity extends Activity {

    private int taskId;
    private Task task;
    private String chosenDate;
    private View dialogView;
    private Dialog dateDialog;

    //DBService
    private TaskDBService taskDBService;
    private CommentDBService commentDBService;

    //deadline text
    private TextView deadlineText;
    private CheckBox checkBox;

    //title text view
    private TextView titleTextView;

    //description
    private TextView descriptionTextView;

    //distribute_to


    //adapter
    private CommentListAdapter commentListAdapter;


    //comment
    private List<Comment> commentList;
    private ListView commentListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);


        //get task id
        Intent intent=getIntent();
        taskId=intent.getIntExtra("taskId",-1);


        //task DBService
         taskDBService=new TaskDBService(DBManager.getInstance(this));
         task=taskDBService.getTaskById(taskId);



        //title
        titleTextView=(TextView)findViewById(R.id.task_name);
        titleTextView.setText(task.getName());
        titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent infoIntent=getIntent();
                int projectId=infoIntent.getIntExtra("projectId",-1);
                String projectName=infoIntent.getStringExtra("projectName");

                
                Intent intent=new Intent(TaskActivity.this,EditTaskActivity.class);
                intent.putExtra("projectId",projectId);
                intent.putExtra("projectName",projectName);
                intent.putExtra("taskId",taskId);

                startActivity(intent);
            }
        });


        //set description
        descriptionTextView=(TextView)findViewById(R.id.task_description);
        descriptionTextView.setText(task.getDescription());


        //get deadline text view
        deadlineText=(TextView)findViewById(R.id.dialog_text_deadline);
        deadlineText.setText(task.getDeadline());


        //set deadline clickListener
        RelativeLayout deadline=(RelativeLayout)findViewById(R.id.deadline_click);
        deadline.setOnClickListener(new deadlineClickListener());


        //set check box
        checkBox=(CheckBox)findViewById(R.id.task_checkbox);

        if(task.getComment().equals("1")){
            checkBox.setChecked(true);
            titleTextView.setTextColor(Color.parseColor("#A0A0A0"));
            titleTextView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            titleTextView.invalidate();
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked==true){

                    titleTextView.setTextColor(Color.parseColor("#A0A0A0"));
                    titleTextView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    task.setComment("1");
                    taskDBService.updateTask(task);


                }else {

                    titleTextView.setTextColor(Color.parseColor("#000000"));
                    titleTextView.getPaint().setFlags(0);

                }
                titleTextView.invalidate();
            }
        });

        //get comments
        commentDBService=new CommentDBService(DBManager.getInstance(this));
        commentList=commentDBService.getCommentsByTaskId(taskId);

        commentListView=(ListView)findViewById(R.id.comment_list_view);

        if(commentList.size()!=0){
            //set adapter
            commentListAdapter=new CommentListAdapter(this,commentList);
            commentListAdapter.notifyDataSetChanged();
            commentListView.setAdapter(commentListAdapter);
            setListViewHeight(commentListView);

        }




    }


    class deadlineClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {

            AlertDialog.Builder builder=new AlertDialog.Builder(TaskActivity.this);
            dialogView=View.inflate(TaskActivity.this,R.layout.dialog_data_picker,null);
            DatePicker datePicker=(DatePicker)dialogView.findViewById(R.id.dialog_date_picker);

            TextView current=(TextView)dialogView.findViewById(R.id.dialog_current_dialog);
            builder.setView(dialogView);


            //date picker

            Calendar cal = Calendar.getInstance();

            datePicker.init(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),
                    new mDateChangeListener());

            int month=cal.get(Calendar.MONTH)+1;
            current.setText(""+cal.get(Calendar.YEAR)+"-"+month+"-"+
                    cal.get(Calendar.DAY_OF_MONTH));

            //buttons

            Button cancelButton=(Button)dialogView.findViewById(R.id.dialog_date_cancel);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dateDialog.dismiss();
                }
            });

            Button okButton=(Button)dialogView.findViewById(R.id.dialog_date_ok);
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    deadlineText.setText(chosenDate);
                    task.setDeadline(chosenDate);
                    taskDBService.updateTask(task);
                    dateDialog.dismiss();

                }
            });

            Button cleanButton=(Button)dialogView.findViewById(R.id.dialog_date_clean);
            cleanButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deadlineText.setText("");
                    task.setDeadline("");
                    taskDBService.updateTask(task);
                    dateDialog.dismiss();
                }
            });



            dateDialog=builder.create();
            dateDialog.show();


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task, menu);

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


            Intent infoIntent=getIntent();
            int projectId=infoIntent.getIntExtra("projectId",-1);
            String projectName=infoIntent.getStringExtra("projectName");



            Intent intent =new Intent(this,TaskListActivity.class);
            intent.putExtra("projectId",projectId);
            intent.putExtra("projectName",projectName);

            startActivity(intent);

          // onBackPressed();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
    class mDateChangeListener implements DatePicker.OnDateChangedListener{

        @Override
        public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
            chosenDate="";
            month=month+1;
            chosenDate=""+year+"-"+month+"-"+dayOfMonth;
            TextView current=(TextView)dialogView.findViewById(R.id.dialog_current_dialog);
            current.setText(chosenDate);


        }
    }

    private void setListViewHeight(ListView listView){

        CommentListAdapter adapter=commentListAdapter;

        int totalHeight=0;
        for(int i=0,len=adapter.getCount();i<len;i++){

            View listItem=adapter.getView(i,null,listView);

            listItem.measure(1,1);
            totalHeight+=listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params=listView.getLayoutParams();
        params.height=totalHeight+(listView.getDividerHeight()*(adapter.getCount()-1));
        listView.setLayoutParams(params);

    }

}
