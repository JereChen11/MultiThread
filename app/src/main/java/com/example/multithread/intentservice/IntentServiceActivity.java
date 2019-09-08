package com.example.multithread.intentservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.multithread.R;

/**
 * @author jere
 */
public class IntentServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);

        //模拟 Jere 做下载动作
        Intent intent = new Intent(this, MyIntentService.class);
        intent.setAction(MyIntentService.DOWNLOAD_ACTION);
        intent.putExtra(MyIntentService.TEST_AUTHOR, "Jere");
        startService(intent);

        //模拟 James 做读取动作
        Intent jamesIntent = new Intent(this, MyIntentService.class);
        jamesIntent.setAction(MyIntentService.READ_ACTION);
        jamesIntent.putExtra(MyIntentService.TEST_AUTHOR, "James");
        startService(jamesIntent);

    }
}
