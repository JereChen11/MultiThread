package com.example.multithread.multiAsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.multithread.R;

/**
 * @author jere
 */
public class TestMultipleAsyncTask extends AppCompatActivity {

    private static final String TAG = "TestMultipleAsyncTask";
    private int mTestI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_multiple_async_task);

        for (int i = 0; i < 10; i++) {
            print(i);
        }
    }

    private void print(int i) {
        Log.d(TAG, "print: i = " + i);
        mTestI = i;
        MyRunnable myRunnable = new MyRunnable(i);
        new Thread(myRunnable).start();
    }

    /**
     * 方法一：测试 mTestI是否可控
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "run: mTestI = " + mTestI);
        }
    };

    /**方法二：测试 mI 是否可控 **/
    public class MyRunnable implements Runnable {
        int mI;

        public MyRunnable(int i) {
            mI = i;
        }

        @Override
        public void run() {
            Log.d(TAG, "run: mI = " + mI);
        }
    }
}
