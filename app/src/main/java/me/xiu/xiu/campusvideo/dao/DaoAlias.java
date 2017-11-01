package me.xiu.xiu.campusvideo.dao;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import me.xiu.xiu.campusvideo.dao.base.CVBaseDao;
import me.xiu.xiu.campusvideo.dao.classify.Classify;
import me.xiu.xiu.campusvideo.dao.classify.ClassifyDao;
import me.xiu.xiu.campusvideo.dao.classify.ClassifyDaoImpl;
import me.xiu.xiu.campusvideo.dao.common.Campus;
import me.xiu.xiu.campusvideo.dao.common.CampusDao;
import me.xiu.xiu.campusvideo.dao.common.CampusDaoImpl;
import me.xiu.xiu.campusvideo.dao.media.MediaPoint;
import me.xiu.xiu.campusvideo.dao.media.MediaPointDao;
import me.xiu.xiu.campusvideo.dao.media.MediaPointDaoImpl;
import me.xiu.xiu.campusvideo.dao.offline.Offline;
import me.xiu.xiu.campusvideo.dao.offline.OfflineDao;
import me.xiu.xiu.campusvideo.dao.offline.OfflineDaoImpl;
import me.xiu.xiu.campusvideo.dao.preference.AppPreference;
import me.xiu.xiu.campusvideo.dao.preference.AppPreferenceDao;
import me.xiu.xiu.campusvideo.dao.preference.AppPreferenceDaoImpl;

/**
 * Created by felix on 16/3/20.
 */
@SuppressWarnings("unchecked")
public enum DaoAlias {

    CLASSIFY(Classify.class) {
        @Override
        public ClassifyDao createDao(ConnectionSource connectionSource) throws SQLException {
            return new ClassifyDaoImpl(connectionSource);
        }
    },
    CAMPUS(Campus.class) {
        @Override
        public CampusDao createDao(ConnectionSource connectionSource) throws SQLException {
            return new CampusDaoImpl(connectionSource);
        }
    },
    APP_PREFERENCE(AppPreference.class) {
        @Override
        public AppPreferenceDao createDao(ConnectionSource connectionSource) throws SQLException {
            return new AppPreferenceDaoImpl(connectionSource);
        }
    },
    OFFLINE(Offline.class) {
        @Override
        public OfflineDao createDao(ConnectionSource connectionSource) throws SQLException {
            return new OfflineDaoImpl(connectionSource);
        }
    },
    MEDIA_POINT(MediaPoint.class) {
        @Override
        public MediaPointDao createDao(ConnectionSource connectionSource) throws SQLException {
            return new MediaPointDaoImpl(connectionSource);
        }
    };

    Class<?> modelClass;

    private CVBaseDao<?, ?> mBaseDao;

    DaoAlias(Class<?> modelClass) {
        this.modelClass = modelClass;
    }

    public <T> T getDao(ConnectionSource connectionSource) throws SQLException {
        if (mBaseDao == null) {
            mBaseDao = createDao(connectionSource);
        }
        return (T) mBaseDao;
    }

    public abstract <T> T createDao(ConnectionSource connectionSource) throws SQLException;
}
