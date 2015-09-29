package me.xiu.xiu.campusvideo.common.xml;

/**
 * Created by felix on 15/9/27.
 */
public class Xml {
    /**
     * single:
     * [0]root/root: BARSET,
     * [1]m/m: TOTAL_VIDEOS, PUBLICLASS_DATE
     * [2]img/content: HOME_BANNER, MOVIE_BANNER, TELEPLAY_BANNER
     * <p/>
     * multi:
     * [0]gkk|jlp|jz/gkk|jlp|jz: EDU_BANNER
     */

    public static final String TAG_M = "m";
    public static final String TAG_IMG = "img";
    public static final String TAG_CONTENT = "content";

    public static final String[] start = new String[]{"root", "m", "img"};
    public static final String[] end = new String[]{"root", "m", "content"};

    public static final String[][] starts = new String[][]{new String[]{"gkk", "jlp", "jz"}};
    public static final String[][] ends = new String[][]{new String[]{"gkk", "jlp", "jz"}};

    public static final String ENCODING = "GB-2312";

    public static final String BARSET = "/bar/BarSet.xml";
    // 所有视频信息列表
    public static final String TOTAL_VIDEOS = "/bar/list/Total.xml";
    // 主页的推荐视频列表
    public static final String HOME_BANNER = "/banner1/flash.xml";
    // 教育类推荐列表（公开课，讲座，纪录片）
    public static final String EDU_BANNER = "/banner1/gongkaike/xyflash.xml";
    // 电影的推荐列表
    public static final String MOVIE_BANNER = "/banner1/hrdp/flash.xml";
    // 电视剧的推荐列表
    public static final String TELEPLAY_BANNER = "/banner1/rbjc/flash.xml";

    public static final String PUBLICLASS_DATE = "/bar/list/52_adddate.xml";
    public static final String CATHEDRA_DATE = "/bar/list/54_adddate.xml";
    public static final String DOCUMENTARY_DATE = "/bar/list/53_adddate.xml";

    public static final String MOVIE_ACTION_DATE = "/bar/list/25_2_adddate.xml";
    public static final String MOVIE_COMEDY_DATE = "/bar/list/25_1_adddate.xml";
    public static final String MOVIE_LOVERS_DATE = "/bar/list/25_3_adddate.xml";
    public static final String MOVIE_FICTION_DATE = "/bar/list/25_4_adddate.xml";
    public static final String MOVIE_PANIC_DATE = "/bar/list/25_8_adddate.xml";
    public static final String MOVIE_WARFARE_DATE = "/bar/list/25_6_adddate.xml";
    public static final String MOVIE_CRIMINAL_DATE = "/bar/list/25_9_adddate.xml";
    public static final String MOVIE_FRESH_DATE = "/bar/list/25_270_adddate.xml";

    public static final String MOVIE_MAINLAND_DATE = "/bar/list/25_12_adddate.xml";
    public static final String MOVIE_FICTION_RANK = "/bar/list/25_12_click.xml";

    public static final String MOVIE_HKTW_DATE = "/bar/list/25_15_adddate.xml";
    public static final String MOVIE_HKTW_RANK = "/bar/list/25_15_click.xml";

    public static final String MOVIE_XVIDEO_DATE = "/bar/list/25_13_adddate.xml";
    public static final String MOVIE_XVIDEO_RANK = "/bar/list/25_13_click.xml";

    public static final String MOVIE_KKC_DATE = "/bar/list/25_14_adddate.xml";
    public static final String MOVIE_KKC_RANK = "/bar/list/25_14_click.xml";

    public static final String TELEPLAY_SYNC_HOT = "/bar/list/jrgx.xml";
    public static final String TELEPLAY_MAINLAND_DATE = "/bar/list/30_18_adddate.xml";
    public static final String TELEPLAY_MAINLAND_RANK = "/bar/list/30_18_click.xml";

    public static final String TELEPLAY_XVIDEO_DATE = "/bar/list/30_19_adddate.xml";
    public static final String TELEPLAY_XVIDEO_RANK = "/bar/list/30_19_click.xml";

    public static final String TELEPLAY_HKTW_DATE = "/bar/list/30_21_adddate.xml";
    public static final String TELEPLAY_HKTW_RANK = "/bar/list/30_21_adddate.xml";

    public static final String TELEPLAY_KKC_DATE = "/bar/list/30_20_adddate.xml";
    public static final String TELEPLAY_KKC_RANK = "/bar/list/30_20_click.xml";

    public static final String ANIME_DATE = "/bar/list/37_adddate.xml";
    public static final String ANIME_RANK = "/bar/list/37_click.xml";

    public static final String TVSHOW_DATE = "/bar/list/41_2_adddate.xml";
}
