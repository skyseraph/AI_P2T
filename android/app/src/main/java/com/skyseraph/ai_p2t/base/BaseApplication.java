package com.skyseraph.ai_p2t.base;

import android.app.Application;
import android.content.Context;

import com.skyseraph.ai_p2t.utils.P2TExceptionHandler;

/**
 * Created by skyserpah on 2018/1/1.
 */

public class BaseApplication extends Application {

    private static Context sAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = this;
        Thread.setDefaultUncaughtExceptionHandler(new P2TExceptionHandler());
    }

    public static Context getContext() {
        return sAppContext;
    }
}
