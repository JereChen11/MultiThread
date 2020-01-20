package com.example.multithread.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.multithread.R;

import java.lang.ref.WeakReference;
import java.util.Arrays;

/**
 * @author jere
 */
public class AsyncTaskActivity extends AppCompatActivity implements View.OnClickListener {
    private ProgressBar progressBar;
    private TextView displayTv;
    private Button startBtn, stopBtn;
    private MyAsyncTask myAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        progressBar = findViewById(R.id.progress_bar);
        displayTv = findViewById(R.id.display_tv);
        startBtn = findViewById(R.id.start_btn);
        stopBtn = findViewById(R.id.stop_btn);
        myAsyncTask = new MyAsyncTask(this);

        startBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_btn:
                myAsyncTask.execute("Jere test");
                //AsyncTask只能执行一次，如果你在AsyncTask执行过程中再次调用 execute() 执行它，就会跳IllegalStateException异常
                //java.lang.IllegalStateException: Cannot execute task: the task is already running.
                startBtn.setEnabled(false);
                break;
            case R.id.stop_btn:
                //中断线程执行
                myAsyncTask.cancel(true);
                break;
            default:
                break;
        }
    }

    /**
     * 创建MyAsyncTask类, 继承AsyncTask类 -> AsyncTask<Params, Progress, Result>
     * 3个泛型参数指定类型:
     * Params（输入参数）: String类型
     * Progress（执行进度）: Integer类型
     * Result（执行结果）: String类型
     */
    private static class MyAsyncTask extends AsyncTask<String, Integer, String> {
        private WeakReference<AsyncTaskActivity> asyncTaskActivityWeakReference;

        MyAsyncTask(AsyncTaskActivity asyncTaskActivity) {
            asyncTaskActivityWeakReference = new WeakReference<>(asyncTaskActivity);
        }

        /**
         * 在后台任务开始计算执行前执行onPreExecute()操作
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AsyncTaskActivity asyncTaskActivity = asyncTaskActivityWeakReference.get();
            //只有在 AsyncTaskActivity 没被销毁的时候才显示 displayTv 及 Toast.
            if (asyncTaskActivity != null && !asyncTaskActivity.isFinishing()) {
                asyncTaskActivity.displayTv.setText("onPreExecute: start! ");
                Toast.makeText(asyncTaskActivity, "onPreExecute", Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * 接收输入参数，执行后台任务中的计算工作（耗时操作），计算结束，返回计算结果
         * @param strings Params输入参数，在主线程执行execute()传入的参数
         * @return 返回执行结果给onPostExecute()
         */
        @Override
        protected String doInBackground(String... strings) {
            for (int i = 1; i <= 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //通过调用publishProgress()方法，将执行进度传递给onProgressUpdate()
                publishProgress(i);
                if (isCancelled()) {
                    break;
                }
            }

            String result;
            result = Arrays.toString(strings) + " return from doInBackground";
            return result;
        }

        /**
         * 接收后台计算执行进度，在UI线程中通过ProgressBar现实执行进度。
         * @param values ： 执行进度，通过publishProgress()传入
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            AsyncTaskActivity asyncTaskActivity = asyncTaskActivityWeakReference.get();
            //只有在 AsyncTaskActivity 没被销毁的时候才显示 progressBar 及 displayTv.
            if (asyncTaskActivity != null && !asyncTaskActivity.isFinishing()) {
                //得到一个整型数组，但每次里面只有一个元素，所有通过values[0]就可以拿到当前的执行进度
                asyncTaskActivity.progressBar.setProgress(values[0]);
                asyncTaskActivity.displayTv.setText("onProgressUpdate: value = " + values[0]);
            }
        }

        /**
         * 接收后台任务计算结束返回的结果，在UI线程上显示出来
         * @param s ：后台计算结束返回的执行结果，由doInBackground()返回
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            AsyncTaskActivity asyncTaskActivity = asyncTaskActivityWeakReference.get();
            //只有在 AsyncTaskActivity 没被销毁的时候才显示 displayTv 及 Toast.
            if (asyncTaskActivity != null && !asyncTaskActivity.isFinishing()) {
                asyncTaskActivity.displayTv.setText("onPostExecute: " + s);
                Toast.makeText(asyncTaskActivity, "onPostExecute", Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * 在主线程中调用cancel()方法，执行onCancelled()，取消执行后台任务
         *
         * 当任务计算完成时，无法取消任务，或者已经取消任务后不可再次取消
         */
        @Override
        protected void onCancelled() {
            super.onCancelled();
            AsyncTaskActivity asyncTaskActivity = asyncTaskActivityWeakReference.get();
            //只有在 AsyncTaskActivity 没被销毁的时候才显示 progressBar displayTv Toast.
            if (asyncTaskActivity != null && !asyncTaskActivity.isFinishing()) {
                asyncTaskActivity.displayTv.setText("onCancelled");
                asyncTaskActivity.progressBar.setProgress(0);
                Toast.makeText(asyncTaskActivity, "onCancelled", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
