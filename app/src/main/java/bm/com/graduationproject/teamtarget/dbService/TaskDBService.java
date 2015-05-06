package bm.com.graduationproject.teamtarget.dbService;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import bm.com.graduationproject.teamtarget.dbHelper.DBManager;
import bm.com.graduationproject.teamtarget.model.Task;

/**
 * Created by bm on 2015/5/5.
 */
public class TaskDBService {

    private DBManager dbManager;

    public TaskDBService(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public List<Task> getTasksByTaskListId(int taskListId){

        List<Task> tasks=new ArrayList<Task>();

        SQLiteDatabase database;
        database=dbManager.openDB();

        Cursor cursor=database.rawQuery("select * from task where task_list_id=?",new String[]{String.valueOf(taskListId)});

        Task t;
        while (cursor.moveToNext()){

            t=new Task(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getString(4),
                    cursor.getString(5),cursor.getInt(6),cursor.getInt(7),cursor.getInt(8),cursor.getInt(9));

            tasks.add(t);
        }

        return tasks;
    }
}
