package com.example.multithread.threadpool;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * @author jere
 */
public class CustomExecutor {
    /**
     * 任务在除调用者线程之外的某个线程中执行。
     */
    public static class MyExecutor implements Executor {

        @Override
        public void execute(@NonNull Runnable runnable) {
            //为每个任务生成一个新线程
            new Thread(runnable).start();
        }
    }

    /**
     * Executor 立即在调用者的线程中运行提交的任务
     */
    public static class DirectExecutor implements Executor {

        @Override
        public void execute(@NonNull Runnable runnable) {
            //executor立即在调用者的线程中运行提交的任务
            runnable.run();
        }
    }
}
