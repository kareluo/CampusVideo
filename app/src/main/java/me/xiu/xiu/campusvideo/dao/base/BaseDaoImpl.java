package me.xiu.xiu.campusvideo.dao.base;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

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
}
