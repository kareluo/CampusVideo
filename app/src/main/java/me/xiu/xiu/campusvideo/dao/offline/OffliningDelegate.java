package me.xiu.xiu.campusvideo.dao.offline;

import android.os.SystemClock;
import android.support.v4.util.LongSparseArray;
import android.util.Log;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import me.xiu.xiu.campusvideo.aidls.Offlining;
import me.xiu.xiu.campusvideo.common.DownloadState;
import me.xiu.xiu.campusvideo.dao.DaoAlias;
import me.xiu.xiu.campusvideo.dao.DatabaseHelper;
import me.xiu.xiu.campusvideo.util.FileUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by felix on 16/5/4.
 */
public class OffliningDelegate {
    private static final String TAG = "OffliningDelegate";

    private OfflineDao mOfflineDao;
    private LongSparseArray<Offlining> mOfflinings;

    private LongSparseArray<Subscription> mSubscriptions;
    private Scheduler mOffliningScheduler;

    private OffliningCallback mCallback;

    private static final int MAX_THREAD = 3;
    private static final int BUFFER_SIZE = 8192;
    private static final long UPDATE_GAP = 1000;

    public OffliningDelegate(OffliningCallback callback) throws Exception {
        mCallback = callback;
        mOfflinings = new LongSparseArray<>();
        mSubscriptions = new LongSparseArray<>();
        mOfflineDao = DatabaseHelper.getDao(DaoAlias.OFFLINE);
        mOffliningScheduler = Schedulers.from(Executors.newFixedThreadPool(MAX_THREAD));
    }

    public void sync(Action0 callback) {
        Observable.just(getMaxId())
                .map(new Func1<Long, Integer>() {
                    @Override
                    public Integer call(Long maxId) {
                        try {
                            List<Offlining> offlinings = mOfflineDao.queryOfflinings(maxId);
                            for (Offlining offlining : offlinings) {
                                mOfflinings.put(offlining.getId(), offlining);
                            }
                            return mOfflinings.size();
                        } catch (SQLException ignored) {

                        }
                        return 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer count) {
                        if (callback != null) {
                            callback.call();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    public void actionStart() {
        sync(new Action0() {
            @Override
            public void call() {
                Observable.from(list())
                        .filter(offlining -> !isStart(offlining.getId()))
                        .map(offlining -> {
                            mSubscriptions.put(offlining.getId(), start(offlining.getId()));
                            return null;
                        })
                        .subscribeOn(Schedulers.io())
                        .subscribe(aVoid -> {

                        }, throwable -> {

                        });
            }
        });
    }

    public void toggle(long id) {

    }

    public boolean isStart(long id) {
        return isStart(mSubscriptions.get(id));
    }

    private boolean isStart(Subscription subscription) {
        return subscription != null && !subscription.isUnsubscribed();
    }

    public long getMaxId() {
        if (mOfflinings != null && mOfflinings.size() > 0) {
            return mOfflinings.keyAt(mOfflinings.size() - 1);
        }
        return 0L;
    }

    public void put(long id, Subscription subscription) {
        mSubscriptions.put(id, subscription);
    }

    public Offlining get(long id) {
        return mOfflinings.get(id);
    }

    public List<Offlining> list() {
        List<Offlining> offlinings = new ArrayList<>();
        int size = mOfflinings.size();
        for (int i = 0; i < size; i++) {
            offlinings.add(mOfflinings.valueAt(i));
        }
        return offlinings;
    }

    public boolean remove(long id) {
        Subscription subscription = mSubscriptions.get(id);
        if (subscription != null) {
            subscription.unsubscribe();
            mSubscriptions.remove(id);
        }
        mOfflinings.remove(id);
        try {
            return mOfflineDao.deleteById(id) == 1;
        } catch (SQLException ignored) {

        }
        return false;
    }

    public boolean save(long id) {
        return save(get(id));
    }

    public boolean save(Offlining offlining) {
        if (offlining != null) {
            Offline offline = new Offline(offlining);
            if (offline.getState() == DownloadState.DOWNLOADING.value) {
                offline.setState(DownloadState.WAITING.value);
            }
            try {
                mOfflineDao.createOrUpdate(offline);
                return true;
            } catch (SQLException ignored) {

            }
        }
        return false;
    }

    public Subscription start(long id) {
        return Observable
                .create(new Observable.OnSubscribe<Offlining>() {
                    @Override
                    public void call(Subscriber<? super Offlining> subscriber) {
                        Offlining offlining = mOfflinings.get(id);
                        if (offlining == null) {
                            subscriber.onError(new IllegalArgumentException("找不到Id对应的Offlining"));
                            return;
                        }

                        long progress = offlining.getProgress();

                        OkHttpClient client = new OkHttpClient();

                        Request request = new Request.Builder()
                                .url(offlining.getPath())
                                .addHeader("Range", "bytes=%d-" + progress)
                                .get()
                                .build();

                        RandomAccessFile accessFile;
                        try {
                            offlining.setState(DownloadState.DOWNLOADING.value);
                            subscriber.onNext(offlining);

                            Response response = client.newCall(request).execute();
                            ResponseBody body = response.body();

                            offlining.setTotal(Math.max(progress + body.contentLength(), offlining.getTotal()));

                            File file = FileUtils.obtainFile(new File(offlining.getDest()));
                            accessFile = new RandomAccessFile(file, "rw");
                            accessFile.seek(progress);

                            InputStream inputStream = response.body().byteStream();
                            int len, round = 0;
                            long millis = SystemClock.uptimeMillis();
                            byte[] buffer = new byte[BUFFER_SIZE];
                            while ((len = inputStream.read(buffer)) != -1) {
                                accessFile.write(buffer, 0, len);
                                progress += len;
                                if (SystemClock.uptimeMillis() - millis > UPDATE_GAP) {
                                    millis = SystemClock.uptimeMillis();
                                    offlining.setProgress(progress);
                                    subscriber.onNext(offlining);
                                    if (round++ % 15 == 0) save(offlining);
                                }
                            }

                            if (offlining.getTotal() <= offlining.getProgress()) {
                                offlining.setState(DownloadState.DONE.value);
                            }

                            save(offlining);
                            subscriber.onNext(offlining);
                        } catch (Exception e) {
                            Log.w(TAG, e);
                            offlining.setState(DownloadState.ERROR.value);
                            save(offlining);
                            subscriber.onNext(offlining);
                        }
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(mOffliningScheduler)
                .doOnUnsubscribe(() -> {
                    if (save(id)) {
                        mCallback.update(get(id));
                    }
                })
                .subscribe(offlining -> {
                    mCallback.update(offlining);
                }, throwable -> {

                });
    }

    public interface OffliningCallback {
        void update(Offlining offlining);

        void update(List<Offlining> offlinings);

        void remove(long id, boolean success);

        void add(Offlining offlining);
    }
}
