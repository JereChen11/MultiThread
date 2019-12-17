package com.example.multithread.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.multithread.R;

import java.lang.ref.WeakReference;

/**
 * @author jere
 */
public class HandlerAddThreadActivity extends AppCompatActivity {
    private TextView mDisplayTv;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_add_thread);

        TextView titleTv = findViewById(R.id.title_tv);
        titleTv.setText("Handler + Thread");

        Button mClickBtn = findViewById(R.id.click_btn);
        mDisplayTv = findViewById(R.id.display_tv);

        //mHandler用于处理主线程消息队列中的子线程消息
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    //更新UI
                    mDisplayTv.setText("Jere test: User Handler ");
                }
            }
        };

        //方法二：利用静态内部类，防止内存泄露。
//        mHandler = new MyHandler(this);

        mClickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开启子线程，子线程处理UI工作
                CustomChildThread customThread = new CustomChildThread();
                customThread.start();
            }
        });
    }

    public class CustomChildThread extends Thread {

        @Override
        public void run() {
            //在子线程中创建一个消息对象
            Message childThreadMessage = new Message();
            childThreadMessage.what = 1;
            //将该消息放入主线程的消息队列中
            mHandler.sendMessage(childThreadMessage);
        }
    }

    //静态内部类，防止内存泄露
    public static class MyHandler extends Handler {
        WeakReference<HandlerAddThreadActivity> weakReference;

        public MyHandler(HandlerAddThreadActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HandlerAddThreadActivity activity = weakReference.get();
            if (activity != null && !activity.isFinishing()) {
                if (msg.what == 1) {
                    //更新UI
                    activity.mDisplayTv.setText("Jere test: User Handler ");
                }
            }
        }
    }
}
