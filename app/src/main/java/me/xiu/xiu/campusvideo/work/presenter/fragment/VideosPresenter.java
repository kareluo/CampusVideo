package me.xiu.xiu.campusvideo.work.presenter.fragment;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.common.xml.ParseRule;
import me.xiu.xiu.campusvideo.common.xml.XmlObject;
import me.xiu.xiu.campusvideo.common.xml.XmlParser;
import me.xiu.xiu.campusvideo.util.Logger;
import me.xiu.xiu.campusvideo.util.ValueUtil;
import me.xiu.xiu.campusvideo.work.model.video.VInfo;
import me.xiu.xiu.campusvideo.work.model.video.VideoInfo;
import me.xiu.xiu.campusvideo.work.viewer.fragment.VideosViewer;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by felix on 16/4/7.
 */
public class VideosPresenter extends Presenter<VideosViewer> {
    private static final String TAG = "VideosPresenter";

    public VideosPresenter(VideosViewer viewer) {
        super(viewer);
    }

    public void load(ParseRule<XmlObject> parseRule) {
        if (parseRule == null) return;
        subscribe(XmlParser.parse(parseRule.getShortUrl(), parseRule.getTag(), parseRule.getCount(),
                new XmlParser.ParseSubscription<XmlObject>(parseRule.getFilter()) {
                    @Override
                    public void onResult(XmlObject result) {
                        Bundle[] elements = result.getElements();
                        if (!ValueUtil.isEmpty(elements)) {
                            map(elements);
                        }
                    }
                }
        ));
    }

    private void map(Bundle[] bundles) {
        subscribe(Observable.just(bundles)
                .map(new Func1<Bundle[], List<VInfo>>() {
                    @Override
                    public List<VInfo> call(Bundle[] bundles) {
                        List<VInfo> infos = new ArrayList<>();
                        for (Bundle bundle : bundles) {
                            VideoInfo info = new VideoInfo();
                            info.setName(bundle.getString("a"));
                            info.setVid(bundle.getString("b"));
                            info.setDescription(bundle.getString("c"));
                            infos.add(info);
                        }
                        return infos;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<VInfo>>() {
                    @Override
                    public void call(List<VInfo> infos) {
                        getViewer().onUpdate(infos);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(TAG, throwable);
                    }
                }));
    }

}
