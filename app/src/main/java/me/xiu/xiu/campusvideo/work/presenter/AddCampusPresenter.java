package me.xiu.xiu.campusvideo.work.presenter;

import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.dao.DaoAlias;
import me.xiu.xiu.campusvideo.dao.DatabaseHelper;
import me.xiu.xiu.campusvideo.dao.common.Campus;
import me.xiu.xiu.campusvideo.dao.common.CampusDao;
import me.xiu.xiu.campusvideo.util.Logger;
import me.xiu.xiu.campusvideo.work.viewer.AddCampusViewer;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by felix on 17/1/21.
 */
public class AddCampusPresenter extends Presenter<AddCampusViewer> {

    private static final String TAG = "AddCampusPresenter";

    public AddCampusPresenter(AddCampusViewer viewer) {
        super(viewer);
    }

    public void addCampus(final String name, final String host) {
        subscribe(Observable.create(
                new Observable.OnSubscribe<Boolean>() {
                    @Override
                    public void call(Subscriber<? super Boolean> subscriber) {
                        try {
                            Campus campus = new Campus(name, host);
                            CampusDao campusDao = DatabaseHelper.getDao(DaoAlias.CAMPUS);
                            campusDao.createOrUpdate(campus);
                            subscriber.onNext(true);
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                        subscriber.onNext(false);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean success) {
                        if (success) {
                            getViewer().showToastMessage("添加成功");
                            getViewer().finish();
                        } else {
                            getViewer().showToastMessage("添加失败");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.w(TAG, throwable);
                        getViewer().showToastMessage("添加失败:" + throwable.getMessage());
                    }
                }));
    }
}
