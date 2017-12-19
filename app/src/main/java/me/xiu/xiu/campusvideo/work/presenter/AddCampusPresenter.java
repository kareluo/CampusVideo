package me.xiu.xiu.campusvideo.work.presenter;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.dao.DaoAlias;
import me.xiu.xiu.campusvideo.dao.DatabaseHelper;
import me.xiu.xiu.campusvideo.dao.common.Campus;
import me.xiu.xiu.campusvideo.dao.common.CampusDao;
import me.xiu.xiu.campusvideo.work.viewer.AddCampusViewer;

/**
 * Created by felix on 17/1/21.
 */
public class AddCampusPresenter extends Presenter<AddCampusViewer> {

    private static final String TAG = "AddCampusPresenter";

    public AddCampusPresenter(AddCampusViewer viewer) {
        super(viewer);
    }

    public void addCampus(final String name, final String host) {
        subscribe(Observable.just(new Campus(name, host))
                .observeOn(Schedulers.io())
                .map(campus -> {
                    CampusDao dao = DatabaseHelper.getDao(DaoAlias.CAMPUS);
                    dao.createOrUpdate(campus);
                    return true;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> {
                    if (success) {
                        getViewer().showToastMessage("添加成功");
                        getViewer().finish();
                    } else {
                        getViewer().showToastMessage("添加失败");
                    }
                }));
    }
}
