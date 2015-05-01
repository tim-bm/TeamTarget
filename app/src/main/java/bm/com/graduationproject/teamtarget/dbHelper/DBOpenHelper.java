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

    final String CREATE_TABLE_PROJECT="CREATE TABLE \"project\" (" +
            "\"project_id\"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "\"project_name\"  TEXT NOT NULL," +
            "\"ownership\"  TEXT NOT NULL," +
            "\"publicity\"  TEXT NOT NULL," +
            "\"creator\"  INTEGER NOT NULL," +
            "\"finish_status\"  INTEGER," +
            "CONSTRAINT \"fk_creator\" FOREIGN KEY (\"creator\") REFERENCES \"user\" (\"user_id\") ON DELETE CASCADE ON UPDATE CASCADE" +
            ");";
    final String CREATE_TABLE_TAG="CREATE TABLE \"tag\" (" +
            "\"tag_id\"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "\"status\"  TEXT NOT NULL" +
            ");";
    final String CREATE_TABLE_TASK="CREATE TABLE \"task\" (" +
            "\"task_id\"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "\"description\"  TEXT," +
            "\"creator\"  INTEGER," +
            "\"deadline\"  TEXT NOT NULL," +
            "\"comment\"  TEXT," +
            "\"tag\"  INTEGER," +
            "\"distribute_to\"  INTEGER NOT NULL," +
            "\"task_list_id\"  INTEGER NOT NULL," +
            "\"project_id\"  INTEGER NOT NULL," +
            "CONSTRAINT \"fk_distribute\" FOREIGN KEY (\"distribute_to\") REFERENCES \"user\" (\"user_id\") ON DELETE SET NULL ON UPDATE CASCADE," +
            "CONSTRAINT \"fk_tag\" FOREIGN KEY (\"tag\") REFERENCES \"tag\" (\"tag_id\") ON DELETE SET NULL ON UPDATE CASCADE," +
            "CONSTRAINT \"fk_task_list\" FOREIGN KEY (\"task_list_id\") REFERENCES \"task_list\" (\"task_list_id\") ON DELETE CASCADE ON UPDATE CASCADE," +
            "CONSTRAINT \"fk_project\" FOREIGN KEY (\"project_id\") REFERENCES \"project\" (\"project_id\") ON DELETE CASCADE ON UPDATE CASCADE"+
            ");";
    final String CREATE_TABLE_TASK_LIST="CREATE TABLE \"task_list\" (" +
            "\"task_list_id\"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "\"task_name\"  TEXT NOT NULL," +
            "\"project_id\"  INTEGER NOT NULL," +
            "CONSTRAINT \"fk_project\" FOREIGN KEY (\"project_id\") REFERENCES \"project\" (\"project_id\") ON DELETE CASCADE ON UPDATE CASCADE" +
            ");";
    final String CREATE_TABLE_USER="CREATE TABLE \"user\" (" +
            "\"user_id\"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "\"user_name\"  TEXT NOT NULL," +
            "\"user_email\"  TEXT NOT NULL" +
            ");";


    final String CREATE_TABLE_PROJECT_PARTICIPANT="CREATE TABLE \"project_participant\" (" +
            "\"project_id\"  INTEGER NOT NULL," +
            "\"user_id\"  INTEGER NOT NULL," +
            "PRIMARY KEY (\"project_id\" ASC, \"user_id\" ASC)," +
            "CONSTRAINT \"fk_participant\" FOREIGN KEY (\"user_id\") REFERENCES \"user\" (\"user_id\") ON DELETE CASCADE ON UPDATE CASCADE," +
            "CONSTRAINT \"fk_project\" FOREIGN KEY (\"project_id\") REFERENCES \"project\" (\"project_id\") ON DELETE CASCADE ON UPDATE CASCADE" +
            ");";

    //initial data
    final String INSERT_USER_1="INSERT INTO \"user\" VALUES (1, 'aaa', 'aaa@qq.com');";
    final String INSERT_USER_2="INSERT INTO \"user\" VALUES (2, 'bbb', 'bbb@qq.com');";

    final String INSERT_TAG_1="INSERT INTO \"tag\" VALUES (1, '完成');";
    final String INSERT_TAG_2="INSERT INTO \"tag\" VALUES (2, '紧急');";
    final String INSERT_TAG_3="INSERT INTO \"tag\" VALUES (3, '正常');";

    final String INSERT_PROJECT="INSERT INTO \"project\" VALUES (1, '毕业设计', '个人项目', '私有项目', 1, 0);";

    final String INSERT_TASK_LIST_1="INSERT INTO \"main\".\"task_list\" VALUES (1, '正在做', 1);";
    final String INSERT_TASK_LIST_2="INSERT INTO \"main\".\"task_list\" VALUES (2, '将要做', 1);";
    final String INSERT_TASK_LIST_3="INSERT INTO \"main\".\"task_list\" VALUES (3, '紧急事项', 1);";

    final String INSERT_TASK="INSERT INTO \"task\" VALUES (1, '待完成任务', 1, '2015-4-30', '我的评论', 1, 1, 1, 1);";

    final String INSERT_PROJECT_PARTICIPANT_1="INSERT INTO \"project_participant\" VALUES (1, 1);";
    final String INSERT_PROJECT_PARTICIPANT_2="INSERT INTO \"project_participant\" VALUES (1, 2);";




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
        sqLiteDatabase.execSQL(CREATE_TABLE_PROJECT);
        sqLiteDatabase.execSQL(CREATE_TABLE_TAG);
        sqLiteDatabase.execSQL(CREATE_TABLE_TASK);
        sqLiteDatabase.execSQL(CREATE_TABLE_TASK_LIST);
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(CREATE_TABLE_PROJECT_PARTICIPANT);


        //initial data
        sqLiteDatabase.execSQL(INSERT_USER_1);
        sqLiteDatabase.execSQL(INSERT_USER_2);

        sqLiteDatabase.execSQL(INSERT_TAG_1);
        sqLiteDatabase.execSQL(INSERT_TAG_2);
        sqLiteDatabase.execSQL(INSERT_TAG_3);

        sqLiteDatabase.execSQL(INSERT_PROJECT);

        sqLiteDatabase.execSQL(INSERT_TASK_LIST_1);
        sqLiteDatabase.execSQL(INSERT_TASK_LIST_2);
        sqLiteDatabase.execSQL(INSERT_TASK_LIST_3);

        sqLiteDatabase.execSQL(INSERT_TASK);

        sqLiteDatabase.execSQL(INSERT_PROJECT_PARTICIPANT_1);
        sqLiteDatabase.execSQL(INSERT_PROJECT_PARTICIPANT_2);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {


    }
}
