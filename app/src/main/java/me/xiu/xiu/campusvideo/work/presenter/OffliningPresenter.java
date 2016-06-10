package me.xiu.xiu.campusvideo.work.presenter;

import android.os.RemoteException;
import android.util.Log;

import java.util.Collections;
import java.util.List;

import me.xiu.xiu.campusvideo.aidls.IOffliningService;
import me.xiu.xiu.campusvideo.aidls.Offlining;
import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.work.viewer.OffliningViewer;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by felix on 16/4/17.
 */
public class OffliningPresenter extends Presenter<OffliningViewer> {
    private static final String TAG = "OffliningPresenter";

    public OffliningPresenter(OffliningViewer viewer) {
        super(viewer);
    }

    public void queryAll(IOffliningService service) {
        subscribe(Observable.just(service)
                .map(new Func1<IOffliningService, List<Offlining>>() {
                    @Override
                    public List<Offlining> call(IOffliningService iOffliningService) {
                        try {
                            return iOffliningService.getOffliningList();
                        } catch (RemoteException e) {
                            return Collections.emptyList();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Offlining>>() {
                    @Override
                    public void call(List<Offlining> offlinings) {
                        Log.d(TAG, "offlinings:" + offlinings);
                        getViewer().onUpdate(offlinings);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                }));
    }

}
