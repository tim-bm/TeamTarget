package bm.com.graduationproject.teamtarget.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by bm on 2015/4/28.
 */
public class DBManager {

    private static DBOpenHelper dbOpenHelper;
    private static SQLiteDatabase db;

    private static  int mCount;

    private static DBManager instance=null;

    private DBManager(Context context){

        dbOpenHelper= DBOpenHelper.getInstance(context);

    }

    public static synchronized  DBManager getInstance(Context context){
        if(instance==null){

                instance=new DBManager(context);
        }

        return instance;
    }

    public synchronized  SQLiteDatabase openDB(){
        if(mCount==0){
            db=dbOpenHelper.getWritableDatabase();
        }

        mCount++;
        return db;
    }

    public synchronized void closeDB(SQLiteDatabase database){
        mCount--;
        if(mCount==0){

            database.close();
        }
    }
}
