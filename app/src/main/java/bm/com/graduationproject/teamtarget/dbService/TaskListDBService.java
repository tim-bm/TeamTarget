package bm.com.graduationproject.teamtarget.dbService;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import bm.com.graduationproject.teamtarget.dbHelper.DBManager;
import bm.com.graduationproject.teamtarget.model.TaskList;

/**
 * Created by bm on 2015/5/4.
 */
public class TaskListDBService {

    private DBManager dbManager;

    public TaskListDBService(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public List<TaskList> getTaskListByProjectId(int projectId){

        List<TaskList> taskLists=new ArrayList<TaskList>();

        SQLiteDatabase database;
        database=dbManager.openDB();

        Cursor cursor=database.rawQuery("select * from task_list where project_id=?",new String[]{String.valueOf(projectId)});

        TaskList t;
        while(cursor.moveToNext()){

           t=new TaskList(cursor.getInt(0),cursor.getString(1),cursor.getInt(2));

            taskLists.add(t);
        }
        dbManager.closeDB(database);
        return taskLists;
    }
    public int addTaskList(TaskList taskList){

        SQLiteDatabase database;
        database=dbManager.openDB();
        ContentValues cv=new ContentValues();

        cv.put("task_name",taskList.getName());
        cv.put("project_id",taskList.getProjectId());

        int results=new Long(database.insert("task_list",null,cv)).intValue();

        dbManager.closeDB(database);
        return results;

    }
}
