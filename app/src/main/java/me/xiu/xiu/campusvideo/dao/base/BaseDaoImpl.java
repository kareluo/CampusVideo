package me.xiu.xiu.campusvideo.dao.base;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by felix on 16/3/20.
 */
public class BaseDaoImpl<T, ID> extends com.j256.ormlite.dao.BaseDaoImpl<T, ID> implements BaseDao<T, ID> {

    protected BaseDaoImpl(Class<T> dataClass) throws SQLException {
        super(dataClass);
    }

    protected BaseDaoImpl(ConnectionSource connectionSource, Class<T> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    protected BaseDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
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
