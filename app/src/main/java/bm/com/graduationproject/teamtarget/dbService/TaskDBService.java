package bm.com.graduationproject.teamtarget.dbService;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.renderscript.Sampler;

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

        dbManager.closeDB(database);
        return tasks;
    }

    public Task getTaskById(int taskId){

        SQLiteDatabase database;
        database=dbManager.openDB();

        Cursor cursor=database.rawQuery("select * from task where task_id=?",new String[]{String.valueOf(taskId)});

        Task t=new Task();
        while (cursor.moveToNext()){

            t=new Task(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getString(4),
                    cursor.getString(5),cursor.getInt(6),cursor.getInt(7),cursor.getInt(8),cursor.getInt(9));


        }
        dbManager.closeDB(database);
        return t;
    }

    public List<Task> getTasksByDistributed(int userId){

        SQLiteDatabase database;
        database=dbManager.openDB();

        List<Task> tasks=new ArrayList<Task>();

        Cursor cursor=database.rawQuery("select * from task where distribute_to=?"
                ,new String[]{String.valueOf(userId)});

        Task t;
        while (cursor.moveToNext()){

            t=new Task(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getString(4),
                    cursor.getString(5),cursor.getInt(6),cursor.getInt(7),cursor.getInt(8),cursor.getInt(9));

            tasks.add(t);
        }

        dbManager.closeDB(database);
        return tasks;
    }

    public List<Task> getTasksByProjectId(int projectId){

        SQLiteDatabase database;
        database=dbManager.openDB();

        List<Task> tasks=new ArrayList<Task>();

        Cursor cursor=database.rawQuery("select * from task where project_id=?"
                ,new String[]{String.valueOf(projectId)});

        Task t;
        while (cursor.moveToNext()){

            t=new Task(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getString(4),
                    cursor.getString(5),cursor.getInt(6),cursor.getInt(7),cursor.getInt(8),cursor.getInt(9));

            tasks.add(t);
        }

        dbManager.closeDB(database);
        return tasks;


    }

    public List<Task> getLatestTwoTask(){

        List<Task> tasks=new ArrayList<Task>(2);

        SQLiteDatabase database;
        database=dbManager.openDB();

        Cursor cursor=database.rawQuery("select * from task order by task_id desc limit 2 offset 1",null);
        Task t;
        while (cursor.moveToNext()){
            t=new Task(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),
                    cursor.getString(4),cursor.getString(5),cursor.getInt(6),cursor.getInt(7),cursor.getInt(8),cursor.getInt(9));
            tasks.add(t);
        }
        return tasks;
    }

    public int updateTask(Task task){

        SQLiteDatabase database;
        database=dbManager.openDB();

        ContentValues contentValues=new ContentValues();
        contentValues.put("task_name",task.getName());
        contentValues.put("description",task.getDescription());
        contentValues.put("creator",task.getCreator());
        contentValues.put("deadline",task.getDeadline());
        contentValues.put("comment",task.getComment());
        contentValues.put("tag",task.getTag());
        contentValues.put("distribute_to",task.getDistributeTo());
        contentValues.put("task_list_id",task.getTaskListId());
        contentValues.put("project_id",task.getProjectId());

        int result= database.update("task",contentValues,"task_id=?",new String[]{String.valueOf(task.getId())});
        dbManager.closeDB(database);
        return result;

    }

    public int insertTask(Task task){

        SQLiteDatabase database;
        database=dbManager.openDB();

        ContentValues contentValues=new ContentValues();
        contentValues.put("task_name",task.getName());
        contentValues.put("description",task.getDescription());
        contentValues.put("creator",task.getCreator());
        contentValues.put("deadline",task.getDeadline());
        contentValues.put("comment",task.getComment());
        contentValues.put("tag",task.getTag());
        contentValues.put("distribute_to",task.getDistributeTo());
        contentValues.put("task_list_id",task.getTaskListId());
        contentValues.put("project_id",task.getProjectId());

        int result= new Long(database.insert("task",null,contentValues)).intValue();
        dbManager.closeDB(database);
        return result;
    }
}
