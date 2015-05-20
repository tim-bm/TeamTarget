package bm.com.graduationproject.teamtarget.dbService;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import bm.com.graduationproject.teamtarget.dbHelper.DBManager;
import bm.com.graduationproject.teamtarget.model.Project;

/**
 * Created by bm on 2015/5/1.
 */
public class ProjectDBService {

    private DBManager dbManager;


    public ProjectDBService(DBManager dbManager){

        this.dbManager=dbManager;
    }
    public List<Project> getAllProject(){

        List<Project> projects=new ArrayList<Project>();

        SQLiteDatabase database;
        database=dbManager.openDB();


        Cursor cursor=database.rawQuery("select * from project",null);
        Project p;
        while(cursor.moveToNext()){

            p=new Project(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                    cursor.getString(3),cursor.getInt(4),cursor.getInt(5));
            projects.add(p);

        }

        dbManager.closeDB(database);
        return projects;

    }
    public Project getProjectById(int projectId){
        SQLiteDatabase database;
        database=dbManager.openDB();

        Cursor cursor=database.rawQuery("select * from project where project_id=?",
                new String[]{String.valueOf(projectId)});
        Project p=new Project();
        while(cursor.moveToNext()){
            p=new Project(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                    cursor.getString(3),cursor.getInt(4),cursor.getInt(5));
        }

        dbManager.closeDB(database);
        return p;
    }

    public int addProject(Project project){
        SQLiteDatabase database;
        database=dbManager.openDB();
        ContentValues cv=new ContentValues();
        cv.put("project_name",project.getName());
        cv.put("ownership",project.getOwnership());
        cv.put("publicity",project.getPublicity());
        cv.put("creator",project.getCreator());
        cv.put("finish_status",project.getFinishStatus());

        int results=new Long(database.insert("project",null,cv)).intValue();

        dbManager.closeDB(database);
        return results;
    }



}
