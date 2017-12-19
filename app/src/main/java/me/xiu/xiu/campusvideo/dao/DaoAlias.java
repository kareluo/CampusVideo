package me.xiu.xiu.campusvideo.dao;

import me.xiu.xiu.campusvideo.dao.classify.Classify;
import me.xiu.xiu.campusvideo.dao.common.Campus;
import me.xiu.xiu.campusvideo.dao.media.MediaPoint;
import me.xiu.xiu.campusvideo.dao.offline.Offline;
import me.xiu.xiu.campusvideo.dao.preference.Preference;

/**
 * Created by felix on 16/3/20.
 */
public enum DaoAlias {

    CLASSIFY(Classify.class),
    CAMPUS(Campus.class),
    APP_PREFERENCE(Preference.class),
    OFFLINE(Offline.class),
    MEDIA_POINT(MediaPoint.class);

    private Class<?> daoClass;

    DaoAlias(Class<?> clazz) {
        daoClass = clazz;
    }

    public Class<?> getDaoClass() {
        return daoClass;
    }
}
