package me.xiu.xiu.campusvideo.util;

import android.util.Log;

import me.xiu.xiu.campusvideo.BuildConfig;
import me.xiu.xiu.campusvideo.common.CampusVideo;

/**
 * Created by felix on 15/9/18.
 */
public class Logger {

    private static final boolean DEBUG = CampusVideo.DEBUG;

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }
}
