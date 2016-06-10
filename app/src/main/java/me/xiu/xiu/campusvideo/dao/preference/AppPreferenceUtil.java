package me.xiu.xiu.campusvideo.dao.preference;

import android.support.annotation.WorkerThread;

import java.sql.SQLException;

import me.xiu.xiu.campusvideo.dao.DaoAlias;
import me.xiu.xiu.campusvideo.dao.DatabaseHelper;
import me.xiu.xiu.campusvideo.util.Logger;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by felix on 16/4/26.
 */
public enum AppPreferenceUtil {

    INSTANCE;

    private static final String TAG = "AppPreferenceUtil";

    private AppPreferenceDao mAppPreferenceDao;

    AppPreferenceUtil() {
        try {
            mAppPreferenceDao = DatabaseHelper.getDao(DaoAlias.APP_PREFERENCE);
        } catch (Exception e) {
            Logger.w(TAG, e);
        }
    }

    public static void put(int key, String value) {
        Observable.just(value)
                .observeOn(Schedulers.io())
                .doOnNext(v -> {
                    try {
                        INSTANCE.mAppPreferenceDao.createOrUpdate(new AppPreference(key, v));
                    } catch (SQLException e) {
                        Logger.w(TAG, e);
                    }
                }).subscribe();
    }

    @WorkerThread
    public static String get(int key, String defaultValue) {
        try {
            AppPreference preference = INSTANCE.mAppPreferenceDao.queryForId(key);
            if (preference != null) {
                return preference.getValue();
            } else return defaultValue;
        } catch (SQLException e) {
            Logger.w(TAG, e);
        }
        return defaultValue;
    }

}
