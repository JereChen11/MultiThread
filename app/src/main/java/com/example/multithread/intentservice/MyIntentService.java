package com.example.multithread.intentservice;

import android.app.IntentService;
import android.content.Intent;
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

    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * 进行一些耗时操作
     * @param intent 通过startService(Intent intent)方法传入
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            String author = intent.getExtras().getString(TEST_AUTHOR);
            //模拟下载动作
            if (DOWNLOAD_ACTION.equals(action)) {
                for (int i = 0; i < 10; i++) {
                    try {
                        //线程等待1s，模拟耗时操作
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, author + " " + action + " " + i);
                }
            }
            //模拟读操作
            if (READ_ACTION.equals(action)) {
                for (int i = 0; i < 10; i++) {
                    try {
                        //线程等待2s，模拟耗时操作
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, author + " " + action + " " + i);
                }
            }
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Toast.makeText(this, "onStartCommand", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }
}
