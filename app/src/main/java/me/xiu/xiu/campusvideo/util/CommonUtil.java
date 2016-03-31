package me.xiu.xiu.campusvideo.util;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import io.vov.vitamio.utils.Log;

/**
 * Created by felix on 16/3/18.
 */
public class CommonUtil {
    private static final String TAG = "CommonUtil";

    public static void mkdirs(File file) {
        if (file != null && !file.exists()) {
            if (file.isDirectory()) {
                file.mkdirs();
            } else {
                file.getParentFile().mkdirs();
            }
        }
    }

    public static void mkfile(File file) throws IOException {
        mkdirs(file);
        if (file != null && !file.exists()) {
            file.createNewFile();
        }
    }

    public static String hashName(String text) {
        return new BigInteger(text.getBytes()).toString(Character.MAX_RADIX);
    }

    public static void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Log.e(TAG, e);
            }
        }
    }

    public static int sum(int[] values) {
        int sum = 0;
        for (int v : values) {
            sum += v;
        }
        return sum;
    }

    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }
}
