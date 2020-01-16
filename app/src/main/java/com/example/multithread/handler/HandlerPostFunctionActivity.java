package com.example.multithread.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
    public static final String CURRENT_PROCESS_KEY = "CURRENT_PROCESS";
    private TextView mDisplayTv;
    private Handler mHandler;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_add_thread);

        TextView titleTv = findViewById(R.id.title_tv);
        titleTv.setText("Handler post() function");

        mDisplayTv = findViewById(R.id.display_tv);
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
        mHandler = new MyHandler(this);

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
            //在子线程中创建一个消息对象
            Message childThreadMessage = new Message();
            childThreadMessage.what = 1;
            //将该消息放入主线程的消息队列中
            mHandler.sendMessage(childThreadMessage);

            //模拟耗时进度，将进度值传给主线程用于更新 ProgressBar 进度。
            for (int i = 1; i <= 5; i++) {
                try {
                    //让当前执行的线程（即 CustomChildThread）睡眠 1s
                    Thread.sleep(1000);

                    //新创建一个 Runnable 用户处理 UI 工作
                    MyRunnable runnable = new MyRunnable(HandlerPostFunctionActivity.this, i);
                    //调用Handler post 方法。
                    mHandler.post(runnable);
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

        public MyRunnable(HandlerPostFunctionActivity activity, int value) {
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

    /**
     * 将 Handler 写成静态内部类，防止内存泄露
     */
    public static class MyHandler extends Handler {
        WeakReference<HandlerPostFunctionActivity> weakReference;

        public MyHandler(HandlerPostFunctionActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HandlerPostFunctionActivity activity = weakReference.get();
            if (activity != null && !activity.isFinishing()) {
                switch (msg.what) {
                    case 1:
                        //更新 TextView UI
                        activity.mDisplayTv.setText("Handler Post() Runnable Test!!");
                        break;
                    case 2:
                        //获取 ProgressBar 的进度，然后显示进度值
                        Bundle bundle = msg.getData();
                        int process = bundle.getInt(CURRENT_PROCESS_KEY);
                        activity.mProgressBar.setProgress(process);
                        break;
                    default:
                        break;
                }
            }

        }
    }
}