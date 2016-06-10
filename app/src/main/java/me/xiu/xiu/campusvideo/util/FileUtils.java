package me.xiu.xiu.campusvideo.util;

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

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {

            }
        }
    }
}
