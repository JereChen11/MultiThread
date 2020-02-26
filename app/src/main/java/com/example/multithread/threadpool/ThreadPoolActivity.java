package com.example.multithread.threadpool;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.multithread.R;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jere
 */
public class ThreadPoolActivity extends AppCompatActivity {
    private static final String TAG = "ThreadPoolActivity";
    private final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private final int KEEP_ALIVE_TIME = 1;
    private final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    private BlockingQueue<Runnable> taskQueue = new LinkedBlockingDeque<Runnable>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);

//        //Executor practice
//        CustomExecutor.MyExecutor myExecutor = new CustomExecutor.MyExecutor();
//        myExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                Log.d(TAG, "MyExecutor run: " + Thread.currentThread().getId());
//            }
//        });


//        //利用Executors创建线程数量为2的定长线程池
//        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
//        for (int i = 0; i < 10; i++) {
//            final int finalI = i;
//            //向线程池提交任务
//            fixedThreadPool.execute(new Runnable() {
//                @Override
//                public void run() {
//                    Log.d(TAG, " Executors.newFixedThreadPool run: " + finalI);
//                }
//            });
//            try {
//                //让线程等待一秒
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            //当i==5时，关闭线程池
//            if (i == 5) {
//                fixedThreadPool.shutdown();
//                Log.d(TAG, "executorService shutdown.");
//                break;
//            }
//        }

//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                Log.d(TAG, "scheduledThreadPool run: ");
//            }
//        };
//        //1.创建定时线程池
//        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);
//        //2.调用schedule()方法，延时15s后向线程池中提交runnable任务
//        scheduledThreadPool.schedule(runnable, 15, TimeUnit.SECONDS);
//        //3.关闭线程池
//        scheduledThreadPool.shutdown();
//
//        /**==========================================================================*/
//
//        //1.创建线程池
//        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
//        //2.调用execute()方法向线程池中提交任务
//        cachedThreadPool.execute(new Runnable() {
//            @Override
//            public void run() {
//                Log.d(TAG, "cachedThreadPool run: ");
//            }
//        });
//        //3.关闭线程池
//        cachedThreadPool.shutdown();


        ExecutorService executorService = new ThreadPoolExecutor(NUMBER_OF_CORES,
                NUMBER_OF_CORES * 2,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                taskQueue,
                nameThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "executorService run: ");
            }
        });
        executorService.shutdown();

    }

    private ThreadFactory nameThreadFactory = new ThreadFactory() {
        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            return null;
        }
    };



//    /** 定长线程池
//     * 创建线程数量固定的线程池，线程会一直存在直到调用shutdown()才会被回收
//     * @param nThreads 定义线程池中的线程数
//     * @return （ExecutorService） 新创建的线程池
//     */
//    public static ExecutorService newFixedThreadPool(int nThreads) {
//        throw new RuntimeException("Stub!");
//    }
//
//    /** 定时线程池
//     * 创建一个线程池，可以调度命令在给定的延迟后运行，或者定期执行
//     * @param corePoolSize 即使它们处于空闲状态，也要保留在池中的线​​程数
//     * @return （ScheduledExecutorService）返回一个新创建的定时线程池
//     */
//    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
//        throw new RuntimeException("Stub!");
//    }
//
//    /** 可缓存线程池
//     * 创建一个 根据需要创建新线程 的线程池，可重用以前构造的线程（如果没有可重用的线程则重新创建一个新的线程）。
//     * 如果一个线程超过60s没被使用，就会被终止及回收，不会浪费资源，所以适用于线程数量多，但线程耗时断短的情况
//     * @return （ExecutorService） 新创建的线程池
//     */
//    public static ExecutorService newCachedThreadPool() {
//        throw new RuntimeException("Stub!");
//    }
//
//    /** 单线程化线程池
//     * 创建一个Executor，它使用一个在无界队列中运行的工作线程。
//     * 保证任务按顺序执行，并且在任何给定时间不会有多个任务处于活动状态。
//     * 保证不使用其他线程来执行程序，所以与newFixedThreadPool(1)是不同的。
//     * @return 新创建的单线程Executor
//     */
//    public static ExecutorService newSingleThreadExecutor() {
//        throw new RuntimeException("Stub!");
//    }

}
