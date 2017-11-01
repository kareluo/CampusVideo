package me.xiu.xiu.campusvideo.dao.preference;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import me.xiu.xiu.campusvideo.dao.base.CVBaseDaoImpl;

/**
 * Created by felix on 16/4/26.
 */
public class AppPreferenceDaoImpl extends CVBaseDaoImpl<AppPreference, Integer> implements AppPreferenceDao {

    public AppPreferenceDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, AppPreference.class);
    }


}
