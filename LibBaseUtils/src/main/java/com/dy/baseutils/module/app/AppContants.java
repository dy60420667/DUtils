package com.dy.baseutils.module.app;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

/**
 * Descripty:
 * Auth:  邓渊  dymh21342@163.com
 * Date: 2018/2/1.15:14
 */

public class AppContants {
    public static Application app;
    public static Handler mMainHandler;

    public static void init(Application app){
        AppContants.app = app;
        mMainHandler = new Handler(Looper.myLooper());
    }

    public static void runOnUiThread(Runnable runnable){
        mMainHandler.post(runnable);
    }
}
