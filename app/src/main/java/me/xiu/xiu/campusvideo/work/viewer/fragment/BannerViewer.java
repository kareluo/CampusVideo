package me.xiu.xiu.campusvideo.work.viewer.fragment;

import java.util.List;

import me.xiu.xiu.campusvideo.common.Viewer;
import me.xiu.xiu.campusvideo.work.model.HomeBanner;
import me.xiu.xiu.campusvideo.work.model.video.VInfo;

/**
 * Created by felix on 16/4/4.
 */
public interface BannerViewer extends Viewer {

    String KEY_BANNER = "BANNER";
    String KEY_VIDEOS = "VIDEOS";


    void onUpdateBanner(List<HomeBanner> banners);

    void onUpdateVideos(List<VInfo> infos);
}
