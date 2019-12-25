package com.example.multithread.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.multithread.R;

import java.lang.ref.WeakReference;

/**
 * @author jere
 */
public class HandlerObtainMessageActivity extends AppCompatActivity {
    private static final String TAG = "HandlerAddThreadActivity";
    public static final String CURRENT_PROCESS_KEY = "CURRENT_PROCESS";
    private TextView mDisplayTv;
    private Handler mHandler;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_add_thread);

        TextView titleTv = findViewById(R.id.title_tv);
        titleTv.setText("Handler + Thread");

        mDisplayTv = findViewById(R.id.display_tv);
        mProgressBar = findViewById(R.id.test_handler_progress_bar);

        //mHandler用于处理主线程消息队列中的子线程消息
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        //更新 TextView UI
                        mDisplayTv.setText("Handler obtainMessage() Test!!");
                        break;
                    case 2:
                        //通过 msg.obj 获取 ProgressBar 的进度，然后显示进度值
                        int process = (int) msg.obj;
                        mProgressBar.setProgress(process);
                        break;
                    default:
                        break;
                }

            }
        };

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
            //
            mHandler.obtainMessage(1).sendToTarget();

            //模拟耗时进度，将进度值传给主线程用于更新 ProgressBar 进度。
            for (int i = 1; i <= 5; i++) {
                try {
                    //让当前执行的线程（即 CustomChildThread）睡眠 1s
                    Thread.sleep(1000);

                    //将 i 传递给主线程 progressBar
                    mHandler.obtainMessage(2, i).sendToTarget();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }
    }

}
