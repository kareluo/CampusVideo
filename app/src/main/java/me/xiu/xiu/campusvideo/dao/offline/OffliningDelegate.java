package me.xiu.xiu.campusvideo.dao.offline;

import java.util.List;

import me.xiu.xiu.campusvideo.aidls.Offlining;

/**
 * Created by felix on 16/5/4.
 */
public class OffliningDelegate {

//    private static final String TAG = "OffliningDelegate";
//
//    private OfflineDao mOfflineDao;
//
//    private Scheduler mOffliningScheduler;
//
//    private OffliningCallback mCallback;
//
//    private volatile LongSparseArray<Offlining> mOfflinings;
//
//    private volatile LongSparseArray<Subscription> mSubscriptions;
//
//    private static final int MAX_THREAD = 3;
//
//    private static final int BUFFER_SIZE = 8192;
//
//    private static final long UPDATE_GAP = 1000;
//
//    public OffliningDelegate(OffliningCallback callback) throws Exception {
//        mCallback = callback;
//        mOfflinings = new LongSparseArray<>();
//        mSubscriptions = new LongSparseArray<>();
//        mOfflineDao = DatabaseHelper.getDao(DaoAlias.OFFLINE);
//        mOffliningScheduler = Schedulers.from(Executors.newFixedThreadPool(MAX_THREAD));
//    }
//
//    public void sync(final Action0 callback) {
//        Observable.just(getMaxId())
//                .observeOn(Schedulers.io())
//                .map(new Func1<Long, Integer>() {
//                    @Override
//                    public Integer call(Long maxId) {
//                        try {
//                            List<Offlining> offlinings = mOfflineDao.queryOfflinings(maxId);
//                            for (Offlining offlining : offlinings) {
//                                mOfflinings.put(offlining.getId(), offlining);
//                            }
//                            return mOfflinings.size();
//                        } catch (SQLException ignored) {
//                            return 0;
//                        }
//                    }
//                })
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer count) {
//                        if (callback != null) {
//                            callback.call();
//                        }
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Logger.w(TAG, throwable);
//                    }
//                });
//    }
//
//    public List<Offlining> getOfflinings() {
//        List<Offlining> offlinings = new ArrayList<>();
//        for (int i = 0; i < mOfflinings.size(); i++) {
//            offlinings.add(mOfflinings.valueAt(i));
//        }
//        return offlinings;
//    }
//
//    public void actionStart() {
//        sync(new Action0() {
//            @Override
//            public void call() {
//                Observable.from(list())
//                        .filter(new Func1<Offlining, Boolean>() {
//                            @Override
//                            public Boolean call(Offlining offlining) {
//                                return OfflineState.WAITING.value == offlining.getState();
//                            }
//                        })
//                        .map(new Func1<Offlining, Object>() {
//                            @Override
//                            public Object call(Offlining offlining) {
//                                long id = offlining.getId();
//                                Subscription subscription = mSubscriptions.get(id);
//                                if (subscription != null) {
//                                    subscription.unsubscribe();
//                                }
//                                mSubscriptions.remove(id);
//                                mSubscriptions.put(id, start(id));
//                                return null;
//                            }
//                        })
//                        .subscribe(new Action1<Object>() {
//                            @Override
//                            public void call(Object o) {
//
//                            }
//                        }, new Action1<Throwable>() {
//                            @Override
//                            public void call(Throwable throwable) {
//                                Logger.w(TAG, throwable);
//                            }
//                        });
//            }
//        });
//    }
//
//    public void actionResumeAll() {
//        Observable.from(list())
//                .observeOn(Schedulers.io())
//                .filter(new Func1<Offlining, Boolean>() {
//                    @Override
//                    public Boolean call(Offlining offlining) {
//                        return OfflineState.resume(offlining.getState());
//                    }
//                })
//                .map(new Func1<Offlining, Offlining>() {
//                    @Override
//                    public Offlining call(Offlining offlining) {
//                        offlining.setState(OfflineState.WAITING.value);
//                        return offlining;
//                    }
//                })
//                .subscribe(new Action1<Offlining>() {
//                    @Override
//                    public void call(Offlining offlining) {
//                        actionStart();
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Logger.w(TAG, throwable);
//                    }
//                });
//    }
//
//    public void actionPauseAll() {
//        Observable.from(list())
//                .observeOn(Schedulers.io())
//                .filter(new Func1<Offlining, Boolean>() {
//                    @Override
//                    public Boolean call(Offlining offlining) {
//                        return OfflineState.valueOf(offlining.getState()).canPause();
//                    }
//                })
//                .map(new Func1<Offlining, Boolean>() {
//                    @Override
//                    public Boolean call(Offlining offlining) {
//                        pauseSync(offlining);
//                        return true;
//                    }
//                })
//                .subscribe(new Action1<Boolean>() {
//                    @Override
//                    public void call(Boolean result) {
//
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Logger.w(TAG, throwable);
//                    }
//                });
//    }
//
//    public void pauseSync(Offlining offlining) {
//        if (offlining == null) return;
//        long id = offlining.getId();
//        Subscription subscription = mSubscriptions.get(id);
//        if (subscription != null) {
//            subscription.unsubscribe();
//        }
//        mSubscriptions.remove(id);
//        offlining.setState(OfflineState.PAUSE.value);
//        save(offlining);
//    }
//
//    public void toggle(long id) {
//
//    }
//
//    private boolean isStart(Subscription subscription) {
//        return subscription != null && !subscription.isUnsubscribed();
//    }
//
//    public long getMaxId() {
//        if (mOfflinings != null && mOfflinings.size() > 0) {
//            return mOfflinings.keyAt(mOfflinings.size() - 1);
//        }
//        return 0L;
//    }
//
//    public void put(long id, Subscription subscription) {
//        mSubscriptions.put(id, subscription);
//    }
//
//    public Offlining get(long id) {
//        return mOfflinings.get(id);
//    }
//
//    public List<Offlining> list() {
//        List<Offlining> offlinings = new ArrayList<>();
//        int size = mOfflinings.size();
//        for (int i = 0; i < size; i++) {
//            offlinings.add(mOfflinings.valueAt(i));
//        }
//        return offlinings;
//    }
//
//    public void remove(final long id) {
//        Observable.just(id)
//                .map(new Func1<Long, Boolean>() {
//                    @Override
//                    public Boolean call(Long oid) {
//                        Subscription subscription = mSubscriptions.get(oid);
//                        if (subscription != null) {
//                            subscription.unsubscribe();
//                            mSubscriptions.remove(id);
//                        }
//                        mOfflinings.remove(oid);
//                        try {
//                            return mOfflineDao.deleteById(oid) == 1;
//                        } catch (SQLException e) {
//                            Logger.w(TAG, e);
//                        }
//                        return false;
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Action1<Boolean>() {
//                    @Override
//                    public void call(Boolean success) {
//                        mCallback.remove(id, success);
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Logger.w(TAG, throwable);
//                    }
//                });
//    }
//
//    public boolean save(long id) {
//        return save(get(id));
//    }
//
//    public boolean save(Offlining offlining) {
//        if (offlining != null) {
//            Offline offline = new Offline(offlining);
//            if (offline.getState() == OfflineState.OFFLINING.value) {
//                offline.setState(OfflineState.WAITING.value);
//            }
//            try {
//                mOfflineDao.createOrUpdate(offline);
//                return true;
//            } catch (SQLException ignored) {
//
//            }
//        }
//        return false;
//    }
//
//    public Subscription start(final long id) {
//        return Observable
//                .create(new Observable.OnSubscribe<Offlining>() {
//                    @Override
//                    public void call(Subscriber<? super Offlining> subscriber) {
//                        Offlining offlining = mOfflinings.get(id);
//                        Logger.d(TAG, "\n\n++++++++++++++");
//                        Logger.d(TAG, "start:" + offlining);
//
//                        if (offlining == null) {
//                            subscriber.onError(new IllegalArgumentException("找不到Id对应的Offlining"));
//                            return;
//                        }
//
//                        long progress = offlining.getProgress();
//
//                        Logger.d(TAG, "progress:" + progress);
//
//                        OkHttpClient client = new OkHttpClient();
//                        Request request = new Request.Builder()
//                                .url(offlining.getPath())
//                                .addHeader("Range", "bytes=%d-" + progress)
//                                .get()
//                                .build();
//
//                        RandomAccessFile accessFile = null;
//                        try {
//                            offlining.setState(OfflineState.OFFLINING.value);
//                            subscriber.onNext(offlining);
//
//                            Response response = client.newCall(request).execute();
//                            ResponseBody body = response.body();
//
//                            offlining.setTotal(Math.max(progress + body.contentLength(), offlining.getTotal()));
//
//                            Logger.d(TAG, "offlining total=" + offlining.getTotal());
//
//                            File file = FileUtils.obtainFile(new File(offlining.getDest()));
//                            accessFile = new RandomAccessFile(file, "rw");
//                            accessFile.seek(progress);
//
//                            InputStream inputStream = response.body().byteStream();
//                            int len, round = 0;
//                            long millis = SystemClock.uptimeMillis();
//                            byte[] buffer = new byte[BUFFER_SIZE];
//                            while (!subscriber.isUnsubscribed() && (len = inputStream.read(buffer)) != -1) {
//                                accessFile.write(buffer, 0, len);
//                                progress += len;
//                                offlining.setProgress(progress);
//                                if (SystemClock.uptimeMillis() - millis > UPDATE_GAP) {
//                                    millis = SystemClock.uptimeMillis();
//                                    subscriber.onNext(offlining);
//                                    if (round++ % 15 == 0) save(offlining);
//                                }
//                            }
//
//                            if (offlining.getTotal() <= offlining.getProgress()) {
//                                offlining.setState(OfflineState.DONE.value);
//                            }
//
//                            save(offlining);
//                            subscriber.onNext(offlining);
//                        } catch (Exception e) {
//                            Logger.w(TAG, e);
//                            Util.closeQuietly(accessFile);
//                            offlining.setState(OfflineState.ERROR.value);
//                            save(offlining);
//                            subscriber.onNext(offlining);
//                        }
//                        subscriber.onCompleted();
//                    }
//                })
//                .subscribeOn(mOffliningScheduler)
//                .doOnUnsubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        mCallback.update(get(id));
//                    }
//                })
//                .subscribe(new Action1<Offlining>() {
//                    @Override
//                    public void call(Offlining offlining) {
//                        mCallback.update(offlining);
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Logger.w(TAG, throwable);
//                    }
//                }, new Action0() {
//                    @Override
//                    public void call() {
//                        actionStart();
//                    }
//                });
//    }

    public interface OffliningCallback {
        void update(Offlining offlining);

        void update(List<Offlining> offlinings);

        void remove(long id, boolean success);

        void add(Offlining offlining);
    }
}
