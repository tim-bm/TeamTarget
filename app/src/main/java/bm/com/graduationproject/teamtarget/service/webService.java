package bm.com.graduationproject.teamtarget.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by bm on 2015/5/19.
 */
public class WebService extends Service {

    private static Binder mBinder;
    private static String TAG = "WakeService";
    private Thread mThread = null;
    private boolean runFlag = true;
    private static Socket socket=null;



    class MyBinder extends Binder {
        public Service getLocalService(){
            return WebService.this;
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        if(mBinder==null){
            mBinder=new MyBinder();
        }
        return mBinder;
    }

    @Override
    public void onDestroy() {
        runFlag=false;
        super.onDestroy();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Thread thread=getThread();




        return super.onStartCommand(intent, flags, startId);


    }
    private Thread getThread(){
        if(mThread==null){
            mThread=new Thread(new Runnable() {
                @Override
                public void run() {
                    //request for server
                    doRequest();
                }
            });
        }
        return  mThread;
    }

    //doNotification

    private void doRequest(){
        Socket socket=getSocket();
    }

    private Socket getSocket(){
        if(socket==null){
            //the server ip which needs to be changed every time
            String ip="192.168.1.103";
            int port=10000;
            try{

                socket=new Socket(ip,port);
            }catch (UnknownHostException e){
                e.printStackTrace();

            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return  socket;
    }
}
