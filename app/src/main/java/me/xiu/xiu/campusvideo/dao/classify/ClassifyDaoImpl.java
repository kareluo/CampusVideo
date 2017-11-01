package me.xiu.xiu.campusvideo.dao.classify;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import me.xiu.xiu.campusvideo.dao.base.CVBaseDaoImpl;

/**
 * Created by felix on 16/3/20.
 */
public class ClassifyDaoImpl extends CVBaseDaoImpl<Classify, Long> implements ClassifyDao {

    public ClassifyDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Classify.class);
    }

}
