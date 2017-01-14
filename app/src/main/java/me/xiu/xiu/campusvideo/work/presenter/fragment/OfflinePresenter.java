package me.xiu.xiu.campusvideo.work.presenter.fragment;

import java.util.List;

import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.dao.DaoAlias;
import me.xiu.xiu.campusvideo.dao.DatabaseHelper;
import me.xiu.xiu.campusvideo.dao.offline.Offline;
import me.xiu.xiu.campusvideo.dao.offline.OfflineDao;
import me.xiu.xiu.campusvideo.dao.offline.Offlines;
import me.xiu.xiu.campusvideo.util.FileUtils;
import me.xiu.xiu.campusvideo.util.Logger;
import me.xiu.xiu.campusvideo.util.ValueUtil;
import me.xiu.xiu.campusvideo.work.viewer.fragment.OfflineViewer;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by felix on 16/4/16.
 */
public class OfflinePresenter extends Presenter<OfflineViewer> {

    private static final String TAG = "OfflinePresenter";

    public OfflinePresenter(OfflineViewer viewer) {
        super(viewer);
    }

    public void loadOfflines() {
        subscribe(Observable
                .create(new Observable.OnSubscribe<List<Offlines>>() {
                    @Override
                    public void call(Subscriber<? super List<Offlines>> subscriber) {
                        subscriber.onStart();
                        try {
                            OfflineDao dao = DatabaseHelper.getDao(DaoAlias.OFFLINE);
                            subscriber.onNext(dao.queryOfflineses());
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Offlines>>() {
                    @Override
                    public void call(List<Offlines> offlines) {
                        getViewer().onLoadSuccess(offlines);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.w(TAG, throwable);
                    }
                }));
    }

    public void deleteOfflines(List<Offlines> offlines) {
        if (!ValueUtil.isEmpty(offlines)) {
            subscribe(Observable.just(offlines)
                    .observeOn(Schedulers.io())
                    .map(new Func1<List<Offlines>, Boolean>() {
                        @Override
                        public Boolean call(List<Offlines> offlines) {
                            try {
                                OfflineDao dao = DatabaseHelper.getDao(DaoAlias.OFFLINE);
                                for (Offlines offline : offlines) {
                                    List<Offline> os = offline.getOfflines();
                                    if (os != null) {
                                        for (Offline o : os) {
                                            FileUtils.delete(o.getDest());
                                        }
                                    }
                                    dao.clear(offline.getVid());
                                }
                            } catch (Exception e) {
                                Logger.w(TAG, e);
                            }
                            return null;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean success) {
                            loadOfflines();
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Logger.w(TAG, throwable);
                        }
                    }));
        }
    }
}
