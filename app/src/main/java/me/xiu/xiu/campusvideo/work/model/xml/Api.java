package me.xiu.xiu.campusvideo.work.model.xml;

/**
 * Created by felix on 2017/12/25 下午7:51.
 */

public interface Api {

    String bar_set = "/bar/BarSet.xml";

    String total = "/bar/list/Total.xml";

    String film = "/mov/{videoId}/film.xml";

    String film_episode = "/mov/{videoId}/url2.xml";
}
