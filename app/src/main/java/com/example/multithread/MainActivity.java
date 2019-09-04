package com.example.multithread;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.multithread.handler.HandlerAddThreadActivity;
import com.example.multithread.handlerthread.HandlerThreadActivity;

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
            default:
                break;
        }
    }
}
