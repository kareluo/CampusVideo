package me.xiu.xiu.campusvideo.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

import me.xiu.xiu.campusvideo.dao.base.BaseDao;
import me.xiu.xiu.campusvideo.dao.classify.Classify;

/**
 * Created by felix on 16/3/20.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper implements DatabaseConfig {
    private static final String TAG = "DatabaseHelper";

    private Context mContext;
    private static DatabaseHelper mInstance;
    private ConcurrentHashMap<Class<?>, BaseDao<?, ?>> mDaoMap;

    public DatabaseHelper(Context context) {
        this(context, DATABASE_NAME, DB_VERSION);
        mContext = context;
        mDaoMap = new ConcurrentHashMap<>();
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

    public static <T extends BaseDao<?, ?>> T getDao(DaoAlias daoAlias) throws Exception {
        return (T) getInstance().getDaoInstance(daoAlias.daoClass);
    }

    public <T extends BaseDao<?, ?>> T getDaoInstance(Class<T> clazz) throws Exception {
        BaseDao<?, ?> baseDao = mDaoMap.get(clazz);
        if (baseDao == null) {
            baseDao = clazz.getConstructor(ConnectionSource.class).newInstance(getConnectionSource());
            mDaoMap.put(clazz, baseDao);
        }
        return (T) baseDao;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Classify.class);
        } catch (SQLException e) {
            Log.w(TAG, e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
