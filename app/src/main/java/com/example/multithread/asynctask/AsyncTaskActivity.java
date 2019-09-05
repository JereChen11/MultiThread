package com.example.multithread.asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.multithread.R;

/**
 * @author jere
 */
public class AsyncTaskActivity extends AppCompatActivity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        progressBar = findViewById(R.id.progress_bar);

        final MyAsyncTask myAsyncTask = new MyAsyncTask();

        Button startBtn = findViewById(R.id.start_progress_bar_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAsyncTask.execute("click start btn");
            }
        });
    }

    private class MyAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            for (int i = 1; i <= 5; i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressBar.setProgress(i);
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            for (int i = 1; i <= 2; i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return "jere test return from doInBackground!";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(AsyncTaskActivity.this, "onPostExecute", Toast.LENGTH_SHORT).show();
        }
    }

}
