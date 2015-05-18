package bm.com.graduationproject.teamtarget.dbService;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import bm.com.graduationproject.teamtarget.dbHelper.DBManager;
import bm.com.graduationproject.teamtarget.model.Comment;

/**
 * Created by bm on 2015/5/12.
 */
public class CommentDBService {

    private DBManager dbManager;

    public CommentDBService(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public List<Comment> getCommentsByTaskId(int taskId){

        List<Comment> comments=new ArrayList<Comment>();

        SQLiteDatabase database;
        database=dbManager.openDB();
        Cursor cursor=database.rawQuery("select * from comment where task_id=?",
                new String[]{String.valueOf(taskId)});
        Comment c;
        while (cursor.moveToNext()){
            c=new Comment(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4));
            comments.add(c);
        }
            dbManager.closeDB(database);
        return comments;
    }
    public int getCommentCountByTaskId(int taskId){

        SQLiteDatabase database;
        database=dbManager.openDB();
        Cursor cursor=database.rawQuery("select count(*) from comment where task_id=?",
                new String[]{String.valueOf(taskId)});
        cursor.moveToFirst();
        return  new Long(cursor.getLong(0)).intValue();

    }
}
