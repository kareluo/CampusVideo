package me.xiu.xiu.campusvideo.work.presenter.fragment;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.common.xml.Xml;
import me.xiu.xiu.campusvideo.common.xml.XmlObject;
import me.xiu.xiu.campusvideo.common.xml.XmlParser;
import me.xiu.xiu.campusvideo.util.CommonUtil;
import me.xiu.xiu.campusvideo.work.model.HomeBanner;
import me.xiu.xiu.campusvideo.work.model.video.VInfo;
import me.xiu.xiu.campusvideo.work.model.video.VideoInfo;
import me.xiu.xiu.campusvideo.work.model.video.VideoSeries;
import me.xiu.xiu.campusvideo.work.viewer.fragment.HomeViewer;

/**
 * Created by felix on 16/3/20.
 */
public class HomePresenter extends Presenter<HomeViewer> {

    private static final int MAX_BANNER = 15;
    private static final int MAX_ITEMS = 8;

    public HomePresenter(HomeViewer viewer) {
        super(viewer);
    }

    public void loadBanner() {
        subscribe(XmlParser.parseMesses(Xml.HOME_BANNER,
                Xml.TAG_IMG, MAX_BANNER, new XmlParser.ParseSubscription<XmlObject>() {
                    @Override
                    public void onResult(XmlObject result) {
                        Bundle[] elements = result.getElements();
                        if (!CommonUtil.isEmpty(elements)) {
                            List<HomeBanner> banners = new ArrayList<>();
                            for (Bundle element : elements) {
                                banners.add(new HomeBanner(element));
                            }
                            getViewer().onUpdateBanner(banners);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                    }
                }));
    }

    public void loadVideoSeries() {
        subscribe(XmlParser.parseMesses(Xml.MOVIE_BANNER, Xml.TAG_IMG, MAX_ITEMS,
                new XmlParser.ParseSubscription<XmlObject>() {
                    @Override
                    public void onResult(XmlObject result) {
                        List<VideoSeries> videoSeries = new ArrayList<>();
                        VideoSeries series = obtainMovieSeries(result);
                        if (series != null) {
                            videoSeries.add(series);
                        }
                        getViewer().onUpdateVideoSeries(videoSeries);
                    }
                }));
    }

    private VideoSeries obtainMovieSeries(XmlObject result) {
        if (result != null && !result.isEmpty()) {
            List<VInfo> vInfos = new ArrayList<>();
            Bundle[] elements = result.getElements();
            for (Bundle element : elements) {
                VideoInfo info = new VideoInfo();
                info.setVid(element.getString("url"));
                info.setName(element.getString("title"));
                info.setDescription(element.getString("content"));
                vInfos.add(info);
            }
            return new VideoSeries("电影", vInfos);
        }
        return null;
    }
}
