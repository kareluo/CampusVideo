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
import me.xiu.xiu.campusvideo.work.viewer.fragment.BannerViewer;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by felix on 16/4/4.
 */
public class BannerPresenter extends Presenter<BannerViewer> {

    public BannerPresenter(BannerViewer viewer) {
        super(viewer);
    }

    public void loadBanner(ParseRule rule) {
        subscribe(XmlParser.parse(rule.getShortUrl(), rule.getTag(), rule.getCount(),
                new XmlParser.ParseSubscription<XmlObject>() {
                    @Override
                    public void onResult(XmlObject result) {
                        Bundle[] elements = result.getElements();
                        if (!ValueUtil.isEmpty(elements)) {
                            onGetBanners(elements);
                        }
                    }
                }));
    }

    private void onGetBanners(Bundle[] elements) {
        subscribe(Observable.just(elements)
                .map(new Func1<Bundle[], List<HomeBanner>>() {
                    @Override
                    public List<HomeBanner> call(Bundle[] bundles) {
                        List<HomeBanner> banners = new ArrayList<>();
                        for (Bundle bundle : bundles) {
                            banners.add(new HomeBanner(bundle));
                        }
                        return banners;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<HomeBanner>>() {
                    @Override
                    public void call(List<HomeBanner> banners) {
                        getViewer().onUpdateBanner(banners);
                    }
                }));
    }

}