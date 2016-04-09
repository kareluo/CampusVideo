package me.xiu.xiu.campusvideo.common;

import me.xiu.xiu.campusvideo.BuildConfig;

/**
 * Created by felix on 15/9/28.
 */
public class CampusVideo {

    public static String protocol = "http://";
    public static String host = "vod.shou.edu.cn";
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

    /**
     * 获取视频详细信息
     *
     * @param vid
     * @return
     */
    public static String getFilm(String vid) {
        return getUrl(mov + vid + "/film.xml");
    }

    /**
     * 获取视频剧集详细信息
     *
     * @param vid
     * @return
     */
    public static String getEpisode(String vid) {
        return getUrl(mov + vid + "/url2.xml");
    }

    public static String getEpisode1(String vid) {
        return getUrl(mov + vid + "/url.xml");
    }

    public static String getVideoUrl(String raw_url) {
        /**
         * protocol为视频的播放协议，一般是“http://”
         * Config.target_host 为视频服务器的ip地址
         * Config.target_port 为视频服务器使用的端口，一般为80
         */

        return String.format("%s%s:%s/kuu%c/%s",
                protocol, host, post, raw_url.charAt(0),
                raw_url.substring(raw_url.lastIndexOf("\\") + 1));
    }
}
