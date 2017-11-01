package me.xiu.xiu.campusvideo.dao.media;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import me.xiu.xiu.campusvideo.dao.base.CVBaseDaoImpl;

/**
 * Created by felix on 17/1/29.
 */

public class MediaPointDaoImpl extends CVBaseDaoImpl<MediaPoint, Long> implements MediaPointDao {

    public MediaPointDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, MediaPoint.class);
    }
}
