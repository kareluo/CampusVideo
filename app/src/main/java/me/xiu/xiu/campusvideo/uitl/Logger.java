package me.xiu.xiu.campusvideo.uitl;

import android.util.Log;

import me.xiu.xiu.campusvideo.BuildConfig;

/**
 * Created by felix on 15/9/18.
 */
public class Logger {

    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, msg);
        }
    }
}
