package me.xiu.xiu.campusvideo.work.presenter.fragment;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.common.xml.Xml;
import me.xiu.xiu.campusvideo.common.xml.XmlObject;
import me.xiu.xiu.campusvideo.common.xml.XmlParser;
import me.xiu.xiu.campusvideo.util.CommonUtil;
import me.xiu.xiu.campusvideo.util.ValueUtil;
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

    private static final String[] TITLES = {"公开课", "纪录片", "讲座", "电影", "电视剧", "动漫", "综艺"};

    public HomePresenter(HomeViewer viewer) {
        super(viewer);
    }

    public void load() {
        loadBanner();
        loadVideoSeries();
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
        subscribe(XmlParser.parse(
                new String[]{
                        Xml.PUBLIC_CLASS_DATE,
                        Xml.DOCUMENTARY_DATE,
                        Xml.CATHEDRA_DATE,
                        Xml.MOVIE_ACTION_DATE,
                        Xml.TELEPLAY_MAINLAND_DATE,
                        Xml.ANIME_DATE,
                        Xml.TV_SHOW_DATE
                },
                new XmlObject.Tag[]{
                        Xml.TAG_M,
                        Xml.TAG_M,
                        Xml.TAG_M,
                        Xml.TAG_M,
                        Xml.TAG_M,
                        Xml.TAG_M,
                        Xml.TAG_M
                },
                new int[]{
                        MAX_ITEMS,
                        MAX_ITEMS,
                        MAX_ITEMS,
                        MAX_ITEMS,
                        MAX_ITEMS,
                        MAX_ITEMS,
                        MAX_ITEMS
                },
                new XmlParser.ParseSubscription<List<XmlObject>>() {
                    @Override
                    public void onResult(List<XmlObject> result) {
                        if (ValueUtil.isEmpty(result)) return;
                        List<VideoSeries> videoSeries = new ArrayList<>();
                        for (int i = 0; i < result.size(); i++) {
                            VideoSeries series = obtainMovieSeries(i, result.get(i));
                            if (series != null) {
                                videoSeries.add(series);
                            }
                        }
                        getViewer().onUpdateVideoSeries(videoSeries);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                }));
    }

    private VideoSeries obtainMovieSeries(int index, XmlObject result) {
        if (result != null && !result.isEmpty()) {
            List<VInfo> vInfos = new ArrayList<>();
            Bundle[] elements = result.getElements();
            for (Bundle element : elements) {
                VideoInfo info = new VideoInfo();
                info.setVid(element.getString("b"));
                info.setName(element.getString("a"));
                info.setDescription(element.getString("d"));
                vInfos.add(info);
            }
            return new VideoSeries(TITLES[index], vInfos);
        }
        return null;
    }
}
