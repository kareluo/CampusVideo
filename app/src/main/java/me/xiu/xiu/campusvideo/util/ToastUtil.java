package me.xiu.xiu.campusvideo.util;

import android.content.Context;
import android.widget.Toast;

import me.xiu.xiu.campusvideo.common.CampusVideo;

/**
 * Created by felix on 15/9/29.
 */
public class ToastUtil {

    private static final boolean DEBUG = CampusVideo.DEBUG;

    public static void show(Context c, String message) {
        if (!DEBUG) {
            return;
        }
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context c, String message, boolean release) {
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }
}
