package com.example.multithread.intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * @author jere
 */
public class MyIntentService extends IntentService {

    private static final String TAG = "MyIntentService";
    public static final String DOWNLOAD_ACTION = "DOWNLOAD_ACTION";
    public static final String READ_ACTION = "READ_ACTION";
    public static final String TEST_AUTHOR = "TEST_AUTHOR";
    public static final String TEST_MESSENGER = "TEST_MESSENGER";
    public static final String DOWNLOAD_PROGRESS_VALUE_KEY = "DOWNLOAD_PROGRESS_VALUE";

    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * 进行一些耗时操作
     * @param intent 通过startService(Intent intent)方法传入
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(TAG, "onHandleIntent: ");
        if (intent != null) {
            final String action = intent.getAction();
            String author = intent.getExtras().getString(TEST_AUTHOR);

            //模拟下载动作
            if (DOWNLOAD_ACTION.equals(action)) {
                for (int i = 0; i <= 6; i++) {
                    try {
                        //线程等待1s，模拟耗时操作
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.e(TAG, author + " " + action + " " + i);
                    //获取从 Activity 传入的 Messenger
                    Messenger messenger = (Messenger) intent.getExtras().get(TEST_MESSENGER);
                    //新建消息，设置下载进度参数
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putInt(DOWNLOAD_PROGRESS_VALUE_KEY, i);
                    msg.setData(bundle);
                    try {
                        //回复消息
                        messenger.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
            //模拟读操作
            if (READ_ACTION.equals(action)) {
                for (int i = 0; i < 5; i++) {
                    try {
                        //线程等待2s，模拟耗时操作
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.e(TAG, author + " " + action + " " + i);
                }
            }
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Toast.makeText(this, "onStartCommand", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "onStart: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }

}
