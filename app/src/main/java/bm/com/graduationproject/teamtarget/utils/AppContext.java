package bm.com.graduationproject.teamtarget.utils;

import android.app.Application;

/**
 * Created by bm on 2015/5/14.
 */
public class AppContext extends Application {
    private int userId;
    private String userName;


    private static  AppContext instance;

    public AppContext() {
    }

    public AppContext(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    public static AppContext getInstance(){
        return instance;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
