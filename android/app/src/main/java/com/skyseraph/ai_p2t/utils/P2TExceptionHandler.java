package com.skyseraph.ai_p2t.utils;

/**
 * Created by skyserpah on 2018/1/1.
 */

public class P2TExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    public P2TExceptionHandler() {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        handleException(thread, ex);
        mDefaultHandler.uncaughtException(thread, ex);
    }

    /**
     * 处理异常信息
     *
     * @param thread 错误线程
     * @param ex     错误
     */
    @SuppressWarnings("unused")
    private void handleException(Thread thread, Throwable ex) {
        if (ex != null) {
            String message = ex.getMessage();
        }
    }
}