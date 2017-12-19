package me.xiu.xiu.campusvideo.work.presenter.fragment;

import android.os.Bundle;

import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.core.xml.ParseRule;
import me.xiu.xiu.campusvideo.work.viewer.fragment.BannerViewer;

/**
 * Created by felix on 16/4/4.
 */
public class BannerPresenter extends Presenter<BannerViewer> {

    public BannerPresenter(BannerViewer viewer) {
        super(viewer);
    }

    public void loadBanner(ParseRule bannerRule, ParseRule videosRule) {
//        subscribe(XmlParser.parse(bannerRule.getShortUrl(), bannerRule.getTag(), bannerRule.getCount(),
//                new XmlParser.ParseSubscription<XmlObject>() {
//                    @Override
//                    public void onResult(XmlObject result) {
//                        Bundle[] elements = result.getElements();
//                        if (!ValueUtil.isEmpty(elements)) {
//                            onGetBanners(elements);
//                        }
//                    }
//                }));
//
//        subscribe(XmlParser.parse(videosRule.getShortUrl(), videosRule.getTag(), videosRule.getCount(),
//                new XmlParser.ParseSubscription<XmlObject>() {
//                    @Override
//                    public void onResult(XmlObject result) {
//                        Bundle[] elements = result.getElements();
//                        if (!ValueUtil.isEmpty(elements)) {
//                            onGetVideos(elements);
//                        }
//                    }
//                }));
    }

    private void onGetBanners(Bundle[] elements) {
//        subscribe(Observable.just(elements)
//                .map(new Func1<Bundle[], List<HomeBanner>>() {
//                    @Override
//                    public List<HomeBanner> call(Bundle[] bundles) {
//                        List<HomeBanner> banners = new ArrayList<>();
//                        for (Bundle bundle : bundles) {
//                            banners.add(new HomeBanner(bundle));
//                        }
//                        return banners;
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<List<HomeBanner>>() {
//                    @Override
//                    public void call(List<HomeBanner> banners) {
//                        getViewer().onUpdateBanner(banners);
//                    }
//                }));
    }

    private void onGetVideos(Bundle[] elements) {
//        subscribe(Observable.just(elements)
//                .map(new Func1<Bundle[], List<VInfo>>() {
//                    @Override
//                    public List<VInfo> call(Bundle[] bundles) {
//                        List<VInfo> infos = new ArrayList<>();
//                        for (Bundle bundle : bundles) {
//                            infos.add(VideoInfo.from(bundle));
//                        }
//                        return infos;
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<List<VInfo>>() {
//                    @Override
//                    public void call(List<VInfo> infos) {
//                        getViewer().onUpdateVideos(infos);
//                    }
//                }));
    }

}
