package me.xiu.xiu.campusvideo.work.presenter;

import android.os.Bundle;

import org.apache.commons.codec.binary.Hex;

import java.util.ArrayList;
import java.util.List;

import me.xiu.xiu.campusvideo.common.CampusVideo;
import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.common.video.Video;
import me.xiu.xiu.campusvideo.common.xml.Xml;
import me.xiu.xiu.campusvideo.common.xml.XmlObject;
import me.xiu.xiu.campusvideo.common.xml.XmlParser;
import me.xiu.xiu.campusvideo.util.CommonUtil;
import me.xiu.xiu.campusvideo.util.Logger;
import me.xiu.xiu.campusvideo.work.viewer.VideoViewer;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by felix on 16/2/18.
 */
public class VideoPresenter extends Presenter<VideoViewer> {
    private static final String TAG = "VideoPresenter";
    private volatile int mEpi = 1;

    public VideoPresenter(VideoViewer viewer) {
        super(viewer);
    }

    public void load(String vid) {
        mEpi = index(vid);
        subscribe(XmlParser.parseXml(CampusVideo.getFilm(vid),
                Xml.TAG_FILM, new XmlParser.ParseSubscription<XmlObject>() {
                    @Override
                    public void onResult(XmlObject result) {
                        Bundle[] elements = result.getElements();
                        if (!CommonUtil.isEmpty(elements)) {
                            getViewer().onUpdateInfo(elements[0]);
                        }
                    }
                }));

        subscribe(XmlParser.parseXml(CampusVideo.getEpisode(vid),
                Xml.TAG_ROOT, new XmlParser.ParseSubscription<XmlObject>() {
                    @Override
                    public void onResult(XmlObject result) {
                        Bundle[] elements = result.getElements();
                        if (!CommonUtil.isEmpty(elements)) {
                            mapEpisodes(elements[0]);
                        }
                    }
                }));
    }

    public void mapEpisodes(Bundle bundle) {
        subscribe(Observable
                .just(bundle)
                .map(new Func1<Bundle, List<Video.Episode>>() {
                    @Override
                    public List<Video.Episode> call(Bundle bundle) {
                        List<Video.Episode> es = new ArrayList<Video.Episode>();
                        String[] episodes = bundle.getString("c", "").split(",");
                        for (int i = 0; i < episodes.length; i++) {
                            es.add(new Video.Episode(mEpi + i, episodes[i]));
                        }
                        return es;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Video.Episode>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.w(TAG, e);
                    }

                    @Override
                    public void onNext(List<Video.Episode> episodes) {
                        getViewer().onUpdateEpisodes(episodes);
                    }
                }));
    }

    public static int index(String vid) {
        try {
            String url = new String(Hex.decodeHex(vid.toCharArray()));
            return Integer.parseInt(url.substring(url.indexOf("##") + 2));
        } catch (Exception e) {
            Logger.w(TAG, e);
        }
        return 1;
    }

}
