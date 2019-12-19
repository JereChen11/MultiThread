package com.example.multithread;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.multithread.asynctask.AsyncTaskActivity;
import com.example.multithread.handler.HandlerAddThreadActivity;
import com.example.multithread.handlerthread.HandlerThreadActivity;
import com.example.multithread.intentservice.IntentServiceActivity;
import com.example.multithread.multiAsyncTask.TestMultipleAsyncTask;

/**
 * @author jere
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button handlerBtn = findViewById(R.id.handler_btn);
        handlerBtn.setOnClickListener(this);
        Button handlerThreadBtn = findViewById(R.id.handler_thread_btn);
        handlerThreadBtn.setOnClickListener(this);
        Button asyncTaskBtn = findViewById(R.id.async_task_btn);
        asyncTaskBtn.setOnClickListener(this);
        Button intentServiceBtn = findViewById(R.id.intent_service_btn);
        intentServiceBtn.setOnClickListener(this);
        Button multiAsyncTaskBtn = findViewById(R.id.multi_async_task_btn);
        multiAsyncTaskBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.thread_btn:
                Intent intent = new Intent(this, ExtendThreadClass.class);
                startActivity(intent);
                break;
            case R.id.handler_btn:
                Intent handlerIntent = new Intent(this, HandlerAddThreadActivity.class);
                startActivity(handlerIntent);
                break;
            case R.id.handler_thread_btn:
                Intent handlerThreadIntent = new Intent(this, HandlerThreadActivity.class);
                startActivity(handlerThreadIntent);
                break;
            case R.id.async_task_btn:
                Intent asyncTaskIntent = new Intent(this, AsyncTaskActivity.class);
                startActivity(asyncTaskIntent);
                break;
            case R.id.intent_service_btn:
                Intent intentServiceIntent = new Intent(this, IntentServiceActivity.class);
                startActivity(intentServiceIntent);
                break;
            case R.id.multi_async_task_btn:
                Intent multiAsyncTaskIntent = new Intent(this, TestMultipleAsyncTask.class);
                startActivity(multiAsyncTaskIntent);
                break;
            default:
                break;
        }
    }
}
