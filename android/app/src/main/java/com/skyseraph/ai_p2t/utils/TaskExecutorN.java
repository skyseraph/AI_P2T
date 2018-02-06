package com.skyseraph.ai_p2t.utils;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskExecutorN {
    private static ExecutorService sThreadPoolExecutor = null;
    private static Handler sMainHandler = null;

    public static void runTask(Runnable task) {
        ensureThreadPoolExecutor();
        sThreadPoolExecutor.submit(task);
    }

    public static void runTaskSynchronized(final Object synchronizedTarget, final Runnable task) {
        ensureThreadPoolExecutor();
        sThreadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                synchronized (synchronizedTarget) {
                    task.run();
                }
            }
        });
    }

    //主线程->主线程
    public static void runTask(final View targetView, Runnable task, final Runnable onLayoutComplete) {
        targetView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                targetView.removeOnLayoutChangeListener(this);
                onLayoutComplete.run();
            }
        });
        task.run();
    }

    public static void scheduleTaskOnMainThread(long delay, Runnable task) {
        ensureMainHandler();
        sMainHandler.postDelayed(task, delay);
    }

    public static void runTaskOnMainThread(Runnable task) {
        ensureMainHandler();
        sMainHandler.post(task);
    }

    public static void runTaskOnMainThread(int delay, Runnable task) {
        ensureMainHandler();
        sMainHandler.postDelayed(task, delay);
    }

    public static void runTaskOnMainThreadDelay(int msec, Runnable task) {
        ensureMainHandler();
        sMainHandler.postDelayed(task, msec);
    }

    public static void runTaskInMainThread(Runnable runnable) {
        if (isMainThread()) {
            runnable.run();
        } else {
            runTaskOnMainThread(runnable);
        }
    }

    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    private synchronized static void ensureMainHandler() {
        if (sMainHandler == null) {
            sMainHandler = new Handler(Looper.getMainLooper());
        }
    }

    private synchronized static void ensureThreadPoolExecutor() {
        if (sThreadPoolExecutor == null) {
            sThreadPoolExecutor = Executors.newFixedThreadPool(5);
        }
    }
}
