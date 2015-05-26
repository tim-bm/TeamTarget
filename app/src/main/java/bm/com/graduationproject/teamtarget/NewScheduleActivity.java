package bm.com.graduationproject.teamtarget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

import bm.com.graduationproject.teamtarget.dbHelper.DBManager;
import bm.com.graduationproject.teamtarget.dbService.ScheduleDBService;
import bm.com.graduationproject.teamtarget.model.Schedule;


public class NewScheduleActivity extends Activity {

    private TextView deadlineText;
    private View dialogView;
    private Dialog dateDialog;
    private String chosenDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule);


        //dead line
        deadlineText=(TextView)findViewById(R.id.new_schedule_text_deadline);
        chosenDate="";
        RelativeLayout deadlineClick=(RelativeLayout)findViewById(R.id.new_schedule_deadline_click);
        deadlineClick.setOnClickListener(new deadlineClick());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_schedule, menu);
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
        if(id==R.id.new_schedule_tick){

            EditText name=(EditText)findViewById(R.id.new_schedule_title);
            EditText content=(EditText)findViewById(R.id.new_schedule_content);

            Schedule s=new Schedule(chosenDate,name.getText().toString(),content.getText().toString());

            ScheduleDBService scheduleDBService=new ScheduleDBService(DBManager.getInstance(this));
            scheduleDBService.insertSchedule(s);

            Intent intent =new Intent(this,MainActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class deadlineClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder=new AlertDialog.Builder(NewScheduleActivity.this);
            dialogView=View.inflate(NewScheduleActivity.this,R.layout.dialog_data_picker,null);

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
                    dateDialog.dismiss();

              }
            });

            Button cleanButton=(Button)dialogView.findViewById(R.id.dialog_date_clean);
            cleanButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deadlineText.setText("");
                    chosenDate="";
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
            TextView current=(TextView)dialogView.findViewById(R.id.dialog_current_dialog);
            current.setText(chosenDate);


        }
    }
}
