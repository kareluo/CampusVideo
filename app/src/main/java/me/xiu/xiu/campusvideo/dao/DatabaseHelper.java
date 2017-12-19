package me.xiu.xiu.campusvideo.dao;

import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import me.xiu.xiu.campusvideo.App;
import me.xiu.xiu.campusvideo.dao.classify.Classify;
import me.xiu.xiu.campusvideo.dao.common.Campus;
import me.xiu.xiu.campusvideo.dao.media.MediaPoint;
import me.xiu.xiu.campusvideo.dao.offline.Offline;
import me.xiu.xiu.campusvideo.dao.preference.Preference;
import me.xiu.xiu.campusvideo.util.Logger;

/**
 * Created by felix on 16/3/20.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper implements DatabaseConfig {

    private static final String TAG = "DatabaseHelper";

    private static DatabaseHelper sInstance;

    public DatabaseHelper() {
        super(App.getContext(), DATABASE_NAME, null, DB_VERSION);
        DaoManager.clearCache();
    }

    private static DatabaseHelper getInstance() {
        if (sInstance == null) {
            synchronized (DatabaseHelper.class) {
                if (sInstance == null) {
                    sInstance = new DatabaseHelper();
                }
            }
        }
        return sInstance;
    }

    @SuppressWarnings("unchecked")
    public static <D extends Dao<T, ?>, T> D getDao(DaoAlias alias) throws SQLException {
        return getInstance().getDao((Class<T>) alias.getDaoClass());
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Classify.class);
            TableUtils.createTableIfNotExists(connectionSource, Campus.class);
            TableUtils.createTableIfNotExists(connectionSource, Preference.class);
            TableUtils.createTableIfNotExists(connectionSource, Offline.class);
            TableUtils.createTableIfNotExists(connectionSource, MediaPoint.class);
        } catch (SQLException e) {
            Logger.w(TAG, e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public static void clear() {
        if (sInstance != null) {
            sInstance.close();
        }
    }

    @Override
    public void close() {
        super.close();
        DaoManager.clearCache();
    }
}
