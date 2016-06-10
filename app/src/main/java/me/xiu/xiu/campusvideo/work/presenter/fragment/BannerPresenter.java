package me.xiu.xiu.campusvideo.work.presenter.fragment;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.common.xml.ParseRule;
import me.xiu.xiu.campusvideo.common.xml.XmlObject;
import me.xiu.xiu.campusvideo.common.xml.XmlParser;
import me.xiu.xiu.campusvideo.util.ValueUtil;
import me.xiu.xiu.campusvideo.work.model.HomeBanner;
import me.xiu.xiu.campusvideo.work.model.video.VInfo;
import me.xiu.xiu.campusvideo.work.model.video.VideoInfo;
import me.xiu.xiu.campusvideo.work.viewer.fragment.BannerViewer;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by felix on 16/4/4.
 */
public class BannerPresenter extends Presenter<BannerViewer> {

    public BannerPresenter(BannerViewer viewer) {
        super(viewer);
    }

    public void loadBanner(ParseRule bannerRule, ParseRule videosRule) {
        subscribe(XmlParser.parse(bannerRule.getShortUrl(), bannerRule.getTag(), bannerRule.getCount(),
                new XmlParser.ParseSubscription<XmlObject>() {
                    @Override
                    public void onResult(XmlObject result) {
                        Bundle[] elements = result.getElements();
                        if (!ValueUtil.isEmpty(elements)) {
                            onGetBanners(elements);
                        }
                    }
                }));

        subscribe(XmlParser.parse(videosRule.getShortUrl(), videosRule.getTag(), videosRule.getCount(),
                new XmlParser.ParseSubscription<XmlObject>() {
                    @Override
                    public void onResult(XmlObject result) {
                        Bundle[] elements = result.getElements();
                        if (!ValueUtil.isEmpty(elements)) {
                            onGetVideos(elements);
                        }
                    }
                }));
    }

    private void onGetBanners(Bundle[] elements) {
        subscribe(Observable.just(elements)
                .map(bundles -> {
                    List<HomeBanner> banners = new ArrayList<>();
                    for (Bundle bundle : bundles) {
                        banners.add(new HomeBanner(bundle));
                    }
                    return banners;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(banners -> {
                    getViewer().onUpdateBanner(banners);
                }));
    }

    private void onGetVideos(Bundle[] elements) {
        subscribe(Observable.just(elements)
                .map(bundles -> {
                    List<VInfo> infos = new ArrayList<>();
                    for (Bundle bundle : bundles) {
                        infos.add(VideoInfo.from(bundle));
                    }
                    return infos;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(infos -> {
                    getViewer().onUpdateVideos(infos);
                }));
    }

}
