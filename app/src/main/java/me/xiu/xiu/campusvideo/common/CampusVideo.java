package me.xiu.xiu.campusvideo.common;

import java.net.URL;

import me.xiu.xiu.campusvideo.BuildConfig;

/**
 * Created by felix on 15/9/28.
 */
public class CampusVideo {

    public static String protocol = "http://";
    public static String host = "vod.gs.edu.cn";
    public static String post = "80";
    private final static String colon = ":";
    private final static String mov = "/mov/";
    public static final boolean DEBUG = BuildConfig.DEBUG;

    public static String getUrl(String path) {
        return protocol + host + colon + post + path;
    }

    /**
     * 获取海报图片300*400
     *
     * @param vid
     * @return
     */
    public static String getPoster(String vid) {
        return getUrl("/mov/" + vid + "/1.jpg");
    }

    /**
     * 获取海报图片120*160
     *
     * @param vid
     * @return
     */
    public static String getPoster2(String vid) {
        return getUrl("/mov/" + vid + "/2.jpg");
    }

    public static String getFilm(String vid) {
        return getUrl(mov + vid + "/film.xml");
    }
}
