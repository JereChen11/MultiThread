package com.example.multithread.handler;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.multithread.R;

import java.lang.ref.WeakReference;

/**
 * @author jere
 */
public class HandlerPostFunctionActivity extends AppCompatActivity {
    private Handler mMainHandler;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_add_thread);

        TextView titleTv = findViewById(R.id.title_tv);
        titleTv.setText("Handler post() function");

        mProgressBar = findViewById(R.id.test_handler_progress_bar);

//        /**
//         * 将可运行的 Runnable 添加到消息队列。Runnable 将在该 Handler 相关的线程上运行处理。
//         * The runnable will be run on the thread to which this handler is attached.
//         */
//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//                //do UI work.
//            }
//        });

        //新建静态内部类 Handler 对象
        mMainHandler = new Handler(getMainLooper());

        Button mClickBtn = findViewById(R.id.click_btn);
        mClickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开启子线程，子线程处理UI工作
                CustomChildThread customThread = new CustomChildThread();
                customThread.start();
            }
        });
    }

    /**
     * 子线程，用于处理耗时工作
     */
    public class CustomChildThread extends Thread {

        @Override
        public void run() {
            //模拟耗时进度，将进度值传给主线程用于更新 ProgressBar 进度。
            for (int i = 1; i <= 5; i++) {
                try {
                    //让当前执行的线程（即 CustomChildThread）睡眠 1s
                    Thread.sleep(1000);

                    //新创建一个 Runnable 用户处理 UI 工作
                    MyRunnable runnable = new MyRunnable(HandlerPostFunctionActivity.this, i);
                    //调用Handler post 方法。
                    mMainHandler.post(runnable);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将 Runnable 写成静态内部类，防止内存泄露
     */
    public static class MyRunnable implements Runnable {
        private int progressBarValue;
        private WeakReference<HandlerPostFunctionActivity> weakReference;

        MyRunnable(HandlerPostFunctionActivity activity, int value) {
            this.weakReference = new WeakReference<>(activity);
            this.progressBarValue = value;
        }

        @Override
        public void run() {
            HandlerPostFunctionActivity activity = weakReference.get();
            if (activity != null && !activity.isFinishing()) {
                activity.mProgressBar.setProgress(progressBarValue);
            }
        }
    }
}
