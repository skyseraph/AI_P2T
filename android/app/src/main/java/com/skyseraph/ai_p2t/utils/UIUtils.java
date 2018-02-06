package com.skyseraph.ai_p2t.utils;

import android.content.Context;
import android.widget.Toast;

import com.skyseraph.ai_p2t.base.BaseApplication;


public class UIUtils {

    /**
     * The constant toast.
     */
    public static Toast toast;

    /**
     * Show toast.
     *
     * @param msg the msg
     */
    public static void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    /**
     * Show toast.
     *
     * @param msg      the msg
     * @param duration the duration
     */
    public static void showToast(String msg, int duration) {
        if (toast == null) {
            toast = Toast.makeText(getContext(), "", duration);
        }
        toast.setText(msg);
        toast.show();
    }

    /**
     * 得到上下文
     *
     * @return context context
     */
    public static Context getContext() {
        return BaseApplication.getContext();
    }

}