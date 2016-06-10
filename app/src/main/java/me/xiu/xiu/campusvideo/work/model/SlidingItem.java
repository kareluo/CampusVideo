package me.xiu.xiu.campusvideo.work.model;

/**
 * Created by felix on 16/3/28.
 */
public enum SlidingItem {
    OFFLINE("离线", 0, Type.ITEM),
    MEDIA("本地视频", 0, Type.ITEM),
    VIDEO_CATE("视频分类", 0, Type.GROUP),
    HOME("主页", 0, Type.ITEM),
    PUBLIC_CLASS("公开课", 0, Type.ITEM),
    DOCUMENTARY("纪录片", 0, Type.ITEM),
    CATHEDRA("讲座", 0, Type.ITEM),
    MOVIE("电影", 0, Type.ITEM),
    TELEPLAY("电视剧", 0, Type.ITEM),
    ANIME("动漫", 0, Type.ITEM),
    TV_SHOW("综艺", 0, Type.ITEM),
    OTHER("其他", 0, Type.GROUP),
    SETTING("设置", 0, Type.ITEM);

    public int icon;
    public int type;
    public String title;

    SlidingItem(String title, int ic, int type) {
        this.title = title;
        this.icon = ic;
        this.type = type;
    }

    public interface Type {
        int GROUP = 0;
        int ITEM = 1;

        // Type的总数
        int COUNT = 2;
    }
}
