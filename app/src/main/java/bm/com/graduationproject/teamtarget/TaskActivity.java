package bm.com.graduationproject.teamtarget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bm.com.graduationproject.teamtarget.adapter.CommentListAdapter;
import bm.com.graduationproject.teamtarget.adapter.TagListAdapter;
import bm.com.graduationproject.teamtarget.dbHelper.DBManager;
import bm.com.graduationproject.teamtarget.dbService.CommentDBService;
import bm.com.graduationproject.teamtarget.dbService.TaskDBService;
import bm.com.graduationproject.teamtarget.dbService.UserDBService;
import bm.com.graduationproject.teamtarget.model.Comment;
import bm.com.graduationproject.teamtarget.model.Task;
import bm.com.graduationproject.teamtarget.model.User;
import bm.com.graduationproject.teamtarget.utils.AppContext;
import bm.com.graduationproject.teamtarget.utils.Tag;


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
    private TagListAdapter tagListAdapter;

    //comment
    private List<Comment> commentList;
    private ListView commentListView;

    //tag and dialog
    private TextView tagTextView;
    private Dialog tagDialog;




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
                Intent intent=new Intent(TaskActivity.this,EditTaskActivity.class);

                //determine if from myTask or task activity
                if(infoIntent.getIntExtra("fromMyTask",-1)==-1){

                    int projectId=infoIntent.getIntExtra("projectId",-1);
                    String projectName=infoIntent.getStringExtra("projectName");



                    intent.putExtra("projectId",projectId);
                    intent.putExtra("projectName",projectName);
                    intent.putExtra("taskId",taskId);

                }else{
                    intent.putExtra("taskId",taskId);
                    intent.putExtra("fromMyTask",1);
                }


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
                    task.setComment("0");
                    taskDBService.updateTask(task);
                }
                titleTextView.invalidate();
            }
        });

        //set tag
        RelativeLayout tagClick=(RelativeLayout)findViewById(R.id.task_tag);
         tagTextView=(TextView)findViewById(R.id.task_tag_color);
        //tagTextView.setBackgroundResource(R.drawable.text_view_radius_border);

        tagListAdapter=new TagListAdapter(this,getPreparedTags());

        if(task.getTag()!=-1){
            tagListAdapter.setSelectedPosition(task.getTag());
            tagListAdapter.notifyDataSetChanged();
            tagTextView.setBackgroundResource(getPreparedTags().get(task.getTag()).getResource());
        }

        tagClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showTagDialog();

            }
        });





        //get comments
        commentDBService=new CommentDBService(DBManager.getInstance(this));
        commentList=commentDBService.getCommentsByTaskId(taskId);

        commentListView=(ListView)findViewById(R.id.comment_list_view);

        if(commentList.size()!=0){

            //find user name
            UserDBService userDBService=new UserDBService(DBManager.getInstance(this));
            List<User> users=new ArrayList<User>();
            User u;
            for(int i=0;i<commentList.size();i++){
                u=userDBService.getUserByUserId(commentList.get(i).getUserId());
                users.add(u);
            }

            //set adapter
            commentListAdapter=new CommentListAdapter(this,commentList,users);
            commentListAdapter.notifyDataSetChanged();
            commentListView.setAdapter(commentListAdapter);
            setListViewHeight(commentListView);

        }

        //set edit text
        EditText newComment=(EditText)findViewById(R.id.task_comment);
        newComment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                if(actionId== EditorInfo.IME_ACTION_DONE){

                    Comment comment=new Comment();
                    comment.setUserId(AppContext.getInstance().getUserId());
                    comment.setTaskId(taskId);
                    comment.setContent(textView.getText().toString());

                    Calendar cal = Calendar.getInstance();
                    int month=cal.get(Calendar.MONTH)+1;

                    comment.setDate(""+cal.get(Calendar.YEAR)+"-"+month+"-"+cal.get(Calendar.DAY_OF_MONTH));

                    CommentDBService commentDBService_1=new CommentDBService(DBManager.getInstance(TaskActivity.this));
                    commentDBService_1.insertComment(comment);

                    closeInputMethod(textView);
                    textView.setText("");
                    refresh();

                }


                return true;
            }
        });






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

            //set default date
            chosenDate=""+cal.get(Calendar.YEAR)+"-"+month+"-"+
                    cal.get(Calendar.DAY_OF_MONTH);

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
        if(id==R.id.task_tick){

            Intent intent_main=new Intent(this,MainActivity.class);
            startActivity(intent_main);

            return true;
        }
        if(id==android.R.id.home){


            Intent infoIntent=getIntent();

            if(infoIntent.getIntExtra("backToMain",-1)==1){
                Intent intent_main=new Intent(this,MainActivity.class);
                startActivity(intent_main);
                return true;
            }

            if(infoIntent.getIntExtra("fromMyTask",-1)==-1){
                int projectId=infoIntent.getIntExtra("projectId",-1);
                String projectName=infoIntent.getStringExtra("projectName");



                Intent intent =new Intent(this,TaskListActivity.class);
                intent.putExtra("projectId",projectId);
                intent.putExtra("projectName",projectName);

                startActivity(intent);


            }else{


                Intent intent_my=new Intent(this,MyTaskActivity.class);
                startActivity(intent_my);
            }


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

            listItem.measure(0,0);
            totalHeight+=listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params=listView.getLayoutParams();
        params.height=totalHeight+(listView.getDividerHeight()*(adapter.getCount()-1));
        listView.setLayoutParams(params);

    }
    private void showTagDialog(){

        LinearLayout tagDialogLinearLayout=(LinearLayout)getLayoutInflater()
                .inflate(R.layout.dialog_tag_selection, null);

        ListView tagListView=(ListView)tagDialogLinearLayout.findViewById(R.id.task_tag_list_view);
        tagListView.setAdapter(tagListAdapter);

        //set cancel button
        TextView cancel =(TextView)tagDialogLinearLayout.findViewById(R.id.dialog_tag_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tagDialog.dismiss();
            }
        });


        //set listItem listener
        tagListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tagListAdapter.setSelectedPosition(i);
                tagListAdapter.notifyDataSetChanged();


                List<Tag> tagColor=getPreparedTags();
                tagTextView.setBackgroundResource(tagColor.get(i).getResource());
                task.setTag(tagColor.get(i).getSign());
                taskDBService.updateTask(task);
               tagDialog.dismiss();

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);
        builder.setView(tagDialogLinearLayout);
        tagDialog=builder.create();
        tagDialog.show();
    }

    private void closeInputMethod(TextView textView){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if (isOpen) {

            imm.hideSoftInputFromWindow(textView.getWindowToken(),0);
        }
    }

    private void refresh(){
        onCreate(null);
    }
    private List<Tag> getPreparedTags(){
        List<Tag> tags=new ArrayList<Tag>(3);

        Tag t;
        t=new Tag(R.drawable.tag_appearance_red,0,"紧急");
        tags.add(t);
        t=new Tag(R.drawable.tag_appearance_yellow,1,"一般");
        tags.add(t);
        t=new Tag(R.drawable.tag_appearance_green,2,"正常");
        tags.add(t);

        return  tags;
    }

}
