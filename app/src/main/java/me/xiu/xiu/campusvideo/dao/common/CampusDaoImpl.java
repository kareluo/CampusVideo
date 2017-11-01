package me.xiu.xiu.campusvideo.dao.common;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import me.xiu.xiu.campusvideo.dao.base.CVBaseDaoImpl;

/**
 * Created by felix on 16/4/14.
 */
public class CampusDaoImpl extends CVBaseDaoImpl<Campus, Long> implements CampusDao {

    public CampusDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Campus.class);
    }

    @Override
    public List<Campus> queryCampus(boolean justConnection) throws SQLException {
        if (justConnection) {
            return queryBuilder().where().eq(Campus.FIELD_STATE, Campus.State.CONNECTION).query();
        } else {
            return queryForAll();
        }
    }
}
