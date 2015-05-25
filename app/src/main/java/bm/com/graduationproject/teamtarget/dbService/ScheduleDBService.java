package bm.com.graduationproject.teamtarget.dbService;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import bm.com.graduationproject.teamtarget.dbHelper.DBManager;
import bm.com.graduationproject.teamtarget.model.Schedule;

/**
 * Created by bm on 2015/5/22.
 */
public class ScheduleDBService {

    private DBManager dbManager;

    public ScheduleDBService (DBManager dbManager){
        this.dbManager=dbManager;

    }


    public List<Schedule> getLatestFiveSchedule(){

        List<Schedule> schedules=new ArrayList<Schedule>(5);

        SQLiteDatabase database;
        database=dbManager.openDB();

        Cursor cursor=database.rawQuery("select * from schedule order by schedule_id desc limit 5 offset 1",null);
        Schedule s;
        while (cursor.moveToNext()){
           s=new Schedule(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
            schedules.add(s);
        }
        dbManager.closeDB(database);
        return schedules;
    }

    public Schedule getScheduleByDate(String date){

        Schedule schedule=new Schedule();

        SQLiteDatabase database;
        database=dbManager.openDB();

        Cursor cursor=database.rawQuery("select * from schedule where date=?",new String[]{date});

        while(cursor.moveToNext()){
            schedule=new Schedule(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
        }

        return schedule;
    }


}
