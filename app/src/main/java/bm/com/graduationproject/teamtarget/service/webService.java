package bm.com.graduationproject.teamtarget.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

        if(thread.isAlive()){
                Log.i(TAG,"thread is already start");
        }else{
            thread.start();
        }

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
        if(socket.isConnected()){
            Log.i(TAG,"socket is connected");
            InputStream is=null;

           OutputStream os=null;

            //call server continue
            while(runFlag){
                try{
                    //set message to server
                    os=socket.getOutputStream();
                    String str="1&1&1";
                    os.write(str.getBytes());
                    os.flush();

                    //receive message from server
                    is=socket.getInputStream();
                    byte[] resp=new byte[100];
                    is.read(resp);
                    String res=new String(resp).trim();

                    String message=new String(res.getBytes("UTF-8"),"UTF-8");
                    System.out.println("中文:"+res);

                    Log.i(TAG, res);

                    runFlag=false;


                }catch (Exception e){
                    e.printStackTrace();
                    if(is!=null){

                        try {
                            is.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }

                    if(os!=null){

                        try {
                            os.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }

                }

            }

        }
    }

    private Socket getSocket(){
        if(socket==null){
            //the server ip which needs to be changed every time
            String ip="192.168.1.106";
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

    private void updateComment(String str){
        String[] content=str.split("&");

    }
}
