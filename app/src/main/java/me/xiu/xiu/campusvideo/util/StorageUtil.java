package me.xiu.xiu.campusvideo.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by felix on 15/9/29.
 */
public class StorageUtil {

    private static final String APPDIR = "Campusvideo";
    private static final String XMLDIR = "xmls";

    public static File sdcard(Context c) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory();
        }
        return null;
    }

    public static File xmlCacheDir(Context c) {
        File file = new File(c.getExternalCacheDir(), XMLDIR);
        if (!file.exists() && !file.mkdir()) {
            return null;
        }
        return file;
    }

    public static File appDir(Context c) {
        File sdcard = sdcard(c);
        if (sdcard == null) {
            return null;
        }
        File file = new File(sdcard, APPDIR);
        if (!file.exists() && !file.mkdir()) {
            return null;
        }
        return file;
    }

}
