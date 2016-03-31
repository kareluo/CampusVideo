package me.xiu.xiu.campusvideo.work.viewer.fragment;

import java.util.List;

import me.xiu.xiu.campusvideo.common.Viewer;
import me.xiu.xiu.campusvideo.work.model.HomeBanner;
import me.xiu.xiu.campusvideo.work.model.video.VideoSeries;

/**
 * Created by felix on 16/3/20.
 */
public interface HomeViewer extends Viewer {

    void onUpdateBanner(List<HomeBanner> banners);

    void onUpdateVideoSeries(List<VideoSeries> videoSeries);

}
