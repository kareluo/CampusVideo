package me.xiu.xiu.campusvideo.util;

import android.text.TextUtils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 * Created by felix on 16/4/30.
 */
public class FileUtils {

    public static File obtainFile(File file) throws IOException {
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            file.createNewFile();
        }
        return file;
    }

    public static String getFileName(String path) {
        if (!TextUtils.isEmpty(path)) {
            int index = path.lastIndexOf("/");
            if (index + 1 < path.length()) {
                return path.substring(index + 1);
            }
        }
        return path;
    }

    public static boolean delete(String path) {
        return path != null && delete(new File(path));
    }

    public static boolean delete(File file) {
        return file != null && file.exists() && file.delete();
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignored) {

            }
        }
    }
}
