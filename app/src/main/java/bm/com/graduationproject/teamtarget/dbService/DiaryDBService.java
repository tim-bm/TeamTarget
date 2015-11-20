package bm.com.graduationproject.teamtarget.dbService;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import bm.com.graduationproject.teamtarget.dbHelper.DBManager;
import bm.com.graduationproject.teamtarget.model.Diary;

/**
 * Created by bm on 2015/5/26.
 */
public class DiaryDBService {
    private DBManager dbManager;

    public DiaryDBService(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public List<Diary> getDiaryDesc(){

        List<Diary> diaries=new ArrayList<Diary>();

        SQLiteDatabase database;
        database=dbManager.openDB();

        Cursor cursor=database.rawQuery("select * from diary order by diary_id desc ",null);

        Diary d;
        while(cursor.moveToNext()){

            d=new Diary(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3));
            diaries.add(d);
        }

        dbManager.closeDB(database);
        return diaries;

    }

    public int insertDiary(Diary diary){

        SQLiteDatabase database;
        database=dbManager.openDB();

        ContentValues contentValues=new ContentValues();
        contentValues.put("date",diary.getDate());
        contentValues.put("task_id",diary.getTaskId());
        contentValues.put("content",diary.getContent());

        int result= new Long(database.insert("diary",null,contentValues)).intValue();
        dbManager.closeDB(database);
        return result;
    }


}
