package me.xiu.xiu.campusvideo.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import me.xiu.xiu.campusvideo.dao.base.CVBaseDao;
import me.xiu.xiu.campusvideo.dao.classify.Classify;
import me.xiu.xiu.campusvideo.dao.common.Campus;
import me.xiu.xiu.campusvideo.dao.media.MediaPoint;
import me.xiu.xiu.campusvideo.dao.offline.Offline;
import me.xiu.xiu.campusvideo.dao.preference.AppPreference;

/**
 * Created by felix on 16/3/20.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper implements DatabaseConfig {
    private static final String TAG = "DatabaseHelper";

    private Context mContext;

    private static DatabaseHelper mInstance;

    public DatabaseHelper(Context context) {
        this(context, DATABASE_NAME, DB_VERSION);
        mContext = context;
    }

    public DatabaseHelper(Context context, String databaseName, int databaseVersion) {
        super(context, databaseName, null, databaseVersion);
}

    public static void init(Context context) {
        mInstance = new DatabaseHelper(context);
    }

    protected static DatabaseHelper getInstance() {
        return mInstance;
    }

    public static <T extends CVBaseDao<?, ?>> T getDao(DaoAlias daoAlias) throws Exception {
        return getInstance().getDaoInstance(daoAlias);
    }

    public <T extends CVBaseDao<?, ?>> T getDaoInstance(DaoAlias daoAlias) throws Exception {
        return daoAlias.getDao(getConnectionSource());
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Classify.class);
            TableUtils.createTableIfNotExists(connectionSource, Campus.class);
            TableUtils.createTableIfNotExists(connectionSource, AppPreference.class);
            TableUtils.createTableIfNotExists(connectionSource, Offline.class);
            TableUtils.createTableIfNotExists(connectionSource, MediaPoint.class);
        } catch (SQLException e) {
            Log.w(TAG, e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
