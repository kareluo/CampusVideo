package me.xiu.xiu.campusvideo.work.presenter;

import android.os.Bundle;
import android.util.Xml;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.CampusVideo;
import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.common.xml.XmlObject;
import me.xiu.xiu.campusvideo.common.xml.XmlParser;
import me.xiu.xiu.campusvideo.dao.DaoAlias;
import me.xiu.xiu.campusvideo.dao.DatabaseHelper;
import me.xiu.xiu.campusvideo.dao.common.Campus;
import me.xiu.xiu.campusvideo.dao.common.CampusDao;
import me.xiu.xiu.campusvideo.util.Logger;
import me.xiu.xiu.campusvideo.util.ValueUtil;
import me.xiu.xiu.campusvideo.work.viewer.CampusViewer;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by felix on 16/4/10.
 */
public class CampusPresenter extends Presenter<CampusViewer> {
    private static final String TAG = "CampusPresenter";

    private List<Campus> mOriginalCampuses;

    private OkHttpClient mOkHttpClient;

    private static final long TIMEOUT = 3;

    public CampusPresenter(CampusViewer viewer) {
        super(viewer);
        mOriginalCampuses = new ArrayList<>();
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(TIMEOUT, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(TIMEOUT, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(TIMEOUT, TimeUnit.SECONDS);
    }

    public void load() {
        subscribe(Observable
                .create(new Observable.OnSubscribe<List<Campus>>() {

                    @Override
                    public void call(Subscriber<? super List<Campus>> subscriber) {
                        try {
                            CampusDao campusDao = DatabaseHelper.getDao(DaoAlias.CAMPUS);
                            if (campusDao.isEmpty()) {
                                InputStream inputStream = getViewer().getContext()
                                        .getResources().openRawResource(R.raw.campuses);

                                XmlObject xmlObject = XmlParser.parse(inputStream,
                                        XmlObject.Tag.create("campus", "campus"), Integer.MAX_VALUE, "utf-8");

                                if (xmlObject != null && !xmlObject.isEmpty()) {
                                    Bundle[] elements = xmlObject.getElements();
                                    for (Bundle element : elements) {
                                        mOriginalCampuses.add(new Campus(element.getString("name"),
                                                element.getString("address")));
                                    }
                                }

                                campusDao.createIfNotExists(mOriginalCampuses);
                                Logger.d(TAG, "init campus database.");
                                Logger.d(TAG, String.format(Locale.CHINA, "add %d items.", mOriginalCampuses.size()));
                            } else {
                                mOriginalCampuses = campusDao.queryForAll();
                            }
                            subscriber.onNext(mOriginalCampuses);
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(campuses -> {
                    getViewer().onUpdateCampuses(campuses);
                }, throwable -> {
                    Logger.e(TAG, throwable);
                }));
    }

    public void sync(List<Campus> campuses) {
        if (ValueUtil.isEmpty(campuses)) return;

        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);

        Observable.from(campuses)
                .flatMap(campus -> Observable.just(campus)
                        .doOnNext(campus1 -> {
                            try {
                                Response response = mOkHttpClient.newCall(
                                        new Request.Builder().url(
                                                CampusVideo.getConfigXml(campus1.host)
                                        ).get().build()).execute();

                                if (response.isSuccessful()) {
                                    InputStream inputStream = response.body().byteStream();
                                    if (inputStream != null) {
                                        XmlPullParser parser = Xml.newPullParser();
                                        parser.setInput(inputStream,
                                                me.xiu.xiu.campusvideo.common.xml.Xml.ENCODING);
                                        String namespace = parser.getNamespace();

                                        campus1.state = Campus.State.CONNECTION;
                                        return;
                                    }
                                }
                            } catch (Exception e) {
                                Logger.e(TAG, e);
                            }
                            campus1.state = Campus.State.DISCONNECTION;
                        })
                        .subscribeOn(Schedulers.from(service)))
                .buffer(2, TimeUnit.SECONDS, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> getViewer().onUpdateState(), throwable -> Logger.w(TAG, throwable),
                        service::shutdown);
    }

    public void sortByName(List<Campus> campuses) {
        subscribe(Observable.just(campuses)
                .map(campuses1 -> {
                    Collections.sort(campuses1, (a, b) -> a.name.compareTo(b.name));
                    return campuses1;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(campuses1 -> {
                    getViewer().onUpdateState();
                }));
    }

    public void sortByIQ(List<Campus> campuses) {
        subscribe(Observable.just(campuses)
                .map(campuses1 -> {
                    Collections.sort(campuses1, (a, b) -> b.connection_count - a.connection_count);
                    return campuses1;
                })
                .subscribe(campuses1 -> {
                    getViewer().onUpdateState();
                }));
    }

    public void loadAll(boolean isLoadAll) {
        subscribe(Observable.just(isLoadAll)
                .map(isLoadAll1 -> {
                    try {
                        CampusDao campusDao = DatabaseHelper.getDao(DaoAlias.CAMPUS);
                        return campusDao.queryCampus(!isLoadAll1);
                    } catch (Exception e) {
                        Logger.w(TAG, e);
                    }
                    return null;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Campus>>() {
                    @Override
                    public void onCompleted() {
                        getViewer().cancelLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewer().cancelLoadingDialog();
                    }

                    @Override
                    public void onNext(List<Campus> campuses) {
                        getViewer().onUpdateCampuses(campuses);
                    }
                }));
    }

    public void searchCampus(String text) {
        subscribe(Observable.just(text)
                .map(s -> {
                    List<Campus> campuses = new ArrayList<>();
                    for (Campus campus : mOriginalCampuses) {
                        if (campus.name.contains(s)) {
                            campuses.add(campus);
                        }
                    }
                    return campuses;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(campuses -> {
                    getViewer().onUpdateCampuses(campuses);
                }, throwable -> {
                    Logger.w(TAG, throwable);
                }));
    }
}
