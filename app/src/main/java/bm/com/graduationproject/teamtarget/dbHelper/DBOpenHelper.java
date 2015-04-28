package bm.com.graduationproject.teamtarget.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by bm on 2015/4/28.
 */
public class DBOpenHelper extends SQLiteOpenHelper {


    private static DBOpenHelper instance=null;


   private final static  String DATABASE_NAME="teamTargetDatabase";
   private  final static int DATABASE_VERSION=1;

    final String CREATE_TABLE="CREATE TABLE `user` (" +
            "  `user_id` int(11) NOT NULL AUTOINCREMENT," +
            "  `email` varchar(255) DEFAULT NULL," +
            "  `user_name` varchar(25) DEFAULT NULL," +
            "  PRIMARY KEY (`user_id`)" +
            ")";

    private DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public static DBOpenHelper getInstance(Context context){

        if(instance==null){

            synchronized (DBOpenHelper.class) {
                if (instance == null) {
                    instance = new DBOpenHelper(context);
                }
            }
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        Log.d("create table","true");
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
