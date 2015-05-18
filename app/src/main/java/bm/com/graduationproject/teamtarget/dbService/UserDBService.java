package bm.com.graduationproject.teamtarget.dbService;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import bm.com.graduationproject.teamtarget.dbHelper.DBManager;
import bm.com.graduationproject.teamtarget.model.User;

/**
 * Created by bm on 2015/5/13.
 */
public class UserDBService {
    private DBManager dbManager;

    public UserDBService(DBManager dbManager) {
        this.dbManager = dbManager;
    }


    public User getUserByUserId(int userId){

        SQLiteDatabase database;
        database=dbManager.openDB();

       Cursor cursor= database.rawQuery("select * from user where user_id=?", new String[]{String.valueOf(userId)});
        User u=new User();
        while (cursor.moveToNext()){
            u=new User(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
        }

        dbManager.closeDB(database);
        return u;
    }
}
