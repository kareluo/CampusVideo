package me.xiu.xiu.campusvideo.work.presenter;

import java.util.ArrayList;
import java.util.List;

import me.xiu.xiu.campusvideo.common.CampusVideo;
import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.core.xml.Xml;
import me.xiu.xiu.campusvideo.core.xml.XmlObject;
import me.xiu.xiu.campusvideo.core.xml.XmlParser;
import me.xiu.xiu.campusvideo.dao.common.Campus;
import me.xiu.xiu.campusvideo.work.viewer.CampusViewer;

/**
 * Created by felix on 16/4/10.
 */
public class CampusPresenter extends Presenter<CampusViewer> {

    private static final String TAG = "CampusPresenter";

    private List<Campus> mOriginalCampuses;

    private static final long TIMEOUT = 3;

    public CampusPresenter(CampusViewer viewer) {
        super(viewer);
        mOriginalCampuses = new ArrayList<>();
    }

    public void load() {
//        subscribe(Observable
//                .create(new Observable.OnSubscribe<List<Campus>>() {
//
//                    @Override
//                    public void call(Subscriber<? super List<Campus>> subscriber) {
//                        try {
//                            CampusDao campusDao = DatabaseHelper.getDao(DaoAlias.CAMPUS);
//                            if (campusDao.isEmpty()) {
//                                InputStream inputStream = getViewer().getContext()
//                                        .getResources().openRawResource(R.raw.campuses);
//
//                                XmlObject xmlObject = XmlParser.parse(inputStream,
//                                        XmlObject.Tag.create("campus", "campus"), Integer.MAX_VALUE, "utf-8");
//
//                                if (xmlObject != null && !xmlObject.isEmpty()) {
//                                    Bundle[] elements = xmlObject.getElements();
//                                    for (Bundle element : elements) {
//                                        mOriginalCampuses.add(new Campus(element.getString("name"),
//                                                element.getString("address")));
//                                    }
//                                }
//
//                                campusDao.createIfNotExists(mOriginalCampuses);
//                                Logger.d(TAG, "init campus database.");
//                                Logger.d(TAG, String.format(Locale.CHINA, "add %d items.", mOriginalCampuses.size()));
//                            } else {
//                                mOriginalCampuses = campusDao.queryForAll();
//                            }
//                            subscriber.onNext(mOriginalCampuses);
//                        } catch (Exception e) {
//                            subscriber.onError(e);
//                        }
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<List<Campus>>() {
//                    @Override
//                    public void call(List<Campus> campuses) {
//                        getViewer().onUpdateCampuses(campuses);
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Logger.e(TAG, throwable);
//                    }
//                }));
    }

    public void sync(List<Campus> campuses) {
//        if (ValueUtil.isEmpty(campuses)) return;
//
//        final ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
//
//        subscribe(Observable.from(campuses)
//                .flatMap(new Func1<Campus, Observable<?>>() {
//                    @Override
//                    public Observable<?> call(Campus campus) {
//                        return Observable.just(campus)
//                                .doOnNext(new Action1<Campus>() {
//                                    @Override
//                                    public void call(Campus campus) {
//                                        if (isAvailable(campus)) {
//                                            campus.state = Campus.State.CONNECTION;
//                                        } else {
//                                            campus.state = Campus.State.DISCONNECTION;
//                                        }
//                                    }
//                                }).subscribeOn(Schedulers.from(service));
//                    }
//                })
//                .buffer(2, TimeUnit.SECONDS, 5)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<List<Object>>() {
//                    @Override
//                    public void call(List<Object> objects) {
//                        getViewer().onUpdateState();
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Logger.w(TAG, throwable);
//                    }
//                }, new Action0() {
//                    @Override
//                    public void call() {
//                        service.shutdown();
//                    }
//                }));
    }

    private boolean isAvailable(Campus campus) {
        try {
            XmlObject xmlObject = XmlParser.parse(CampusVideo.getConfigXml(campus.host), Xml.TAG_ROOT);
            return xmlObject != null && !xmlObject.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public void sortByName(List<Campus> campuses) {
//        subscribe(Observable.just(campuses)
//                .map(new Func1<List<Campus>, List<Campus>>() {
//                    @Override
//                    public List<Campus> call(List<Campus> campuses) {
//                        Collections.sort(campuses, new Comparator<Campus>() {
//                            @Override
//                            public int compare(Campus a, Campus b) {
//                                return a.name.compareTo(b.name);
//                            }
//                        });
//                        return campuses;
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<List<Campus>>() {
//                    @Override
//                    public void call(List<Campus> campuses) {
//                        getViewer().onUpdateState();
//                    }
//                }));
    }

    public void sortByIQ(List<Campus> campuses) {
//        subscribe(Observable.just(campuses)
//                .map(new Func1<List<Campus>, List<Campus>>() {
//                    @Override
//                    public List<Campus> call(List<Campus> campuses) {
//                        Collections.sort(campuses, new Comparator<Campus>() {
//                            @Override
//                            public int compare(Campus a, Campus b) {
//                                return b.connection_count - a.connection_count;
//                            }
//                        });
//                        return campuses;
//                    }
//                })
//                .subscribe(new Action1<List<Campus>>() {
//                    @Override
//                    public void call(List<Campus> campuses) {
//                        getViewer().onUpdateState();
//                    }
//                }));
    }

    public void loadAll(boolean isLoadAll) {
//        subscribe(Observable.just(isLoadAll)
//                .map(new Func1<Boolean, List<Campus>>() {
//                    @Override
//                    public List<Campus> call(Boolean isLoadAll1) {
//                        try {
//                            CampusDao campusDao = DatabaseHelper.getDao(DaoAlias.CAMPUS);
//                            return campusDao.queryCampus(!isLoadAll1);
//                        } catch (Exception e) {
//                            Logger.w(TAG, e);
//                        }
//                        return null;
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<List<Campus>>() {
//                    @Override
//                    public void onCompleted() {
//                        getViewer().cancelLoadingDialog();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        getViewer().cancelLoadingDialog();
//                    }
//
//                    @Override
//                    public void onNext(List<Campus> campuses) {
//                        getViewer().onUpdateCampuses(campuses);
//                    }
//                }));
    }

    public void searchCampus(String text) {
//        subscribe(Observable.just(text)
//                .map(new Func1<String, List<Campus>>() {
//                    @Override
//                    public List<Campus> call(String s) {
//                        List<Campus> campuses = new ArrayList<>();
//                        for (Campus campus : mOriginalCampuses) {
//                            if (campus.name.contains(s)) {
//                                campuses.add(campus);
//                            }
//                        }
//                        return campuses;
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<List<Campus>>() {
//                    @Override
//                    public void call(List<Campus> campuses) {
//                        getViewer().onUpdateCampuses(campuses);
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Logger.w(TAG, throwable);
//                    }
//                }));
    }
}
