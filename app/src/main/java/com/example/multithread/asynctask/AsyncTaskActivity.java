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
        myAsyncTask = new MyAsyncTask();

        startBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_btn:
                myAsyncTask.execute("Jere test");
                //AsyncTask can only be executed once.
                //so we need to block user click startBtn if AsyncTask had executed.
                startBtn.setEnabled(false);
                break;
            case R.id.stop_btn:
                myAsyncTask.cancel(true);

                break;
            default:
                break;
        }
    }

    /**
     * 创建AsyncTask子类
     *
     * 继承AsyncTask类 -> AsyncTask<Params, Progress, Result>
     * 3个泛型参数指定类型；若不使用，可用java.lang.Void类型代替
     * Params（输入参数）:
     * Progress（执行进度）:
     * Result（执行结果）:
     * 此处指定为：输入参数 = String类型、执行进度 = Integer类型、执行结果 = String类型
     */
    private class MyAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            displayTv.setText("onPreExecute: start! ");
            Toast.makeText(AsyncTaskActivity.this, "onPreExecute", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... strings) {
            for (int i = 1; i <= 7; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
            }

            return " 'Result' return from doInBackground!";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            displayTv.setText("onPostExecute finished, " + s);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            displayTv.setText("onCancelled");
            progressBar.setProgress(0);
        }

    }

}
