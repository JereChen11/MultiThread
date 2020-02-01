package com.example.multithread.intentservice;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.example.multithread.R;

import java.lang.ref.WeakReference;

/**
 * @author jere
 */
public class IntentServiceActivity extends AppCompatActivity {
    private ProgressBar downloadProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);

        downloadProgressBar = findViewById(R.id.download_progress_bar);

        //用于创建 Messenger，接收 IntentService 回复的消息
        MessengerHandler messengerHandler = new MessengerHandler(this);

        //模拟 Jere 做下载动作
        Intent intent = new Intent(this, MyIntentService.class);
        intent.setAction(MyIntentService.DOWNLOAD_ACTION);
        intent.putExtra(MyIntentService.TEST_AUTHOR, "Jere");
        //将 Messenger 传递给 IntentService, 使其传递消息回来，实现客户端与服务端之间进行沟通
        intent.putExtra(MyIntentService.TEST_MESSENGER, new Messenger(messengerHandler));
        startService(intent);

//        //模拟 James 做读取动作
//        Intent jamesIntent = new Intent(this, MyIntentService.class);
//        jamesIntent.setAction(MyIntentService.READ_ACTION);
//        jamesIntent.putExtra(MyIntentService.TEST_AUTHOR, "James");
//        startService(jamesIntent);

    }

    /**
     * 用于创建 Messenger 对象
     *
     * 静态内部类，防止内存泄漏
     */
    public static class MessengerHandler extends Handler {
        private WeakReference<IntentServiceActivity> weakReference;

        MessengerHandler(IntentServiceActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //msg 为 IntentService 回复的消息，包含 Bundle 等信息。
            Bundle bundle = msg.getData();
            //获取 IntentService 传递过来的 下载进度 参数
            int downloadProgressBarValue = bundle.getInt(MyIntentService.DOWNLOAD_PROGRESS_VALUE_KEY);

            //将下载进度设置成 ProgressBar 的进度，显示出来。
            IntentServiceActivity activity = weakReference.get();
            if (activity != null && !activity.isFinishing()) {
                activity.downloadProgressBar.setProgress(downloadProgressBarValue);
            }

        }
    }

}
