package me.xiu.xiu.campusvideo.dao.base;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by felix on 16/3/20.
 */
public class CVBaseDaoImpl<T, ID> extends BaseDaoImpl<T, ID> implements CVBaseDao<T, ID> {

    protected CVBaseDaoImpl(ConnectionSource connectionSource, Class<T> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public boolean isEmpty() throws SQLException {
        return queryBuilder().countOf() == 0;
    }

    @Override
    public void createIfNotExists(List<T> datas) throws SQLException {
        if (datas == null) return;
        for (T data : datas) createIfNotExists(data);
    }

    @Override
    public void createOrUpdate(List<T> datas) throws SQLException {
        if (datas == null) return;
        for (T data : datas) createOrUpdate(data);
    }
}
