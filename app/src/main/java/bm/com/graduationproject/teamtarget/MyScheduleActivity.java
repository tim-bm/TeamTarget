package bm.com.graduationproject.teamtarget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import bm.com.graduationproject.teamtarget.dbHelper.DBManager;
import bm.com.graduationproject.teamtarget.dbService.ScheduleDBService;
import bm.com.graduationproject.teamtarget.model.Schedule;


public class MyScheduleActivity extends Activity {


    private TextView scheduleContent;
    private List<Schedule> schedules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);


        scheduleContent=(TextView)findViewById(R.id.calenderVIew_content);

        MaterialCalendarView calendarView=(MaterialCalendarView)findViewById(R.id.calenderVIew);

        ScheduleDBService scheduleDBService=new ScheduleDBService(DBManager.getInstance(this));

        schedules=scheduleDBService.getLatestFiveSchedule();




        //get latest schedule

        if(schedules.size()!=0){

            Schedule latest=schedules.get(0);
            calendarView.setSelectedDate(stringToCalender(latest.getDate()));
            scheduleContent.setText(latest.getName()+"： "+latest.getContent());

        }


        //test and check notification



        calendarView.setOnDateChangedListener(new OnDateChangedListener() {
            @Override
            public void onDateChanged(MaterialCalendarView materialCalendarView, CalendarDay calendarDay) {

                ScheduleDBService scheduleDBService=new ScheduleDBService(DBManager.getInstance(MyScheduleActivity.this));
                Schedule s=scheduleDBService.getScheduleByDate(MyScheduleActivity.this.calendarToString(calendarDay.getCalendar()));

                if(s.getName()==null){
                    Toast.makeText(MyScheduleActivity.this,""+MyScheduleActivity.this.calendarToString(calendarDay.getCalendar())+"没有日程安排"
                            ,Toast.LENGTH_LONG).show();
                    scheduleContent.setText("");
                }else{
                    scheduleContent.setText(s.getName()+"： "+s.getContent());
                }

            }
        });

        //test service
        Intent intent=new Intent();
        intent.setAction("com.bm.webservice");
        startService(intent);







    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_schedule, menu);
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

    private Calendar stringToCalender(String date){

        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            cal.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return cal;
    }

    private String calendarToString(Calendar calendar){

        String date="";
        int month=calendar.get(Calendar.MONTH)+1;
        date=""+calendar.get(Calendar.YEAR)+"-"+month+"-"+calendar.get(Calendar.DAY_OF_MONTH);

        return date;

    }
}
