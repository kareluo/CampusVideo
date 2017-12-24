package me.xiu.xiu.campusvideo.core.net.service;

import me.xiu.xiu.campusvideo.core.xml.Xml;
import me.xiu.xiu.campusvideo.work.model.xml.TotalVideo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by felix on 2017/11/9 下午8:36.
 */

public interface XmlService {

    @GET
    <T> Call<T> fetch(@Url String url);

    @GET()
    Call<TotalVideo> fetchTotal(@Url String url);

//    @GET(Xml.TOTAL_VIDEOS)
    Call<TotalVideo> fetchTotalVideos();
}
