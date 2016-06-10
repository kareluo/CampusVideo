package me.xiu.xiu.campusvideo.work.presenter;

import android.os.Bundle;
import android.util.Log;

import org.apache.commons.codec.binary.Hex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.xiu.xiu.campusvideo.common.CampusVideo;
import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.common.video.Video;
import me.xiu.xiu.campusvideo.common.xml.Xml;
import me.xiu.xiu.campusvideo.common.xml.XmlObject;
import me.xiu.xiu.campusvideo.common.xml.XmlParser;
import me.xiu.xiu.campusvideo.dao.DaoAlias;
import me.xiu.xiu.campusvideo.dao.DatabaseHelper;
import me.xiu.xiu.campusvideo.dao.offline.Offline;
import me.xiu.xiu.campusvideo.dao.offline.OfflineDao;
import me.xiu.xiu.campusvideo.util.CommonUtil;
import me.xiu.xiu.campusvideo.util.Logger;
import me.xiu.xiu.campusvideo.util.ValueUtil;
import me.xiu.xiu.campusvideo.work.service.OffliningService;
import me.xiu.xiu.campusvideo.work.viewer.OfflineViewer;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func3;
import rx.schedulers.Schedulers;

/**
 * Created by felix on 16/4/30.
 */
public class OfflinePresenter extends Presenter<OfflineViewer> {

    private volatile int mEpi = 1;
    private static final String TAG = "OfflinePresenter";

    public OfflinePresenter(OfflineViewer viewer) {
        super(viewer);
    }

    public void load(String vid) {

        mEpi = index(vid);

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
                .map(bundles -> {
                    List<Video.Episode> es = new ArrayList<>();
                    String[] episodes = bundles.getString("c", "").split(",");
                    for (int i = 0; i < episodes.length; i++) {
                        es.add(new Video.Episode(mEpi + i, episodes[i]));
                    }
                    return es;
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

    public void offline(String name, String videoId, String destDir, Video.Episode... episodes) {
        Log.d(TAG, "offline:" + name);
        subscribe(Observable
                .zip(Observable.just(name), Observable.just(videoId), Observable.just(episodes),
                        new Func3<String, String, Video.Episode[], List<Offline>>() {

                            @Override
                            public List<Offline> call(String name, String videoId, Video.Episode[] episodes) {
                                if (!ValueUtil.isEmpty(episodes)) {
                                    List<Offline> offlinings = new ArrayList<>();
                                    for (Video.Episode episode : episodes) {
                                        if (episode.isValid())
                                            offlinings.add(new Offline(name, videoId, destDir + episode.getSid(), episode));
                                    }
                                    return offlinings;
                                }
                                return Collections.emptyList();
                            }
                        })
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<List<Offline>>() {
                    @Override
                    public void call(List<Offline> offlinings) {
                        try {
                            OfflineDao dao = DatabaseHelper.getDao(DaoAlias.OFFLINE);
                            dao.createIfNotExists(offlinings);
                        } catch (Exception e) {
                            Logger.w(TAG, e);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Offline>>() {
                    @Override
                    public void call(List<Offline> offlines) {
                        OffliningService.postAction(getViewer().getContext(), OffliningService.ACTION_START);
                    }
                }));
    }
}
