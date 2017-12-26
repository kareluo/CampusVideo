package me.xiu.xiu.campusvideo.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.File;
import java.util.Locale;

import me.xiu.xiu.campusvideo.BuildConfig;
import me.xiu.xiu.campusvideo.core.xml.Xml;
import me.xiu.xiu.campusvideo.dao.common.Campus;
import me.xiu.xiu.campusvideo.util.Logger;

/**
 * Created by felix on 15/9/28.
 */
public class CampusVideo {

    private static final String TAG = "CampusVideo";

    private static final Campus DEFAULT_CAMPUS = new Campus("校园视频", "vod.shou.edu.cn");

    public static Campus campus = DEFAULT_CAMPUS;

    public static String protocol = "http://";

    public static String port = "80";

    public static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String SHARED_CONFIG = "configs";

    private static final String CONFIG_CAMPUS = "Campus";

    private static final String CONFIG_HOST = "Host";

    public static final boolean ENABLE_AD = false;

    public static final String VIDEO_DIR = "Campusvideo/videos/";

    public static synchronized void init(Context context) {
        SharedPreferences shared = context.getSharedPreferences(SHARED_CONFIG, Context.MODE_PRIVATE);
        campus.name = shared.getString(CONFIG_CAMPUS, DEFAULT_CAMPUS.name);
        campus.host = shared.getString(CONFIG_HOST, DEFAULT_CAMPUS.host);
    }

    public static synchronized void update(Context context, Campus cps) {
        campus = cps;
        SharedPreferences shared = context.getSharedPreferences(SHARED_CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = shared.edit();
        edit.putString(CONFIG_CAMPUS, campus.name);
        edit.putString(CONFIG_HOST, campus.host);
        edit.commit();
    }

    public static String getUrl(String path) {
        return String.format("%s%s:%s%s", protocol, campus.host, port, path);
    }

    public static String getBanner(String path) {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return getUrl(path);
    }

    /**
     * 获取海报图片300*400
     */
    public static String getPoster(String vid) {
        return getUrl(String.format("/mov/%s/1.jpg", vid));
    }

    /**
     * 获取海报图片120*160
     */
    public static String getPoster2(String vid) {
        return getUrl(String.format("/mov/%s/2.jpg", vid));
    }

    /**
     * 获取视频详细信息
     */
    public static String getFilm(String vid) {
        return getUrl(String.format("/mov/%s/film.xml", vid));
    }

    /**
     * 获取视频剧集详细信息
     */
    public static String getEpisode(String vid) {
        return getUrl(String.format("/mov/%s/url2.xml", vid));
    }

    public static String getEpisode1(String vid) {
        return getUrl(String.format("/mov/%s/url.xml", vid));
    }

    public static String getVideoUrl(String raw_url) {
        /**
         * protocol为视频的播放协议，一般是“http://”
         * Config.target_host 为视频服务器的ip地址
         * Config.target_port 为视频服务器使用的端口，一般为80
         */

        return String.format("%s%s:%s/kuu%c/%s",
                protocol, campus.host, port, raw_url.charAt(0),
                raw_url.substring(raw_url.lastIndexOf("\\") + 1));
    }

    public static String getConfigXml(String host) {
        return String.format("%s%s%s", protocol, host, Xml.BARSET);
    }

    /**
     * 650 X 430
     */
    public static String getStillUrl(String vid, int index) {
        return getUrl(String.format(Locale.ROOT, "/mov/%s/jzd%d.jpg", vid, index));
    }

    public static File getVideoDir(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File path = Environment.getExternalStorageDirectory();
            File file = new File(path, CampusVideo.VIDEO_DIR);
            boolean mkdirs = file.mkdirs();
            if (mkdirs) {
                Logger.i(TAG, "mkdirs=true");
            }
            return file;
        } else {
            File dir = context.getExternalFilesDir(null);
            if (dir != null) {
                File file = new File(dir, "videos/");
                boolean mkdirs = file.mkdirs();
                if (mkdirs) {
                    Logger.i(TAG, "internal mkdirs=true");
                }
                return file;
            }
        }
        return context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
    }
}
