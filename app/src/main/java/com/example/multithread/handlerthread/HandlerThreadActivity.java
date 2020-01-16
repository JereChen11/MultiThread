package com.example.multithread.handlerthread;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.multithread.R;

/**
 * @author jere
 */
public class HandlerThreadActivity extends AppCompatActivity {
    private final int SET_PROGRESS_BAR_1 = 1;
    private final int SET_PROGRESS_BAR_2 = 2;
    private HandlerThread myHandlerThread;
    private Handler mWorkHandler, mMainHandler;
    private ProgressBar progressBar1, progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread);

        TextView titleTv = findViewById(R.id.title_tv);
        titleTv.setText("HandlerThread");

        progressBar1 = findViewById(R.id.progress_bar_1);
        progressBar2 = findViewById(R.id.progress_bar_2);
        Button startBtn1 = findViewById(R.id.start_progress_bar_1_btn);
        Button startBtn2 = findViewById(R.id.start_progress_bar_2_btn);

        //创建HandlerThread对象
        myHandlerThread = new HandlerThread("myHandlerThread");
        //启动线程
        myHandlerThread.start();

        //创建主线程Handler，关联APP的主Looper对象
        mMainHandler = new Handler(getMainLooper());

        //创建工作线程Handler，关联HandlerThread的Looper对象，所以它无法与主线程通讯
        mWorkHandler = new Handler(myHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {
                    case SET_PROGRESS_BAR_1:
                        //设置Progress Bar 1
                        for (int i = 1; i <= 4; i++) {
                            try {
                                //模拟耗时工作
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            final int progressSchedule = i;
                            //在工作线程中，通过主线程Handler, 传递信息给主线程，通知主线程处理UI工作。
                            mMainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar1.setProgress(progressSchedule);
                                }
                            });
                        }
                        break;
                    case SET_PROGRESS_BAR_2:
                        //设置Progress Bar 2
                        for (int i = 1; i <= 4; i++) {
                            try {
                                //模拟耗时工作
                                Thread.sleep(1300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            final int progressSchedule2 = i;
                            //在工作线程中，通过主线程Handler, 传递信息给主线程，通知主线程处理UI工作。
                            mMainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar2.setProgress(progressSchedule2);
                                }
                            });
                        }
                        break;
                    default:
                        break;
                }
            }
        };

        startBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //通过工作线程Handler，mWorkHandler发送处理 progress bar 1 的信息给工作线程。
                Message msg = new Message();
                msg.what = SET_PROGRESS_BAR_1;
                mWorkHandler.sendMessage(msg);
            }
        });

        startBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //通过工作线程Handler mWorkHandler发送处理 progress bar 2 的信息给工作线程。
                Message msg = new Message();
                msg.what = SET_PROGRESS_BAR_2;
                mWorkHandler.sendMessage(msg);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //
        myHandlerThread.quit();
        // // 防止工作线程Handler内存泄露，所以清空其关联的Looper对象中的消息队列
        mWorkHandler.removeCallbacks(null);
    }
}
